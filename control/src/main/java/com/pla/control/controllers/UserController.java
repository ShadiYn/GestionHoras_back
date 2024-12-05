package com.pla.control.controllers;

import com.pla.control.models.User;
import com.pla.control.models.UserDTO;
import com.pla.control.repositories.UsersRepository;

import java.security.Principal;

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

	// ------------------------------------------------------------------------
	// Update User
	// ------------------------------------------------------------------------
	
	/* 
	 * Modifica la informacion del usuario
	 * 	Entrada:
	 * 		- upa: token del usuario registrado.
	 * 		- UserDTO user: Objeto userDTO sin usar la contraseña
	 * 	Salida:
	 * 		- 
	 * */
	@PutMapping("/updateuser")
	public ResponseEntity<String> updateUser(UsernamePasswordAuthenticationToken upa, @RequestBody UserDTO userUpdateDTO) {
		User user = (User) upa.getPrincipal();
		// Buscamos al usuario en la base de datos.
		User userToUpdate = usersRepository.findUserById(user.getId());
		// Si no es encontrado damos un error
		if (userToUpdate == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		// Comprobamos que los campos no estan vacios

		if (userUpdateDTO.getUsername() != null && !userUpdateDTO.getUsername().isBlank()) {
			userToUpdate.setUsername(userUpdateDTO.getUsername());
		}

		if (userUpdateDTO.getName() != null && !userUpdateDTO.getName().isBlank()) {
			userToUpdate.setName(userUpdateDTO.getName());
		}

		if (userUpdateDTO.getLastName() != null && !userUpdateDTO.getLastName().isBlank()) {
			userToUpdate.setLast_name(userUpdateDTO.getLastName());
		}

		// E/h
		if (userUpdateDTO.getEurosPerHour() > 0) {
			userToUpdate.setEurosPerHour(userUpdateDTO.getEurosPerHour());
		}
		// EX E/h
		if (userUpdateDTO.getEurosPerExtraHours() > 0) {
			userToUpdate.setEurosPerExtraHours(userUpdateDTO.getEurosPerExtraHours());
		}
		// is flexibe
		userToUpdate.setFlexible(userUpdateDTO.isFlexible());
		// Guardamos el usuario y lo actualizamos.
		usersRepository.save(userToUpdate);
		// Respuesta hacia el front de que todo ha ido correctamente
		return ResponseEntity.ok("User updated successfully");
	}
	
	@PutMapping("/updatepassword")
	public ResponseEntity<String> updateUserPassword(UsernamePasswordAuthenticationToken upa, @RequestBody String password) {
		User user = (User) upa.getPrincipal();
		User userToUpdate = usersRepository.findUserById(user.getId());
		// Si no es encontrado damos un error
		if (userToUpdate == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		
		if(userToUpdate.getPassword().equals(encode.encode(password))) {
			return ResponseEntity.badRequest().body("It's the same password");
		}
		if(password.isBlank() || password.isEmpty()) {
			return ResponseEntity.badRequest().body("It's the same password");
		}
		userToUpdate.setPassword(encode.encode(password));
		usersRepository.save(userToUpdate);
		
		return ResponseEntity.ok("User password updated successfully");
	}
	
	@GetMapping("/checkpassword")
	public ResponseEntity<String> checkPassword(UsernamePasswordAuthenticationToken upa,@RequestBody String oldpassword) {
		User user = (User) upa.getPrincipal();
		if(user.getPassword().equals(encode.encode(oldpassword))) {
			return ResponseEntity.ok("Correct password");
		}
		return ResponseEntity.badRequest().body("Incorrect password");
	}

	// ------------------------------------------------------------------------
	// getUser
	// -------------------------------------------------------- ----------------

	@GetMapping
	public ResponseEntity<User> getUserSettings(UsernamePasswordAuthenticationToken upa) {
		User user = (User) upa.getPrincipal();
		return ResponseEntity.ok(usersRepository.findUserById(user.getId()));
	}
	

	
}
