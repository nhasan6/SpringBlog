package com.strawberry.springblog;
import java.util.List;

public interface PostDao {

    List <Post> findAll();
    Post findById(int postId);
    Post save(Post post);
    Post update(int postId, Post post);
    boolean deleteById(int postId);
    boolean existsById(int postId);
    void deleteAllByAuthor(int userId);
    void deleteAllPosts();
}
