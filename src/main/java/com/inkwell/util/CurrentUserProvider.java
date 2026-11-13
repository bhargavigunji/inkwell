package util;

import com.inkwell.entity.User;
import com.inkwell.exception.ResourceNotFoundException;
import com.inkwell.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserProvider {


    @Autowired
    UserRepo userRepo;

    public User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));

    }
}
