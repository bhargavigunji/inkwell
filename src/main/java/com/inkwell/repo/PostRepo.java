package com.inkwell.repo;

import com.inkwell.entity.Category;
import com.inkwell.entity.Post;
import com.inkwell.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByAuthor(User author);
    List<Post> findByCategory(Category category);
}
