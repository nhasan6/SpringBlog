package com.strawberry.springblog;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{

    // List <Post> findAll(); 
    List <Post> findAllByOrderByTitleAsc(); 
    List <Post> findAllByOrderByLastUpdatedDesc(); 
    List <Post> findAllByOrderByAuthorUsername(); 

    List <Post> findByTitleIgnoreCaseContaining(String text);
    List <Post> findByContentIgnoreCaseContaining(String text);
    List <Post> findByAuthorFirstNameIgnoreCaseOrAuthorUsernameIgnoreCase(String name, String username);
    List <Post> findByDateCreatedGreaterThan(LocalDateTime date);
    List <Post> findByDateCreatedLessThan(LocalDateTime date);
    int countByAuthor(User author);
    Post findById(int postId); // overrides default Optional return value
    List<Post> findByAuthor(User author);
    // Post save(Post post);
    // Post update(int postId, Post post);
    // void deleteById(int postId); 
    // boolean existsById(int postId);
    void deleteByAuthor(User author); // custom method
}
