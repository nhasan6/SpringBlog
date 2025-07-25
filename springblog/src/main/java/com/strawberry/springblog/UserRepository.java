package com.strawberry.springblog;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    // List <User> findAll();
    User findById(int userId); // overrides default Optional return value
    User findByUsername(String username); // add this 
    // User save(User user);
    // User update(int userId, User user); --> changed to just save, and hoping for the best 
    // void deleteById(int userId); 
    // boolean existsById(int userId);
    // void deleteAll();
}
