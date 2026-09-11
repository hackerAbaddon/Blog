package com.example.blog.demo.controller;
import com.example.blog.demo.modal.Blog;
import com.example.blog.demo.service.BlogService;
import com.example.blog.demo.utils.BlogData;
import com.example.blog.demo.utils.BlogResponse;
import com.example.blog.demo.utils.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;


@RestController
@RequestMapping("/api/blog")
@AllArgsConstructor
public class BlogController {

    private final BlogService BlogService;

    @PostMapping("/create")
    ResponseEntity<BlogResponse<BlogData>> createBlog(@RequestBody  Blog blog, Authentication authentication) {
        String email = authentication.getName();
         return BlogService.createBlog(blog,email);
    }

    @PostMapping("/bulk-create")
    ResponseEntity<BlogResponse<List<BlogData>>> bulkCreateBlogs(@RequestBody List<Blog> blogs, Authentication authentication) {
        String email = authentication.getName();
        return BlogService.bulkCreateBlogs(blogs, email);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<BlogResponse<BlogData>> updateBlog(@PathVariable String id, @RequestBody Blog blog, Authentication authentication) {
        String email = authentication.getName();
        return BlogService.updateBlog(id, blog, email);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<BlogResponse<Void>> deleteBlog(@PathVariable String id, Authentication authentication) {
        String email = authentication.getName();
        return BlogService.deleteBlog(id, email);}


    @GetMapping("/get/{id}")
    ResponseEntity<BlogResponse<BlogData>> getBlog(@PathVariable String id, Authentication authentication) {
        String email = authentication.getName();
        return BlogService.getBlog(id, email);}

    @GetMapping("/get-all")
    ResponseEntity<BlogResponse<PageResponse<BlogData>>> getAllBlogs(Authentication authentication, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        String email = authentication.getName();
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );
        return BlogService.getAllBlogs(email,pageable);}
}
