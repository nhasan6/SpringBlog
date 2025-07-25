package com.strawberry.springblog;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId", columnDefinition = "INT")
    @JsonProperty("id")
    private int id; 

    @Column(name = "Email", nullable = false, columnDefinition = "NVARCHAR(100)")
    @JsonProperty("email")
    @NotBlank
    @Email (message = "Invalid email address")
    private String email;

    @Column(name = "Username", nullable = false, columnDefinition = "NVARCHAR(30)")
    @JsonProperty("username")
    @NotBlank (message = "Username is required")
    private String username; // the handle under which their posts will be shared 

    @Column(name = "Password", nullable = false, columnDefinition = "NVARCHAR(30)")
    @JsonProperty("password")
    @NotBlank (message = "Password is required")
    private String password;

    @Column(name = "FirstName", nullable = false, columnDefinition = "NVARCHAR(50)")
    @JsonProperty("firstName")
    @NotBlank (message = "First Name is required")
    private String firstName;

    @Column(name = "LastName", columnDefinition = "NVARCHAR(50)")
    @JsonProperty("lastName")
    private String lastName;

    // public static int userCount = 0; // questionable?
    
    public User() {
        this.id = 0; //default constructor for JSON deserialization (doesn't increment id counter)
    }

    public User(String email, String password, String firstName, String lastName, String username) {
       this.email = email;
       this.password = password;
       this.firstName = firstName;
       this.lastName = lastName;
       this.username = username;
    }

    // // admin has userID = 1. 
    // // When blog is wiped (admin gets deleted), userCount must be reset so a new admin can be initialized
    // public static void resetUserCount() { 
    //     userCount = 0;
    // }

    public int getId() {
        return this.id;
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

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setFirstName(String newName) { 
        this.firstName = newName;
    }

    public void setLastName(String newName) { 
        this.lastName = newName;
    }

    public void setUsername(String newUsername) { 
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
        return this.firstName + " " + this.lastName + " | " + this.username;
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