package com.example.blog.demo.controller;

import com.example.blog.demo.modal.User;
import com.example.blog.demo.service.AuthService;
import com.example.blog.demo.service.UserService;
import com.example.blog.demo.utils.ApiResponce;
import com.example.blog.demo.utils.LoginResponce;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponce<User>> save( @RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponce> login( @RequestBody User user) {
        return authService.login(user);
    }
}
