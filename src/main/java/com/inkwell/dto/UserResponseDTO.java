package com.inkwell.dto;

import com.inkwell.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;


    private LocalDateTime createdAt;



    public static UserResponseDTO fromUser( User user){
        return new UserResponseDTO(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getCreatedAt());
    }

}
