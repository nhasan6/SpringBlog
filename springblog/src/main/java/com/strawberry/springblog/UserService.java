package com.strawberry.springblog;

import java.util.ArrayList;

public interface UserService {

    public ArrayList<User> getAllUsers();
    public User addUser(String email, String password, String firstName, String lastName, String username);
    public User findUser(String username);
    public boolean hasAuthorization(User account);
    public boolean modifyUser(User account, String field, String newContent);
    public void deleteUser(User account);
    public User getCurrentUser();
    public boolean loginToUser(User lockedUser, String password);
    public boolean logOutOfUser();
    public boolean isAdmin(int userId);
    public int countPostsbyAuthor(String username);
}
