package com.pla.control.controllers;

import com.pla.control.models.Register;
import com.pla.control.models.User;
import com.pla.control.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
            }
        }
            User newUser = new User();

            if (register.getUsername() == null || register.getUsername().isEmpty()) {
                return "The username is required";
            }
            newUser.setUsername(register.getUsername());

            if (register.getName() == null || register.getName().isEmpty()) {
                return "The name is required";
            }
            newUser.setName(register.getName());

            if (register.getLast_name() == null || register.getLast_name().isEmpty()) {
                return "The lastname is required";
            }
            newUser.setLast_name(register.getLast_name());

            if (register.getPassword() == null || register.getPassword().isEmpty()) {
                return "The password is required";
            }
            newUser.setPassword(encode.encode(register.getPassword()));

            newUser.setEnabled(true);
            newUser.setCreated_at(LocalDateTime.now());
            newUser.setAccountNonExpired(true);
            newUser.setAccountNonLocked(true);
            newUser.setUpdated_at(LocalDateTime.now());
            newUser.setCredentialsNonExpired(true);

            usersRepository.save(newUser);

            List<User> userList = new ArrayList<>();
            userList.add(newUser);
            for(User user : userList){
                System.out.println(user.getName());
                System.out.println(user.getPassword());
                System.out.println(user.getUsername());
                System.out.println(user.getLast_name());

            }

        return "Register successful";
    }
}
