package com.inkwell.controller;

import com.inkwell.entity.Comment;
import com.inkwell.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping()
    public Comment createComment(@RequestBody Comment comment){
        return commentService.createComment(comment);
    }

    @GetMapping()
    public List<Comment> getAllComments(){
        return commentService.getAllComments();
    }

    @GetMapping("/posts/{postId}")
    public List<Comment> getCommentByPost(@PathVariable Long postId){
        return commentService.getCommentByPost(postId);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
    }


}
