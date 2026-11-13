package com.inkwell.dto;

import com.inkwell.entity.Category;
import com.inkwell.entity.Post;
import com.inkwell.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {

    private Long id;
    private String title;
    private String content;
    private String coverImage;
    private Integer likes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserResponseDTO author;
    private Category category;
    private List<Tag> tags;
    private int commentCount;

    public static PostResponseDTO fromPost(Post post){
        return new PostResponseDTO(post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCoverImage(),
                post.getLikes(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                UserResponseDTO.fromUser(post.getAuthor()),
                post.getCategory(),
                post.getTags(),
                post.getComments()==null?0:post.getComments().size()

        );
    }
}
