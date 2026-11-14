package com.inkwell.service;

import com.inkwell.dto.CommentResponseDTO;
import com.inkwell.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface CommentService {
    CommentResponseDTO createComment(Comment comment);
    Page<CommentResponseDTO> getAllComments(Pageable pageable);
    Page<CommentResponseDTO> getCommentByPost(Long postId, Pageable pageable);
    void deleteComment(Long id);
}
