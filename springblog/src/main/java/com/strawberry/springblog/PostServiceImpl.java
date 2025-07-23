package com.strawberry.springblog;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{
    private final PostDao postDao;
    private final UserService userService; 

    @Autowired 
    public PostServiceImpl(PostDao postDao, UserService userService) {
        this.postDao = postDao;
        this.userService = userService; 
    }

    @Override
    public ArrayList<Post> getAllPosts() {
        return new ArrayList<>(postDao.findAll()); // this shouldn't ever return a null value, only perchance an empty ArrayList
    }

    @Override
    public Post findPost(int postIdNum) { 
        Post blogPost = postDao.findById(postIdNum);
        if (blogPost != null) {
            return blogPost;
        } 
        throw new IllegalArgumentException("Post with specified ID # does not exist.");
    }

    @Override // different?
    public Post addPost(String title, String content) { // throws an exception if it doesn't exist. 
        User currentUser = userService.getCurrentUser(); 
        if (currentUser != null) { 
            Post newPost = new Post(currentUser.getID(), title, content);
            return postDao.save(newPost); // saves post to map (aka blog)
        }
        throw new IllegalStateException("User must sign in or create an account to start blogging!");
    }

    @Override
    public boolean deletePost(Post blogPost){ // (also too many falses for 2 dfferent reasons - authorizarion and not existing)
        if (isAuthorOrAdmin(userService.getCurrentUser(), blogPost)) { 
            return postDao.deleteById(blogPost.getID()); // return false if post doesn't exist (too many falses)
        }
        return false; // no authorization
    }

    private boolean isAuthor(Post post, User user) {
        return post.getAuthorId() == user.getID();
    }

    @Override
    public boolean modifyPost(int postIdNum, String field, String newContent) { // change bc the booleans are too ocnfusing old version returned a boolean
        Post blogPost = postDao.findById(postIdNum);
        if (blogPost == null) {
            throw new IllegalArgumentException("Post with specified ID # does not exist.");
        }
        if (isAuthorOrAdmin(userService.getCurrentUser(), blogPost)) {
            switch (field.toLowerCase()) {
                case "overwrite":
                    blogPost.changeContent(newContent);
                    break;
                case "add":
                    blogPost.addContent(newContent);
                    break;
                case "rename":
                    blogPost.setTitle(newContent);
                    break;
                default:
                    throw new IllegalArgumentException("Inadmissable data field."); // 'rename', 'update', 'overwrite'
            }
            postDao.update(postIdNum, blogPost); // add updated post back to the blog
            return true;
        }
        return false; // user didn't have authorization to modify the post
    }

    @Override
    public ArrayList<Post> getPostsbyAuthor(String username) {
        User account = userService.findUser(username); // throw an exception if account doesn't exist
        ArrayList<Post> result = new ArrayList<Post>();
        for (Post blogPost : postDao.findAll()) {
            if (isAuthor(blogPost, account)) {
                result.add(blogPost);
            }
        }
        return result; // return value would never be null, if anyting it could be an empty arraylist
    }

    private boolean isAuthorOrAdmin(User currentUser, Post blogPost) {
        if (currentUser == null) {
            return false;
        }
        return currentUser.getID() == blogPost.getAuthorId() || userService.isAdmin(currentUser.getID());  // if the current user is the author of the post, or is the admin
    }

}
 