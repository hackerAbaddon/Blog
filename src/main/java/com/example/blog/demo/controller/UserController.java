package com.example.blog.demo.controller;


import com.example.blog.demo.modal.User;
import com.example.blog.demo.service.UserService;
import com.example.blog.demo.utils.ApiResponce;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponce<User>> save(@Valid  @RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {

        String email = authentication.getName();

        return userService.getUser(email);
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponce<?>> update(@Valid @RequestBody User user, Authentication authentication) {
        String email = authentication.getName();
        user.setEmail(email); // Ensure the email is set to the authenticated user's email
        return userService.update(user);  }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponce<Void>> delete(@PathVariable String id, Authentication authentication) {
        String email = authentication.getName();
        return userService.delete(id, email);
    }
}
