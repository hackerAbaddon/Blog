package com.example.blog.demo.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BlogResponse <T>{

    public boolean success;
    public String message;
    public T data;
}
