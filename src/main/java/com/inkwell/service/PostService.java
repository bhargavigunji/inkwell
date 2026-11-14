package com.inkwell.service;

import com.inkwell.dto.PostResponseDTO;
import com.inkwell.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostResponseDTO createPost(Post post);
    Page<PostResponseDTO> getAllPosts(Pageable pageable);
    PostResponseDTO getPostById(Long id);
    PostResponseDTO updatePost(Long id, Post post);
    void deletePost(Long id);
    Page<PostResponseDTO> getPostsByUser(Long userId, Pageable pageable);
    Page<PostResponseDTO> getPostsByCategory(Long categoryId, Pageable pageable);
}
