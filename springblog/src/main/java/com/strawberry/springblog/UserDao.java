package com.strawberry.springblog;

import java.util.List;

public interface UserDao {

    List <User> findAll();
    User findById(int userId);
    User save(User user);
    User update(int userId, User user);
    boolean deleteById(int userId);
    boolean existsById(int userId);
    User getCurrent();
    void setCurrent(User user);
    void deleteAllUsers();
}
