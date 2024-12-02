package com.pla.control.controllers;

import com.pla.control.models.User;
import com.pla.control.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/usersettings")
public class UserController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    BCryptPasswordEncoder encode;

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePassword(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) {

        if (!requestBody.containsKey("newPassword") || requestBody.get("newPassword").isBlank()) {
            return ResponseEntity.badRequest().body("New password is required");
        }

        String newPassword = requestBody.get("newPassword");

        User userToUpdate = usersRepository.findUserById(id);
        if (userToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        userToUpdate.setPassword(encode.encode(newPassword));
        usersRepository.save(userToUpdate);

        return ResponseEntity.ok("Password updated successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUsername(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) {

        if (!requestBody.containsKey("newUsername") || requestBody.get("newUsername").isBlank()) {
            return ResponseEntity.badRequest().body("New username is required");
        }

        String newUsername = requestBody.get("newUsername");

        User userToUpdate = usersRepository.findUserById(id);
        if (userToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        userToUpdate.setUsername(newUsername);
        usersRepository.save(userToUpdate);

        return ResponseEntity.ok("Username updated successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateName(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) {

        if (!requestBody.containsKey("newName") || requestBody.get("newName").isBlank()) {
            return ResponseEntity.badRequest().body("New Name is required");
        }

        String newName = requestBody.get("newName");

        User userToUpdate = usersRepository.findUserById(id);
        if (userToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        userToUpdate.setName(newName);
        usersRepository.save(userToUpdate);

        return ResponseEntity.ok("Name updated successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateLastName(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) {

        if (!requestBody.containsKey("newLasName") || requestBody.get("newLasName").isBlank()) {
            return ResponseEntity.badRequest().body("New Lastname is required");
        }

        String newLasName = requestBody.get("newLasName");

        User userToUpdate = usersRepository.findUserById(id);
        if (userToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        userToUpdate.setLast_name(newLasName);
        usersRepository.save(userToUpdate);

        return ResponseEntity.ok("Lastname updated successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserPassword(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) {

        if (!requestBody.containsKey("newPassword") || requestBody.get("newPassword").isBlank()) {
            return ResponseEntity.badRequest().body("newPassword is required");
        }

        String newPassword = requestBody.get("newPassword");

        User userToUpdate = usersRepository.findUserById(id);
        if (userToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        userToUpdate.setPassword(encode.encode(newPassword));
        usersRepository.save(userToUpdate);

        return ResponseEntity.ok("Password updated successfully");
    }
}
