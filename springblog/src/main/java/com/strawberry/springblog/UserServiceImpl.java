package com.strawberry.springblog;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PostDao postDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, PostDao postDao) {
        this.userDao = userDao;
        this.postDao = postDao;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(userDao.findAll()); // this shouldn't ever return a null value, only perchance an empty ArrayList
    }

    @Override
    public User addUser(String email, String password, String name, String username) {
        // Checks list of existing users 
        for (User user : userDao.findAll()) {
            // The same email can't be used to create more than 1 user
            if (user.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("A user with this email already exists, please try again.");
            }
            // Usernames must be unique 
            if (user.getUsername().equalsIgnoreCase(username)) {
                throw new IllegalArgumentException("This username is already taken, please try again.");
            }
        }
        // If there are no duplications, a new user is created and signed in
        User newUser = new User(email, password, name, username);
        userDao.save(newUser);
        return newUser;
    } 

    public User findUser(String username) { 
        for (User user : userDao.findAll()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new IllegalArgumentException("Username does not exist.");
    }

    public boolean hasAuthorization(User account) { // compares by userId #s because usernames can be modified 
        User current = userDao.getCurrent();
        if ((current != null && account.getID() != current.getID() && current.getID() != 1) || current == null) { // account is not the current user's and they are not the admin or no one is signed in
            return false;
        }
        return true;
    }

    public boolean modifyUser(User account, String field, String newContent) {
        if (hasAuthorization(account)) {
            switch (field.toLowerCase()) {
                case "password":
                    account.setPassword(newContent);
                    break;
                case "username":
                    account.setUsername(newContent);
                    break;
                case "name":
                    account.setName(newContent);
                    break;
                default:
                    throw new IllegalArgumentException("Inadmissable data field."); // 'password', 'username', 'name'
            }
            userDao.update(account.getID(), account); // add updated profile back to the map
            return true;
        }
        return false; // no authorization

    }
    
    // change 
    public void deleteUser(User account) {  // bug, should return false for admin. you can't remove the admin. i've decided. 
        int accountId = account.getID();
        if (accountId == 1) { // admin is deleting their own account, which wipes the whole blog
            userDao.deleteAllUsers();
            postDao.deleteAllPosts(); 
            User.resetUserCount(); 
        } else if (userDao.getCurrent().getID() == 1) { // admin is deleting someone else's account (doesn't log them out)
            postDao.deleteAllByAuthor(accountId);
            userDao.deleteById(accountId);
        } else { // user is deleting their own account
            postDao.deleteAllByAuthor(accountId);
            userDao.deleteById(accountId);
            userDao.setCurrent(null); // signs them out
        }
    } 
    

    public User getCurrentUser() {
        return userDao.getCurrent();
    }

    public boolean loginToUser(User lockedUser, String password) {
        if (lockedUser.verifyPassword(password)) {
            userDao.setCurrent(lockedUser);
            return true;
        } 
        return false; // incorrect password, therefore unsuccessful login


    }
}
