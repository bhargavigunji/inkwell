package com.inkwell.service;

import com.inkwell.dto.CommentResponseDTO;
import com.inkwell.entity.Comment;
import com.inkwell.entity.Post;
import com.inkwell.entity.Role;
import com.inkwell.entity.User;
import com.inkwell.exception.ForbiddenException;
import com.inkwell.exception.ResourceNotFoundException;
import com.inkwell.repo.CommentRepo;
import com.inkwell.repo.PostRepo;
import com.inkwell.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.inkwell.util.CurrentUserProvider;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    PostRepo postRepo;



    @Autowired
    CurrentUserProvider currentUserProvider;


    @Override
    public CommentResponseDTO createComment(Comment comment) {



        Post post = postRepo.findById(comment.getPost().getId()).orElseThrow(()-> new ResourceNotFoundException("Post not found"));



        User author = currentUserProvider.getCurrentUser();

        if(comment.getAuthor()!=null && !comment.getAuthor().getId().equals(author.getId())){
            throw new ForbiddenException("You cannot comment on behalf of other person");
        }

        comment.setPost(post);



        comment.setAuthor(author);
        Comment saved = commentRepo.save(comment);



        return CommentResponseDTO.fromComment(saved);
    }

    @Override
    public Page<CommentResponseDTO> getAllComments(Pageable pageable) {
        return commentRepo.findAll(pageable).map(CommentResponseDTO::fromComment);
    }

    @Override
    public Page<CommentResponseDTO> getCommentByPost(Long postId, Pageable pageable) {
        Post existingPost= postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found"));
        return commentRepo.findByPost(existingPost, pageable).map(CommentResponseDTO::fromComment);
    }

    @Override
    public void deleteComment(Long id) {



        Comment existingComment = commentRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment not found"));

        User currentUser = currentUserProvider.getCurrentUser();

        boolean isCommentAuthor= currentUser.getId().equals(existingComment.getAuthor().getId());
        boolean isPostAuthor = existingComment.getPost().getAuthor().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        if(!isCommentAuthor && !isPostAuthor && !isAdmin){
           throw new ForbiddenException("You are not allowed to delete this comment");
        }

        commentRepo.delete(existingComment);



    }


}
