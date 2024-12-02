package com.pla.control.controllers;

import com.pla.control.models.Register;
import com.pla.control.models.User;
import com.pla.control.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;


@RequestMapping("/register")
@RestController
public class RegisterController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder encode;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody Register register) {


        if (register.getUsername() == null || register.getUsername().isBlank()) {
            return ResponseEntity.badRequest().body("The username is required");
        }

        if (register.getName() == null || register.getName().isBlank()) {
            return ResponseEntity.badRequest().body("The name is required");
        }

        if (register.getPassword() == null || register.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body("The password is required");
        }

        // Verificar si el usuario ya existe
        if (usersRepository.existsByUsername(register.getUsername())) {
            return ResponseEntity.badRequest().body("This username already exists");
        }

        // Crear y guardar el nuevo usuario
        User newUser = new User();
        newUser.setUsername(register.getUsername());
        newUser.setName(register.getName());
        newUser.setLast_name(register.getLast_name());
        newUser.setPassword(encode.encode(register.getPassword()));
        newUser.setEnabled(true);
        newUser.setCreated_at(LocalDateTime.now());
        newUser.setUpdated_at(LocalDateTime.now());
        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setCredentialsNonExpired(true);

        usersRepository.save(newUser);

        return ResponseEntity.ok("Registration successful");
    }

}

