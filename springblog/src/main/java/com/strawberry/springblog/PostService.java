package com.strawberry.springblog;

import java.util.ArrayList;

public interface PostService {

    public ArrayList<Post> getAllPosts();
    public Post findPost(int postIdNum);
    public Post addPost(String title, String content);
    public boolean deletePost(Post blogPost);
    public boolean modifyPost(int postIdNum, String field, String newContent);
    public ArrayList<Post> getPostsbyAuthor(String username);

}