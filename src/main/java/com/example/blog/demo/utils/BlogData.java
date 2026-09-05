package com.example.blog.demo.utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogData {

    private String id;
    private String title;
    private String content;
    private String mediaType;
    private String mediaUrl;
    private LocalDateTime createdAt;


}
