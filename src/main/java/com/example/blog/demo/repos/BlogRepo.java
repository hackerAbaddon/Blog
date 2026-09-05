package com.example.blog.demo.repos;

import com.example.blog.demo.modal.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlogRepo extends JpaRepository<Blog, String> {

    Page<Blog> findAllByUserId(String userId, Pageable pageable);
}
