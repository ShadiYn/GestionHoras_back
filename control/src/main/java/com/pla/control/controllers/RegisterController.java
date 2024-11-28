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
        List<User> people = usersRepository.findAll();
        for (User usr : people) {
            if (usr.getUsername().equals(register.getUsername())) {
                return "This user already exists";
            }else{
                User newUser = new User();
                if (register.getUsername() != null && register.getUsername().isEmpty()){
                    newUser.setUsername(register.getUsername());
                }else{
                    System.out.println("The username is requiered");
                }
                if (register.getName() != null && register.getName().isEmpty()){
                    newUser.setName(register.getName());
                }else{
                    System.out.println("The name is requiered");
                }


                newUser.setLast_name(register.getLast_name());
                newUser.setEnabled(true);
                newUser.setCreated_at(LocalDateTime.now());
                newUser.setAccountNonExpired(true);
                newUser.setAccountNonLocked(true);
                newUser.setUpdated_at(LocalDateTime.now());
                newUser.setCredentialsNonExpired(true);
                newUser.setPassword(encode.encode(register.getPassword()));

                usersRepository.save(newUser);


            }

        }
        return "Succesfull register";
    }

}

