package com.pla.control;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    public static void main(String[] args) {
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        System.out.println(encode.matches("1111", "$2a$10$hiQIrKDcFWVi2QjQgBZlG.MGXYLjF44YD6n0jTEO6RTzMCI4ZO9g2"));
    }
}