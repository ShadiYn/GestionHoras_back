package com.pla.control;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    public static void main(String[] args) {
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        System.out.println(encode.matches("b", "$2a$10$CUbxXBZaQ6/L2F/aj0OEbOAR2hlctQ/swVVgKvlxqM1/aIvni6pE."));
    }
}