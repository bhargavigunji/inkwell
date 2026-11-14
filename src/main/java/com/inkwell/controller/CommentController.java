package com.inkwell.controller;

import com.inkwell.dto.CommentResponseDTO;
import com.inkwell.entity.Comment;
import com.inkwell.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping()
    public CommentResponseDTO createComment(@RequestBody Comment comment){
        return commentService.createComment(comment);
    }

    @GetMapping()
    public Page<CommentResponseDTO> getAllComments(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return commentService.getAllComments(pageable);
    }

    @GetMapping("/posts/{postId}")
    public Page<CommentResponseDTO> getCommentByPost(@PathVariable Long postId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return commentService.getCommentByPost(postId, pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
    }


}
