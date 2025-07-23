package com.strawberry.springblog;
import jakarta.validation.constraints.*;

public class User {
    private final int ID; 
    @NotBlank
    @Email (message = "Invalid email address")
    private String email;
    @NotBlank (message = "Username is required")
    private String username; // the handle under which their posts will be shared 
    @NotBlank (message = "Password is required")
    private String password;
    @NotBlank (message = "Name is required")
    private String name;
    public static int userCount = 0; // questionable?
    
    public User() {
        this.ID = 0; //default constructor for JSON deserialization (doesn't increment id counter)
    }
    public User(String email, String password, String name, String username) {
       userCount++;
       this.ID = userCount;
       this.email = email;
       this.password = password;
       this.name = name;
       this.username = username;
    //    this.posts = new ArrayList<Post>();
    }

    // admin has userID = 1. 
    // When blog is wiped (admin gets deleted), userCount must be reset so a new admin can be initialized
    public static void resetUserCount() { 
        userCount = 0;
    }

    public int getID() {
        return this.ID;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setName(String newName) { // need to update all blog posts
        this.name = newName;
    }

    public void setUsername(String newUsername) { // need to update all blog posts
        this.username = newUsername;
    }

    public boolean verifyPassword(String input) {
        if (input.equals(this.password)) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return this.name + " | " + this.username;
    }

    // public void setPassword(String oldPassword, String newPassword) {
    //     if (verifyPassword(oldPassword)) {
    //         this.password = newPassword;
    //         System.out.println("Password changed successfully.");
    //     } else {
    //         System.out.println("Incorrect password, please try again");
    //     }
    // }

     // public void createPost(String title, String content) {
    //     Post post = new Post(this, title, content);
    //     posts.add(post); // save post to list of posts
    // }

    // public ArrayList<Post> getAllPosts() {
    //     return posts;
    // }

    // public Post editPost(Post postToBeEdited, String content) { 
    //     postToBeEdited.changeContent(content);
    //     return postToBeEdited;
    // } 

    // public Post addToPost(Post postToBeEdited, String content) {
    //     postToBeEdited.addContent(content);
    //     return postToBeEdited;
    // }

}