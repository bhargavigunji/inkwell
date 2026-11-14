package com.inkwell.controller;

import com.inkwell.dto.PostResponseDTO;
import com.inkwell.entity.Post;
import com.inkwell.service.AiService;
import com.inkwell.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    AiService aiService;

    @PostMapping()
    public PostResponseDTO createPost(@RequestBody Post post){
        return postService.createPost(post);
    }

    @GetMapping()
    public Page<PostResponseDTO> getAllPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size ){
        Pageable pageable= PageRequest.of(page,size, Sort.by("createdAt").descending());
        return postService.getAllPosts(pageable);
    }

    @GetMapping("/{id}")
    public PostResponseDTO getPostById(@PathVariable Long id){
        return postService.getPostById(id);
    }

    @PutMapping("/{id}")
    public PostResponseDTO updatePost(@PathVariable Long id, @RequestBody Post post){
        return postService.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id){
         postService.deletePost(id);
    }

    @GetMapping("/user/{userId}")
    public Page<PostResponseDTO> getPostByUser(@PathVariable Long userId,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Pageable pageable =PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postService.getPostsByUser(userId, pageable);
    }

    @GetMapping("/category/{categoryId}")
    public Page<PostResponseDTO> getPostByCategory(@PathVariable Long categoryId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Pageable pageable= PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postService.getPostsByCategory(categoryId, pageable);
    }


    @GetMapping("/{id}/summarize")
    public String summarizePost(@PathVariable Long id){
       PostResponseDTO existingPost=  postService.getPostById(id);

       return aiService.summarizePost(existingPost.getContent());

    }
}
