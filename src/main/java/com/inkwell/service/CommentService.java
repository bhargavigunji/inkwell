package com.inkwell.service;

import com.inkwell.entity.Comment;


import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);
    List<Comment> getAllComments();
    List<Comment> getCommentByPost(Long postId);
    void deleteComment(Long id);
}
