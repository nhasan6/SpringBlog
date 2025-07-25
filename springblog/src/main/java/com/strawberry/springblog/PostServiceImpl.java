package com.strawberry.springblog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final UserService userService; 

    @Autowired 
    public PostServiceImpl(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService; 
    }

    @Override
    public ArrayList<Post> getAllPosts() {
        return new ArrayList<>(postRepository.findAll()); // this shouldn't ever return a null value, only perchance an empty ArrayList
    }

    @Override
    public ArrayList<Post> getAllPostsOrderByTitle() {
        return new ArrayList<>(postRepository.findAllByOrderByTitleAsc()); // this shouldn't ever return a null value, only perchance an empty ArrayList
    }

    @Override
    public ArrayList<Post> getAllPostsOrderByLastUpdated() {
        return new ArrayList<>(postRepository.findAllByOrderByLastUpdatedDesc()); // this shouldn't ever return a null value, only perchance an empty ArrayList
    }

    @Override
    public ArrayList<Post> getAllPostsOrderByUsername() {
        return new ArrayList<>(postRepository.findAllByOrderByAuthorUsername()); // this shouldn't ever return a null value, only perchance an empty ArrayList
    }

    @Override
    public ArrayList<Post> filterByTitle(String text) {
        return new ArrayList<>(postRepository.findByTitleIgnoreCaseContaining(text)); // this shouldn't ever return a null value, only perchance an empty ArrayList
    }

    @Override
    public ArrayList<Post> filterByContent(String text) {
        return new ArrayList<>(postRepository.findByContentIgnoreCaseContaining(text));
    }

    @Override
    public ArrayList<Post> filterByNameOrUsername(String name, String username) {
        return new ArrayList<>(postRepository.findByAuthorFirstNameIgnoreCaseOrAuthorUsernameIgnoreCase(name, username));
    }

    @Override
    public ArrayList<Post> filterByDateGreaterThan(String date) {
        // date must be in the "YYYY-MM-dd" format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        date += " 23:59";
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return new ArrayList<>(postRepository.findByDateCreatedGreaterThan(dateTime));
    }

    @Override
    public ArrayList<Post> filterByDateLessThan(String date) {
        // date must be in the "YYYY-MM-dd" format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        date += " 00:00";
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return new ArrayList<>(postRepository.findByDateCreatedLessThan(dateTime));
    }

    @Override
    public Post findPost(int postIdNum) { 
        Post blogPost = postRepository.findById(postIdNum);
        if (blogPost != null) {
            return blogPost;
        } 
        throw new IllegalArgumentException("Post with specified ID # does not exist.");
    }

    @Override // different?
    public Post addPost(String title, String content) { // throws an exception if it doesn't exist. 
        User currentUser = userService.getCurrentUser(); 
        if (currentUser != null) { 
            Post newPost = new Post(currentUser, title, content);
            return postRepository.save(newPost); // saves post to map (aka blog)
        }
        throw new IllegalStateException("User must sign in or create an account to start blogging!");
    }

    @Override
    public boolean deletePost(Post blogPost){ // assumes external verification for the existence of the post
        if (isAuthorOrAdmin(userService.getCurrentUser(), blogPost)) { 
            postRepository.deleteById(blogPost.getId()); // delete post
            return true; 
        }
        return false; // no authorization
    }

    private boolean isAuthor(Post post, User user) {
        return post.getAuthor().getId() == user.getId();
    }

    @Override
    public boolean modifyPost(int postIdNum, String field, String newContent) { // change bc the booleans are too ocnfusing old version returned a boolean
        Post blogPost = postRepository.findById(postIdNum);
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
            postRepository.save(blogPost); // add updated post back to the blog --> previouslt update, we will see
            return true;
        }
        return false; // user didn't have authorization to modify the post
    }

    @Override 
    public ArrayList<Post> getPostsbyAuthor(String username) {
        User account = userService.findUser(username); // throw an exception if account doesn't exist
        return new ArrayList<>(postRepository.findByAuthor(account));// return value should never be null, if anyting it could be an empty arraylist??
    }

    private boolean isAuthorOrAdmin(User currentUser, Post blogPost) {
        if (currentUser == null) {
            return false;
        }
        return currentUser.getId() == blogPost.getAuthor().getId() || userService.isAdmin(currentUser.getId());  // if the current user is the author of the post, or is the admin
    }

}
 