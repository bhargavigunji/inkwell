package com.inkwell.service;

import com.inkwell.entity.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post);
    List<Post> getAllPosts();
    Post getPostById(Long id);
    Post updatePost(Long id, Post post);
    void deletePost(Long id);
    List<Post> getPostsByUser(Long userId);
    List<Post> getPostsByCategory(Long categoryId);
}
