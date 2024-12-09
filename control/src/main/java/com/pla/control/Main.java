package com.pla.control;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    public static void main(String[] args) {
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        System.out.println(encode.matches("1111", "$2a$10$LyT54qd4LnxUafRB/oOEtuUrNMMBv8wsNccyxPk2X/WYvFkTNknPm"));
    }
}