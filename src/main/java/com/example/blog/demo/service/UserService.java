package com.example.blog.demo.service;

import com.example.blog.demo.modal.User;
import com.example.blog.demo.repos.UserRepo;
import com.example.blog.demo.utils.ApiResponce;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public ResponseEntity<ApiResponce<User>> save(User user) {
//        if(user.getName().isBlank() || user.getName().isEmpty()){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new ApiResponce<>(
//                            false,
//                            "Username is required",
//                            null
//                    )
//            );
//        }
//        if(user.getEmail().isBlank() || user.getEmail().isEmpty()){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new ApiResponce<>(
//                            false,
//                            "Email is required",
//                            null
//                    )
//            );
//        }
        User users = userRepo.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponce<>(
                        true,
                        "User save successfully",
                        users
                )
        );
    }

}
