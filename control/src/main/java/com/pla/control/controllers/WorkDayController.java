package com.pla.control.controllers;

import com.pla.control.models.WorkDay;
import com.pla.control.repositories.UsersRepository;
import com.pla.control.repositories.WorkDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workdays")
public class WorkDayController {

    @Autowired
    WorkDayRepository workDayRepository;

    @Autowired
    UsersRepository usersRepository;

//    @PostMapping
//    public ResponseEntity<WorkDay>createWorkDay(@RequestBody WorkDay workDay){
//
//
//    }
}
