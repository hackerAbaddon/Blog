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

    public ResponseEntity<?> getUser(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return ResponseEntity.ok(
                new ApiResponce<>(
                        true,
                        "User fetched successfully",
                        user
                )
        );
    }


    public  ResponseEntity<ApiResponce<?>> update(User user){

        User UserData = userRepo.findByEmail(user.getEmail()).orElse(null);
        if(UserData == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponce<>(
                            false,
                            "User not found",
                            null
                    )
            );
        }
        UserData.setName(user.getName());
        UserData.setPhoneNumber(user.getPhoneNumber());
        User updatedUser = userRepo.save(UserData);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponce<>(
                        true,
                        "User updated successfully",
                        updatedUser
                )
        );

    }

    public ResponseEntity<ApiResponce<Void>> delete(String id, String email) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponce<>(
                            false,
                            "User not found",
                            null
                    )
            );
        }
        if (!user.getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ApiResponce<>(
                            false,
                            "You are not authorized to delete this user",
                            null
                    )
            );
        }
        userRepo.delete(user);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponce<>(
                        true,
                        "User deleted successfully",
                        null
                )
        );
    }

}
