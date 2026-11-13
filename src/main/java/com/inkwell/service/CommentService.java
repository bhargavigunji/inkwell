package com.inkwell.service;

import com.inkwell.dto.CommentResponseDTO;
import com.inkwell.entity.Comment;


import java.util.List;

public interface CommentService {
    CommentResponseDTO createComment(Comment comment);
    List<CommentResponseDTO> getAllComments();
    List<CommentResponseDTO> getCommentByPost(Long postId);
    void deleteComment(Long id);
}
