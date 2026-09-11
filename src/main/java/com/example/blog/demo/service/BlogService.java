package com.example.blog.demo.service;

import com.example.blog.demo.modal.Blog;
import com.example.blog.demo.modal.User;
import com.example.blog.demo.repos.BlogRepo;
import com.example.blog.demo.repos.UserRepo;
import com.example.blog.demo.utils.BlogData;
import com.example.blog.demo.utils.BlogResponse;
import com.example.blog.demo.utils.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor

public class BlogService {

    private  final BlogRepo blogRepo;
    private  final UserRepo userRepo;

    public ResponseEntity<BlogResponse<BlogData>> createBlog(Blog blog, String email) {

        User user = userRepo.findByEmail(email)
                .orElse(null);

        if(user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BlogResponse<>(
                            false,
                            "User not found",
                            null
                    )
            );
        }
            blog.setUser(user);

            Blog savedBlogs = blogRepo.save(blog);
        BlogData response = new BlogData(
                savedBlogs.getId(),
                savedBlogs.getTitle(),
                savedBlogs.getContent(),
                savedBlogs.getMediaType(),
                savedBlogs.getMediaUrl(),
                savedBlogs.getCreatedAt()
        );
            return ResponseEntity.status(HttpStatus.OK).body(
                    new BlogResponse<>(
                            true,
                            "Blog created successfully",
                            response
                    )
            );



    }

    public ResponseEntity<BlogResponse<List<BlogData>>> bulkCreateBlogs(List<Blog> blogs, String email) {

        User user = userRepo.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BlogResponse<>(
                            false,
                            "User not found",
                            null
                    )
            );
        }

        if (blogs == null || blogs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BlogResponse<>(
                            false,
                            "No blogs provided for upload",
                            null
                    )
            );
        }

        List<Blog> savedBlogs = new ArrayList<>();
        for (Blog blog : blogs) {
            if (blog == null) {
                continue;
            }
            blog.setUser(user);
            savedBlogs.add(blogRepo.save(blog));
        }

        if (savedBlogs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BlogResponse<>(
                            false,
                            "No valid blog data provided",
                            null
                    )
            );
        }

        List<BlogData> response = savedBlogs.stream()
                .map(savedBlog -> new BlogData(
                        savedBlog.getId(),
                        savedBlog.getTitle(),
                        savedBlog.getContent(),
                        savedBlog.getMediaType(),
                        savedBlog.getMediaUrl(),
                        savedBlog.getCreatedAt()
                ))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(
                new BlogResponse<>(
                        true,
                        "Blogs created successfully",
                        response
                )
        );
    }

    public ResponseEntity<BlogResponse<BlogData>> updateBlog(String id , Blog blog, String email) {

        User user = userRepo.findByEmail(email)
                .orElse(null);

        if(user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BlogResponse<>(
                            false,
                            "User not found",
                            null
                    )
            );
        }

        Blog existingBlog = blogRepo.findById(id)
                .orElse(null);
        if(existingBlog == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BlogResponse<>(
                            false,
                            "Blog not found",
                            null
                    )
            );
        }
        if (!existingBlog.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new BlogResponse<>(
                            false,
                            "You are not allowed to update this blog",
                            null
                    )
            );
        }
        existingBlog.setTitle(blog.getTitle());
        existingBlog.setContent(blog.getContent());

        Blog updatedBlog = blogRepo.save(existingBlog);

        BlogData response = new BlogData(
                updatedBlog.getId(),
                updatedBlog.getTitle(),
                updatedBlog.getContent(),
                updatedBlog.getMediaType(),
                updatedBlog.getMediaUrl(),
                updatedBlog.getUpdatedAt()
        );
        return  ResponseEntity.status(HttpStatus.OK).body(
                new BlogResponse<>(
                        true,
                        "Blog updated successfully",
                        response
                )
        );


    }


    public ResponseEntity<BlogResponse<Void>> deleteBlog(String id, String email) {
        User user = userRepo.findByEmail(email)
                .orElse(null);

        if(user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BlogResponse<>(
                            false,
                            "User not found",
                            null
                    )
            );
        }

        Blog existingBlog = blogRepo.findById(id)
                .orElse(null);
        if(existingBlog == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BlogResponse<>(
                            false,
                            "Blog not found",
                            null
                    )
            );
        }
        if (!existingBlog.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new BlogResponse<>(
                            false,
                            "You are not allowed to delete this blog",
                            null
                    )
            );
        }
        blogRepo.delete(existingBlog);
        return  ResponseEntity.status(HttpStatus.OK).body(
                new BlogResponse<>(
                        true,
                        "Blog deleted successfully",
                        null
                )
        );

    }

    public ResponseEntity<BlogResponse<BlogData>> getBlog(String id, String email) {
        User user = userRepo.findByEmail(email)
                .orElse(null);

        if(user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BlogResponse<>(
                            false,
                            "User not found",
                            null
                    )
            );
        }

        Blog existingBlog = blogRepo.findById(id)
                .orElse(null);
        if(existingBlog == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BlogResponse<>(
                            false,
                            "Blog not found",
                            null
                    )
            );
        }
        if (!existingBlog.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new BlogResponse<>(
                            false,
                            "You are not allowed to view this blog",
                            null
                    )
            );
        }
        BlogData response = new BlogData(
                existingBlog.getId(),
                existingBlog.getTitle(),
                existingBlog.getContent(),
                existingBlog.getMediaType(),
                existingBlog.getMediaUrl(),
                existingBlog.getCreatedAt()
        );
        return  ResponseEntity.status(HttpStatus.OK).body(
                new BlogResponse<>(
                        true,
                        "Blog fetched successfully",
                        response
                )
        );

    }

    public ResponseEntity<BlogResponse<PageResponse<BlogData>>> getAllBlogs(
            String email,
            Pageable pageable
    ) {

        User user = userRepo.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BlogResponse<>(
                            false,
                            "User not found",
                            null
                    )
            );
        }

        Page<Blog> blogs = blogRepo.findByUser_Id(
                user.getId(),
                pageable
        );

        List<BlogData> content = blogs.map(blog -> new BlogData(
                blog.getId(),
                blog.getTitle(),
                blog.getContent(),
                blog.getMediaType(),
                blog.getMediaUrl(),
                blog.getCreatedAt()
        )).toList();

        PageResponse<BlogData> response = new PageResponse<>(
                content,
                blogs.getNumber(),
                blogs.getSize(),
                blogs.getTotalElements(),
                blogs.getTotalPages(),
                blogs.isFirst(),
                blogs.isLast()
        );

        return ResponseEntity.ok(
                new BlogResponse<>(
                        true,
                        "Blogs fetched successfully",
                        response
                )
        );
    }
}
