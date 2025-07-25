package com.strawberry.springblog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.ArrayList;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")

public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public ArrayList<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{username}/insights")
    public ResponseEntity<String> getUser(@PathVariable String username) {
        try {
            int numPosts = userService.countPostsbyAuthor(username);
            return ResponseEntity.ok().body(username + " has " + numPosts + " posts!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // username does not exist
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User newUser) {
        try {
            User createdUser = this.userService.addUser(newUser.getEmail(), newUser.getPassword(), newUser.getFirstName(), newUser.getLastName(), newUser.getUsername());
            return ResponseEntity.status(201).body(createdUser);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(409).body(e.getMessage());
        }

    }

    @PutMapping("/login")
    public ResponseEntity<?> loginToUser(@RequestParam String username, @RequestBody String password) {
        try {
            if (this.userService.loginToUser(this.userService.findUser(username), password)) {
                return ResponseEntity.ok().body("Login Success!");
            }
            return ResponseEntity.status(401).body("Incorrect Password. Login Failed."); // bug, if you're already signed in, try to re-log in to your acc w wrong psswd. it says failed, but you're still logged in
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // username does not exist
        }
    }

    @PutMapping("/logout")
    public ResponseEntity<String> logoutOfUser() {
        if (this.userService.logOutOfUser()) {
            return ResponseEntity.ok().body("Successfully logged out!");
        }
        return ResponseEntity.badRequest().body("Unable to sign out because no user is signed in.");
    }

    @PatchMapping("/{username}") // bug - gives access to user and admin to change account details. change so its just the user
    public ResponseEntity<String> modifyUser(@PathVariable String username, @RequestParam String field, @RequestBody String newText) {
        try {
            if (userService.modifyUser(userService.findUser(username), field, newText)) {
                return ResponseEntity.ok().body("User successfully updated");
            }
            return ResponseEntity.status(401).body("Unauthorized user. Account could not be modified.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // username does not exist
        }
    }

    @DeleteMapping("/{username}") 
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        try {
            User toBeDeletedUser = userService.findUser(username); // throws argument 404 exception if does not exist
            try {
                if (userService.hasAuthorization(toBeDeletedUser)) {
                    userService.deleteUser(toBeDeletedUser); // user deleted
                    return ResponseEntity.ok().body("User successfully deleted.");
                }
                return ResponseEntity.status(401).body("Unauthorized user. Account could not be deleted.");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(403).body(e.getMessage()); // cannot delete a user
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/account")
    public ResponseEntity<?> viewCurrentUser() {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            return ResponseEntity.ok().body(currentUser);
        }
        return ResponseEntity.ok().body("Sign in to view your account!");
        }
}
    
