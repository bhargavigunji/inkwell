package com.inkwell.service;

import com.inkwell.dto.PostResponseDTO;
import com.inkwell.entity.Post;

import java.util.List;

public interface PostService {
    PostResponseDTO createPost(Post post);
    List<PostResponseDTO> getAllPosts();
    PostResponseDTO getPostById(Long id);
    PostResponseDTO updatePost(Long id, Post post);
    void deletePost(Long id);
    List<PostResponseDTO> getPostsByUser(Long userId);
    List<PostResponseDTO> getPostsByCategory(Long categoryId);
}
