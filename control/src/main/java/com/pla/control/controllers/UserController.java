package com.pla.control.controllers;

import com.pla.control.models.User;
import com.pla.control.models.UserDTO;
import com.pla.control.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usersettings")
public class UserController {

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	BCryptPasswordEncoder encode;

	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody UserDTO userUpdateDTO) {

		User userToUpdate = usersRepository.findUserById(id);
		if (userToUpdate == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		if (userUpdateDTO.getUsername() != null && !userUpdateDTO.getUsername().isBlank()) {
			userToUpdate.setUsername(userUpdateDTO.getUsername());
		}

		if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isBlank()) {
			userToUpdate.setPassword(encode.encode(userUpdateDTO.getPassword()));
		}

		if (userUpdateDTO.getName() != null && !userUpdateDTO.getName().isBlank()) {
			userToUpdate.setName(userUpdateDTO.getName());
		}

		if (userUpdateDTO.getLastName() != null && !userUpdateDTO.getLastName().isBlank()) {
			userToUpdate.setLast_name(userUpdateDTO.getLastName());
		}

		usersRepository.save(userToUpdate);

		return ResponseEntity.ok("User updated successfully");
	}

	@GetMapping
	public ResponseEntity<User> getUserSettings(UsernamePasswordAuthenticationToken upa) {
		User u = (User) upa.getPrincipal();
		User a = usersRepository.findUserById(u.getId()); 
		return ResponseEntity.ok(a);
	}
}
