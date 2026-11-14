package com.inkwell.repo;

import com.inkwell.entity.Category;
import com.inkwell.entity.Post;
import com.inkwell.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    Page<Post> findByAuthor(User author, Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);
}
