package com.inkwell.service;


import com.inkwell.entity.Role;
import com.inkwell.entity.User;
import com.inkwell.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepo userRepo;

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
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser =userRepo.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        return userRepo.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {

        User existingUser =userRepo.findById(id).orElseThrow(()-> new RuntimeException("User not found"));

        if(existingUser.getRole()== Role.ADMIN){
            throw new RuntimeException("Admin cannot be deleted");
        }

        userRepo.delete(existingUser);

    }

    public User getUserByEmail(String email){
        return userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));
    }
}
