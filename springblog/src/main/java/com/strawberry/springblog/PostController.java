package com.strawberry.springblog;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping 
    public ResponseEntity<ArrayList<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post blogPost) {
        try {
            Post createdPost = this.postService.addPost(blogPost.getTitle(), blogPost.getContent());
            return ResponseEntity.status(201).body(createdPost);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
    
    // @GetMapping("/{id}")
    // public ResponseEntity<?> getPost()

    @DeleteMapping("/{id}") 
    public ResponseEntity<String> deletePost(@PathVariable int id){
        try {
            if (postService.deletePost(postService.findPost(id))) {
                return ResponseEntity.ok().body("Post deleted successfully!");
            } 
            return ResponseEntity.status(401).body("Unauthorized user. Post could not be deleted.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } 
    }

    @PatchMapping("/{id}") // bug, throws 404 for both exceptions (no post found and incorrect argument given)
    public ResponseEntity<String> modifyPost(@PathVariable int id, @RequestParam String modificationType, @RequestBody String newText) {
        try {
            if (postService.modifyPost(id, modificationType, newText)) {
                return ResponseEntity.ok().body("Post successfully updated");
            }
            return ResponseEntity.status(401).body("Unauthorized user. Post could not be modified.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/{username}") // throws exception bc of user service 
    public ResponseEntity<?> getPostsbyAuthor(@PathVariable String username) {
        try {
           return ResponseEntity.ok().body(postService.getPostsbyAuthor(username)); 
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // user doesn't exist
        }
    }


}


