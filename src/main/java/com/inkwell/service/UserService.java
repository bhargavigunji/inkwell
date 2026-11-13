package com.inkwell.service;

import com.inkwell.dto.UserResponseDTO;
import com.inkwell.entity.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO updateUser(Long id, User user);
    void deleteUser(Long id);
    User getUserByEmail(String email);
}
