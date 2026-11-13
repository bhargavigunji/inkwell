package com.inkwell.dto;

import com.inkwell.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long postId;
    private UserResponseDTO author;


    public static CommentResponseDTO fromComment(Comment comment){
        return new CommentResponseDTO(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getPost().getId(),
                UserResponseDTO.fromUser(comment.getAuthor())
        );


    }
}
