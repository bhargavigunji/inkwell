package com.inkwell.repo;


import com.inkwell.entity.Comment;
import com.inkwell.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Long> {

    List<Comment> findByPost(Post post);
}
