package com.example.blog.demo.repos;

import com.example.blog.demo.modal.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogRepo extends MongoRepository<Blog, String> {

    Page<Blog> findByUser_Id(String userId, Pageable pageable);
}
