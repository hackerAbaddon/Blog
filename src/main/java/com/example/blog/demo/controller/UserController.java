package com.example.blog.demo.controller;


import com.example.blog.demo.modal.User;
import com.example.blog.demo.service.UserService;
import com.example.blog.demo.utils.ApiResponce;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponce<User>> save(@Valid  @RequestBody User user) {
        return userService.save(user);
    }


}
