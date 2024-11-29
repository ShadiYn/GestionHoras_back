package com.pla.control.controllers;

import com.pla.control.models.Register;
import com.pla.control.models.User;
import com.pla.control.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class RegisterController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    BCryptPasswordEncoder encode;

    @PostMapping(path = "/register")
    public String register(@RequestBody Register register) {
        // Log para verificar los datos recibidos
        System.out.println("Datos recibidos para el registro: " + register);

        if (register == null) {
            System.out.println("Los datos de registro no pueden ser nulos");
            return "Datos de registro inválidos";
        }

        // Verifica si el usuario ya existe
        List<User> people = usersRepository.findAll();
        for (User usr : people) {
            if (usr.getUsername() != null && usr.getUsername().equals(register.getUsername())) {
                System.out.println("El usuario ya existe: " + register.getUsername());
                return "Este usuario ya existe";
            }
        }

        // Verificación de campos obligatorios
        if (register.getUsername() == null || register.getUsername().isEmpty()) {
            System.out.println("El nombre de usuario es obligatorio");
            return "El nombre de usuario es obligatorio";
        }
        if (register.getName() == null || register.getName().isEmpty()) {
            System.out.println("El nombre es obligatorio");
            return "El nombre es obligatorio";
        }

        // Crear y guardar el nuevo usuario
        User newUser = new User();
        newUser.setUsername(register.getUsername());
        newUser.setName(register.getName());
        newUser.setLast_name(register.getLast_name());
        newUser.setEnabled(true);
        newUser.setCreated_at(LocalDateTime.now());
        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setUpdated_at(LocalDateTime.now());
        newUser.setCredentialsNonExpired(true);
        newUser.setPassword(encode.encode(register.getPassword()));

        // Guardar el usuario en la base de datos
        usersRepository.save(newUser);
        System.out.println("Usuario guardado en la base de datos: " + newUser);

        return "Registro exitoso";
    }

}
