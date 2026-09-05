package com.example.blog.demo.service;
import com.example.blog.demo.modal.User;
import com.example.blog.demo.repos.UserRepo;
import com.example.blog.demo.utils.ApiResponce;
import com.example.blog.demo.utils.LoginResponce;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;


@Service
@AllArgsConstructor
public class AuthService {
    private  final UserRepo repo;
    private  final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public ResponseEntity<ApiResponce<User>> register(@Valid User user){
        User existingUser = repo.findByEmail(user.getEmail())
                .orElse(null);
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponce(
                            false,
                            "User Already Exist",
                            null
                    )
            );
        }
        user.setName(user.getName());
        user.setEmail(user.getEmail());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponce<>(
                true,
                 "User Register successfully",
                        user
                )
        );
    }



    public ResponseEntity<LoginResponce> login(@Valid @RequestBody User user){
        User userData = repo.findByEmail(user.getEmail()).orElse(null);
        if (userData == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new LoginResponce(
                            false,
                            "Something went wrong",
                            null
                    )
            );        }
        boolean match = passwordEncoder.matches(user.getPassword(), userData.getPassword());
        if (!match) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new LoginResponce(
                            false,
                            "Email or password is incorrect",
                            null
                    )
            );
        }
        String token = jwtService.generateToken(userData.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(
                new LoginResponce(
                        true,
                        "User Login successfully",
                        token
                )
        );

    }
}
