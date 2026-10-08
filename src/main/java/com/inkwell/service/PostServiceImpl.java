package com.inkwell.service;

import com.inkwell.entity.Category;
import com.inkwell.entity.Post;
import com.inkwell.entity.Tag;
import com.inkwell.entity.User;
import com.inkwell.repo.CategoryRepo;
import com.inkwell.repo.PostRepo;
import com.inkwell.repo.TagRepo;
import com.inkwell.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepo postRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    TagRepo tagRepo;

    @Override
    public Post createPost(Post post) {

        User author =userRepo.findById(post.getAuthor().getId()).orElseThrow(()-> new RuntimeException("User not found"));

        post.setAuthor(author);

        Category category =categoryRepo.findById(post.getCategory().getId()).orElseThrow(()-> new RuntimeException("Category not found"));
        post.setCategory(category);

        List<Tag> tags = post.getTags().stream()
                .map(tag -> tagRepo.findById(tag.getId())
                        .orElseThrow(()-> new RuntimeException("Tag not found"))).collect(Collectors.toList());

        post.setTags(tags);

        if(post.getLikes()==null){
            post.setLikes(0);
        }



        return postRepo.save(post);
    }


    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepo.findById(id).orElseThrow(()-> new RuntimeException("Post not found"));
    }

    @Override
    public Post updatePost(Long id, Post post) {
        Post existingPost =postRepo.findById(id).orElseThrow(()-> new RuntimeException("Post not found"));
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setCoverImage(post.getCoverImage());

        Category category = categoryRepo.findById(post.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existingPost.setCategory(category);


        existingPost.setTags(post.getTags());

        return postRepo.save(existingPost);
    }

    @Override
    public void deletePost(Long id) {

        Post existingPost =postRepo.findById(id).orElseThrow(()-> new RuntimeException("Post not found"));

        postRepo.delete(existingPost);

    }

    @Override
    public List<Post> getPostsByUser(Long userId) {

       User existingUser=  userRepo.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));

        return postRepo.findByAuthor(existingUser);
    }

    @Override
    public List<Post> getPostsByCategory(Long categoryId) {
        Category existingCategory = categoryRepo.findById(categoryId).orElseThrow(()-> new RuntimeException("Category not found"));


        return postRepo.findByCategory(existingCategory);

    }
}
