package com.pla.control.controllers;

import com.pla.control.models.WorkDay;
import com.pla.control.repositories.UsersRepository;
import com.pla.control.repositories.WorkDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/workdays")
public class WorkDayController {

    @Autowired
    WorkDayRepository workDayRepository;

    @Autowired
    UsersRepository usersRepository;

    @PostMapping
    public ResponseEntity<WorkDay> createWorkDay(@RequestBody WorkDay workDay) {
        workDay.setCreated_at(LocalDateTime.now());
        workDay.setUpdated_at(LocalDateTime.now());
        WorkDay savedWorkDay = workDayRepository.save(workDay);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkDay);
    }

    @GetMapping
    public List<WorkDay> getAllWorkDays() {
        return workDayRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkDay> getWorkDayById(@PathVariable int id) {
        WorkDay workDay = workDayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkDay not found"));
        return ResponseEntity.ok(workDay);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkDay> updateWorkDay(@PathVariable int id, @RequestBody WorkDay workDayDetails) {
        WorkDay workDay = workDayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkDay not found"));

        workDay.setDay(workDayDetails.getDay());
        workDay.setAttended(workDayDetails.isAttended());
        workDay.setJustified(workDayDetails.isJustified());
        workDay.setDescription(workDayDetails.getDescription());
        workDay.setUpdated_at(LocalDateTime.now());

        WorkDay updatedWorkDay = workDayRepository.save(workDay);
        return ResponseEntity.ok(updatedWorkDay);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkDay(@PathVariable int id) {
        WorkDay workDay = workDayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkDay not found"));
        workDayRepository.delete(workDay);
        return ResponseEntity.ok("WorkDay deleted successfully");
    }
}
