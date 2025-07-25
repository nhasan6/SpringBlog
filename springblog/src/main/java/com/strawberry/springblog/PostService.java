package com.strawberry.springblog;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface PostService {

    public ArrayList<Post> getAllPosts();
    public ArrayList<Post> getAllPostsOrderByTitle();
    public ArrayList<Post> getAllPostsOrderByLastUpdated();
    public ArrayList<Post> getAllPostsOrderByUsername();

    public ArrayList<Post> filterByTitle(String text);
    public ArrayList<Post> filterByContent(String text);
    public ArrayList<Post> filterByNameOrUsername(String name, String username);
    public ArrayList<Post> filterByDateGreaterThan(String date);
    public ArrayList<Post> filterByDateLessThan(String date);

    public Post findPost(int postIdNum);
    public Post addPost(String title, String content);
    public boolean deletePost(Post blogPost);
    public boolean modifyPost(int postIdNum, String field, String newContent);
    public ArrayList<Post> getPostsbyAuthor(String username);

}