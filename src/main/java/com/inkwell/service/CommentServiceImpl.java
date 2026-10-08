package com.inkwell.service;

import com.inkwell.entity.Comment;
import com.inkwell.entity.Post;
import com.inkwell.entity.User;
import com.inkwell.repo.CommentRepo;
import com.inkwell.repo.PostRepo;
import com.inkwell.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    PostRepo postRepo;

    @Autowired
    UserRepo userRepo;




    @Override
    public Comment createComment(Comment comment) {

        Post post = postRepo.findById(comment.getPost().getId()).orElseThrow(()-> new RuntimeException("Post not found"));

        comment.setPost(post);

        User author = userRepo.findById(comment.getAuthor().getId()).orElseThrow(()-> new RuntimeException("Author not found"));

        comment.setAuthor(author);





        return commentRepo.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepo.findAll();
    }

    @Override
    public List<Comment> getCommentByPost(Long postId) {
        Post existingPost= postRepo.findById(postId).orElseThrow(()-> new RuntimeException("Post not found"));
        return commentRepo.findByPost(existingPost);
    }

    @Override
    public void deleteComment(Long id) {

        Comment existingComment = commentRepo.findById(id).orElseThrow(()-> new RuntimeException("Comment not found"));

        commentRepo.delete(existingComment);

    }
}
