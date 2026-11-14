package com.inkwell.service;

import com.inkwell.dto.PostResponseDTO;
import com.inkwell.entity.*;
import com.inkwell.exception.ForbiddenException;
import com.inkwell.exception.ResourceNotFoundException;
import com.inkwell.repo.CategoryRepo;
import com.inkwell.repo.PostRepo;
import com.inkwell.repo.TagRepo;
import com.inkwell.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.inkwell.util.CurrentUserProvider;

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

    @Autowired
    CurrentUserProvider currentUserProvider;


    @Override
    public PostResponseDTO createPost(Post post) {

        User author = currentUserProvider.getCurrentUser();

        if(post.getAuthor()!=null && !author.getId().equals(post.getAuthor().getId())){
            throw new ForbiddenException("You cannot create a post on behalf of another user");
        }

        post.setAuthor(author);

        Category category =categoryRepo.findById(post.getCategory().getId()).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
        post.setCategory(category);

        List<Tag> tags = post.getTags().stream()
                .map(tag -> tagRepo.findById(tag.getId())
                        .orElseThrow(()-> new ResourceNotFoundException("Tag not found"))).collect(Collectors.toList());

        post.setTags(tags);

        if(post.getLikes()==null){
            post.setLikes(0);
        }



       Post saved = postRepo.save(post);
        return PostResponseDTO.fromPost(saved);
    }


    @Override
    public Page<PostResponseDTO> getAllPosts(Pageable pageable) {
        return postRepo.findAll(pageable).map(PostResponseDTO::fromPost);
    }

    @Override
    public PostResponseDTO getPostById(Long id) {
        Post post= postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post not found"));
        return PostResponseDTO.fromPost(post);
    }

    @Override
    public PostResponseDTO updatePost(Long id, Post post) {
        Post existingPost =postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post not found"));
        if(!existingPost.getAuthor().getId().equals(currentUserProvider.getCurrentUser().getId())){
            throw new ForbiddenException("You can only edit your own posts");
        }
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setCoverImage(post.getCoverImage());


            Category category = categoryRepo.findById(post.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            existingPost.setCategory(category);

            List<Tag> tags = post.getTags().stream()
                    .map(tag->tagRepo.findById(tag.getId())
                            .orElseThrow(()-> new ResourceNotFoundException("Tag not found"))).collect(Collectors.toList());

            existingPost.setTags(tags);


            Post saved= postRepo.save(existingPost);
            return PostResponseDTO.fromPost(saved);




    }





    @Override
    public void deletePost(Long id) {



        Post existingPost =postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post not found"));

        User currentUser = currentUserProvider.getCurrentUser();
        boolean isOwner = existingPost.getAuthor().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole()== Role.ADMIN;

        if(!isOwner && !isAdmin){
            throw new ForbiddenException("You can only delete your own posts");
        }
        postRepo.delete(existingPost);





    }

    @Override
    public Page<PostResponseDTO> getPostsByUser(Long userId, Pageable pageable) {

       User existingUser=  userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));

        return postRepo.findByAuthor(existingUser, pageable).map(PostResponseDTO::fromPost);

    }

    @Override
    public Page<PostResponseDTO> getPostsByCategory(Long categoryId, Pageable pageable) {
        Category existingCategory = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found"));


        return postRepo.findByCategory(existingCategory, pageable).
                map(PostResponseDTO::fromPost);

    }



}
