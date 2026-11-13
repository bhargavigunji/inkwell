package com.inkwell.controller;

import com.inkwell.dto.PostResponseDTO;
import com.inkwell.entity.Post;
import com.inkwell.service.AiService;
import com.inkwell.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;

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
    public List<PostResponseDTO> getAllPosts(){
        return postService.getAllPosts();
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
    public List<PostResponseDTO> getPostByUser(@PathVariable Long userId){
        return postService.getPostsByUser(userId);
    }

    @GetMapping("/category/{categoryId}")
    public List<PostResponseDTO> getPostByCategory(@PathVariable Long categoryId){
        return postService.getPostsByCategory(categoryId);
    }


    @GetMapping("/{id}/summarize")
    public String summarizePost(@PathVariable Long id){
       PostResponseDTO existingPost=  postService.getPostById(id);

       return aiService.summarizePost(existingPost.getContent());

    }
}
