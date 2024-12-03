package com.pla.control.controllers;

import com.pla.control.models.User;
import com.pla.control.models.UserDTO;
import com.pla.control.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public class RegisterController {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder encode;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {

		if (userDTO.getUsername() == null || userDTO.getUsername().isBlank()) {
			return ResponseEntity.badRequest().body("The username is required");
		}

		if (userDTO.getName() == null || userDTO.getName().isBlank()) {
			return ResponseEntity.badRequest().body("The name is required");
		}

		if (userDTO.getPassword() == null || userDTO.getPassword().isBlank()) {
			return ResponseEntity.badRequest().body("The password is required");
		}

		if (userDTO.getEurosPerHour() == 0) {
			return ResponseEntity.badRequest().body("Your salary per hour is required");
		}

        if (userDTO.isFlexible() == null) {
            return ResponseEntity.badRequest().body("Your schedule type is required");
        }

		if (usersRepository.existsByUsername(userDTO.getUsername())) {
			return ResponseEntity.badRequest().body("This username already exists");
		}

		User newUser = new User(userDTO.getName(), userDTO.getLastName(), userDTO.getUsername(),
				encode.encode(userDTO.getPassword()),userDTO.getEurosPerHour(),userDTO.isFlexible());

		usersRepository.save(newUser);

		return ResponseEntity.ok("Registration successful");
	}

}
