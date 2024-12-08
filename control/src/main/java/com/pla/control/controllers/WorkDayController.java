package com.pla.control.controllers;

import com.pla.control.models.User;
import com.pla.control.models.WorkDay;
import com.pla.control.repositories.UsersRepository;
import com.pla.control.repositories.WorkDayRepository;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/workdays")
public class WorkDayController {

    @Autowired
    WorkDayRepository workDayRepository;

    @Autowired
    UsersRepository usersRepository;

    @PostMapping
    public ResponseEntity<WorkDay> createWorkDay(UsernamePasswordAuthenticationToken upa, @RequestBody WorkDay workDay) {
    	User user =  (User) upa.getPrincipal();
    	workDay.setUser(user);
        WorkDay savedWorkDay = workDayRepository.save(workDay);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkDay);
    }

    @GetMapping("/current")
    public ResponseEntity<WorkDay> getOrCreateCurrentWorkDay(UsernamePasswordAuthenticationToken upa) {
        User user = (User) upa.getPrincipal();
        LocalDate today = LocalDate.now();

        WorkDay workDay = workDayRepository.findByUserAndDay(user, today)
                .orElseGet(() -> {
                    WorkDay newWorkDay = new WorkDay(today, false, false, "", LocalDateTime.now(), LocalDateTime.now());
                    newWorkDay.setUser(user);
                    return workDayRepository.save(newWorkDay);
                });

        return ResponseEntity.ok(workDay);
    }

    //obtener el mes acytual

    @GetMapping("/currentmonth/{id}")
    public ResponseEntity<?> getForCurrentMonth(@PathVariable int id) {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1); // Primer día del mes
        LocalDate endDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()); // Último día del mes

        // Buscar usuario
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Filtrar workdays del mes actual
        List<WorkDay> workDays = workDayRepository.findByDayBetween(startDate, endDate);

        // Filtrar solo los workdays del usuario
        List<WorkDay> userWorkDays = workDays.stream()
                .filter(workDay -> workDay.getUser().getId() == id)
                .toList();

        // Calcular el total de horas trabajadas
        double totalHours = userWorkDays.stream()
                .flatMap(workDay -> workDay.getIntervalsList().stream())
                .mapToDouble(interval -> calculateHours(interval.getStart_time(), interval.getEnd_time()))
                .sum();

        // Construir respuesta
        return ResponseEntity.ok(new TotalWorkHoursResponse(totalHours, userWorkDays));
    }

    // Calcular duración en horas entre dos LocalTime
    private double calculateHours(LocalTime startTime, LocalTime endTime) {
        if (startTime == null || endTime == null) {
            return 0.0;
        }
        return Duration.between(startTime, endTime).toMinutes() / 60.0;
    }

    // DTO para la respuesta
    public static class TotalWorkHoursResponse {
        private double totalHours;
        private List<WorkDay> workDays;

        public TotalWorkHoursResponse(double totalHours, List<WorkDay> workDays) {
            this.totalHours = totalHours;
            this.workDays = workDays;
        }

        public double getTotalHours() {
            return totalHours;
        }

        public List<WorkDay> getWorkDays() {
            return workDays;
        }
    }

    // WorkDayController.java
    @GetMapping("/workdays/month")
    public ResponseEntity<List<WorkDay>> getWorkdaysForMonth(
            @RequestParam int userId,
            @RequestParam int year,
            @RequestParam int month)  {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<WorkDay> workDays = workDayRepository.findByUserAndDayBetween(user, startDate, endDate);

        if (workDays.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(workDays);
        }

        return ResponseEntity.ok(workDays);
    }

    @GetMapping("/hours/current-month/{userId}")
    public ResponseEntity<?> getTotalHoursForCurrentMonth(@PathVariable int userId) {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtenemos todos los WorkDays del usuario en el mes actual
        List<WorkDay> workDays = workDayRepository.findByUserAndDayBetween(user, startDate, endDate);

        // Calculamos el total de horas trabajadas
        double totalHours = workDays.stream()
                .flatMap(workDay -> workDay.getIntervalsList().stream())
                .mapToDouble(interval -> calculateHours(interval.getStart_time(), interval.getEnd_time()))
                .sum();

        // Respuesta
        return ResponseEntity.ok(new TotalHoursResponse(totalHours));
    }

    // DTO para encapsular la respuesta
    public static class TotalHoursResponse {
        private double totalHours;

        public TotalHoursResponse(double totalHours) {
            this.totalHours = totalHours;
        }

        public double getTotalHours() {
            return totalHours;
        }
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
