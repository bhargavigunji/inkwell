package com.inkwell.service;


import com.inkwell.dto.UserResponseDTO;
import com.inkwell.entity.Role;
import com.inkwell.entity.User;
import com.inkwell.exception.ForbiddenException;
import com.inkwell.exception.ResourceNotFoundException;
import com.inkwell.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.inkwell.util.CurrentUserProvider;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CurrentUserProvider currentUserProvider;

    @Override
    public User registerUser(User user) {
        long userCount =userRepo.count();
        if(userCount==0){
            user.setRole(Role.ADMIN);

        }else {
            user.setRole(Role.AUTHOR);

        }
        return userRepo.save(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {



      return userRepo.findAll().stream().map(UserResponseDTO::fromUser).collect(Collectors.toList());

    }

    @Override
    public UserResponseDTO getUserById(Long id) {

        User currentUser = currentUserProvider.getCurrentUser();


        if(currentUser.getRole() !=Role.ADMIN && !currentUser.getId().equals(id)){
            throw new ForbiddenException("Access Denied");

        }
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return UserResponseDTO.fromUser(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, User user) {
        User currentUser = currentUserProvider.getCurrentUser();

        if(!currentUser.getId().equals(id)){
            throw new ForbiddenException("You can only update your own profile");
        }

        currentUser.setUsername(user.getUsername());

        currentUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser =  userRepo.save(currentUser);

        return UserResponseDTO.fromUser(savedUser);
    }

    @Override
    public void deleteUser(Long id) {

        User existingUser =userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));

        if(existingUser.getRole()== Role.ADMIN){
            throw new ForbiddenException("Admin cannot be deleted");
        }

        userRepo.delete(existingUser);

    }

    public User getUserByEmail(String email){
        return userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));
    }
}
