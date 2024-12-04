package com.pla.control.controllers;

import com.pla.control.models.User;
import com.pla.control.models.UserDTO;
import com.pla.control.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//Controlador para el registro y el inicio de sesion del usuario.
public class BasicAuthController {
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder encode;
	
	//------------------------------------------------------------------------
	// 								Login / basicauth
	//------------------------------------------------------------------------

	@PostMapping(path = "/login")
	public ResponseEntity<String> basicauth(UsernamePasswordAuthenticationToken upa) {
		// El objeto principal tiene información sobre el usuario y la contraseña,
		// aunque de todas formas no la vamos a utilizar
		// No devolveremos el usuario ni la contraseña al front, sino información sobre
		// si el login ha sido exitoso. Si lo ha sido, a partir de ese momento, desde el
		// front, mandaremos en la cabecera de cada petición el username y password que
		// han provocado que el login sea exitoso

		User user = (User) upa.getPrincipal();
		return ResponseEntity.ok().body("{\"resp\":\"Login exitoso\", \"id\":" + user.getId() + "}");

	}
	
	//------------------------------------------------------------------------
	// 								Register
	//------------------------------------------------------------------------

	@PostMapping(path = "/register")
	public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {

		//Comprobar si hay algo que es null en la peticion
		
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
		if (userDTO.getEurosPerExtraHours() == 0) {
			return ResponseEntity.badRequest().body("Your extra salary per hour is required");
		}
		if (userDTO.isFlexible() == null) {
			return ResponseEntity.badRequest().body("Your schedule type is required");
		}

		// Verificar si el usuario ya existe
		if (usersRepository.existsByUsername(userDTO.getUsername())) {
			return ResponseEntity.badRequest().body("This username already exists");
		}

		// Si todo lo anterior es correcto creamos y guardamos el nuevo usuario
		User newUser = new User(userDTO.getName(), userDTO.getLastName(), userDTO.getUsername(),
				encode.encode(userDTO.getPassword()), userDTO.getEurosPerHour(),userDTO.getEurosPerExtraHours(), userDTO.isFlexible());

		usersRepository.save(newUser);
		//Respuesta de que todo ha ido correctamente.
		return ResponseEntity.ok("Registration successful");
	}

}