package com.strawberry.springblog;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private User currentUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.currentUser = null;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(userRepository.findAll()); // this shouldn't ever return a null value, only perchance an empty ArrayList
    }

    @Override
    public User addUser(String email, String password, String firstName, String lastName, String username) {
        // Checks list of existing users 
        for (User user : userRepository.findAll()) {
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
        User newUser = new User(email, password, firstName, lastName, username);
        userRepository.save(newUser);
        this.currentUser = newUser; // signs them in
        return newUser;
    } 

    @Override
    public User findUser(String username) { 
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        };
        throw new IllegalArgumentException("Username does not exist.");
    }

    @Override
    // whether current user has authorization to modify the account passed as an argument
    public boolean hasAuthorization(User account) { // compares by userId #s because usernames can be modified 
        if ((this.currentUser != null && account.getId() != this.currentUser.getId() && !isAdmin(this.currentUser.getId())) || this.currentUser == null) { // account is not the current user's and they are not the admin or no one is signed in
            return false;
        }
        return true;
    }

    // @Override
    // public boolean modifyUser(User account, String field, String newContent) {
    //     if (hasAuthorization(account)) {
    //         switch (field.toLowerCase()) {
    //             case "password":
    //                 account.setPassword(newContent);
    //                 break;
    //             case "username":
    //                 account.setUsername(newContent);
    //                 break;
    //             case "name":
    //                 account.setFirstName(newContent);
    //                 break;
    //             default:
    //                 throw new IllegalArgumentException("Inadmissable data field."); // 'password', 'username', 'name'
    //         }
    //         userRepository.save(account); // add updated profile back to the map
    //         return true;
    //     }
    //     return false; // no authorization

    // }

    @Override
    public boolean modifyUser(User account, user updatedUser) {
        if (hasAuthorization(account)) {
            account.setPassword(updatedUser.getPassword());
            account.setUsername(updatedUser.getUsername());
            account.setFirstName(updatedUser.getFirstName());
            userRepository.save(account); // add updated profile back to the map
            return true;
        }
        return false; // no authorization

    }
    
    @Override // MAJOR MAJOR MAJOR BUG
    // change 
    @Transactional
    public void deleteUser(User account) {  
        int accountId = account.getId();
        if (isAdmin(accountId)) { // admin is attempting to delete their own account
            throw new IllegalArgumentException("Admin cannot delete their own account.");
        } else if (isAdmin(this.currentUser.getId())) { // admin is deleting someone else's account (doesn't log them out)
            postRepository.deleteByAuthor(account);
            userRepository.deleteById(accountId);
        } else { // user is deleting their own account
            postRepository.deleteByAuthor(account);
            userRepository.deleteById(accountId);
            this.currentUser = null; // signs them out
        }
    } 
    

    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public boolean loginToUser(User lockedUser, String password) {
        if (lockedUser.verifyPassword(password)) {
            this.currentUser = lockedUser;
            return true;
        } 
        return false; // incorrect password, therefore unsuccessful login
    }

    @Override
    public boolean logOutOfUser() {
        if (this.currentUser != null) { // someone is signed in
            this.currentUser = null;
            return true;
        }
        return false; // no one was signed in to begin with
    }

    @Override 
    public int countPostsbyAuthor(String username) {
        User account = findUser(username); // throw an exception if account doesn't exist
        return postRepository.countByAuthor(account);// return value should never be null, if anyting it could be an empty arraylist??
    }

    public boolean isAdmin(int userId) {
        return userId == 1;
    }

}
