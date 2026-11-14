package com.inkwell.repo;

import com.inkwell.entity.Comment;
import com.inkwell.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CommentRepo extends JpaRepository<Comment,Long> {

    Page<Comment> findByPost(Post post, Pageable pageable);
}
