package com.strawberry.springblog;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@Repository
public class UserDaoImpl implements UserDao  {

    // final bc only one copy of the map can be created. (can't assign users another new concurrenthashmap<>())
    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private User currentUser = null;

    public UserDaoImpl() {}

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User findById(int userId) {
        if (existsById(userId)) {
            return users.get(userId); 
        } 
        return null;
    }

    @Override
    public User save(User user) { // return value redundant?
        // they use some other id counting logic?
        users.put(user.getID(), user);
        this.currentUser = user; // updates them as the new current user
        return user; // does it really need to receive and return the user (kind of redundant)
    }

    @Override
    public User getCurrent() {
        return this.currentUser;
    }

    @Override
    public void setCurrent(User user) {
        this.currentUser = user;
    }

    @Override 
    public User update(int userId, User user) {
        User existingUser = users.get(userId);
        if (existingUser != null) {
            if (user.getUsername() != null) {
                existingUser.setUsername(user.getUsername());
            }
            if (user.getName() != null) {
                existingUser.setName(user.getName());
            }
            if (user.getPassword() != null) {
                existingUser.setPassword(user.getPassword());
            }
            users.replace(userId, existingUser);
            return existingUser;
        } 
        return null;
    }

    @Override
    public boolean deleteById(int userId) {
        if (users.containsKey(userId)) {
            users.remove(userId);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsById(int userId) {
        return users.containsKey(userId);
    }

    @Override
    public void deleteAllUsers() {
        this.users.clear();
        this.currentUser = null;
        User.resetUserCount(); // allows the next "first" user to take over as admin
    }
}
