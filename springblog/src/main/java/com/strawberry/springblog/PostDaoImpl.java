// package com.strawberry.springblog;
// import org.springframework.stereotype.Repository;

// import java.util.concurrent.ConcurrentHashMap;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;

// @Repository
// public class PostDaoImpl implements PostRepository {

//     private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

//     public PostDaoImpl() {}

//     @Override
//     public List<Post> findAll() {
//         return new ArrayList<>(posts.values());
//     }

//     @Override
//     public Post findById(int postId) {
//         if (existsById(postId)) {
//             return posts.get(postId);
//         } 
//         return null;
//     }

//     @Override 
//     public Post save(Post post) {
//         posts.put(post.getId(), post);
//         return post;
//     }

//     @Override // weird, check this
//     public Post update(int postId, Post post) { // tbh why can't i just replace the whole obj???
//         Post existingPost = posts.get(postId);
//         if (existingPost != null) {
//             if (post.getTitle() != null) {
//                 existingPost.setTitle(post.getTitle());
//             }
//             if (post.getContent() != null) {
//                 existingPost.changeContent(post.getContent());
//             } 
//             posts.put(postId, existingPost);
//             return existingPost;
//         }
//         return null;
//     }

//     @Override
//     public boolean deleteById(int postId) {
//         if (posts.containsKey(postId)) {
//             posts.remove(postId);
//             return true;
//         }
//         return false;
//     }

//     @Override
//     public boolean existsById(int postId) {
//         return posts.containsKey(postId);
//     }

//     @Override 
//     public void deleteAllByAuthorId(int userId) { // external code will check if user is null before passing it into the function
//         for (Post post : findAll()) {
//             if (post.getAuthorId() == userId) {
//                 posts.remove(post.getId());
//             }
//         }
//     }

//     @Override
//     public void deleteAllPosts() {
//         posts.clear();
//     }

// }
