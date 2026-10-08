package com.inkwell.controller;

import com.inkwell.config.CustomUserDetailsService;
import com.inkwell.config.JwtUtil;
import com.inkwell.dto.AuthResponse;
import com.inkwell.dto.LoginRequest;
import com.inkwell.dto.RegisterRequest;
import com.inkwell.entity.User;
import com.inkwell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserDetailsService userDetailsService;






    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request){
        User user= new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userService.registerUser(user);

        String token = jwtUtil.generateToken(savedUser.getEmail());

        return  new AuthResponse(token, savedUser.getRole().name());
    }


    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String token = jwtUtil.generateToken(request.getEmail());

        User user = userService.getUserByEmail(request.getEmail());

        return new AuthResponse(token, user.getRole().name());

    }
}
