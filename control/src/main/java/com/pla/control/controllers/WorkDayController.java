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
	public ResponseEntity<WorkDay> createWorkDay(UsernamePasswordAuthenticationToken upa,
			@RequestBody WorkDay workDay) {
		User user = (User) upa.getPrincipal();
		workDay.setUser(user);
		WorkDay savedWorkDay = workDayRepository.save(workDay);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkDay);
	}

	@GetMapping("/current")
	public ResponseEntity<WorkDay> getOrCreateCurrentWorkDay(UsernamePasswordAuthenticationToken upa) {
		User user = (User) upa.getPrincipal();
		LocalDate today = LocalDate.now();

		WorkDay workDay = workDayRepository.findByUserAndDay(user, today).orElseGet(() -> {
			WorkDay newWorkDay = new WorkDay(today, false, false, "", LocalDateTime.now(), LocalDateTime.now());
			newWorkDay.setUser(user);
			return workDayRepository.save(newWorkDay);
		});

		return ResponseEntity.ok(workDay);
	}

	@GetMapping("/current-month")
	public ResponseEntity<List<WorkDay>> getWorkDaysForCurrentMonth(UsernamePasswordAuthenticationToken upa) {
		User user = (User) upa.getPrincipal();

		// Obtener el primer día del mes actual y el último día del mes actual
		LocalDate now = LocalDate.now();
		LocalDate startOfMonth = now.withDayOfMonth(1); // Primer día del mes
		LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth()); // Último día del mes

		// Obtener los días de trabajo del usuario para el mes actual
		List<WorkDay> workDays = workDayRepository.findByUserAndMonth(user, startOfMonth, endOfMonth);

		return ResponseEntity.ok(workDays);
	}

	@GetMapping("/for-month")
	public ResponseEntity<List<WorkDay>> getWorkDaysForSpecificMonth(@RequestParam int month, @RequestParam int year,
			UsernamePasswordAuthenticationToken upa) {
		User user = (User) upa.getPrincipal();

		// Validar si el mes y año son válidos
		if (month < 1 || month > 12 || year < 1900 || year > LocalDate.now().getYear()) {
			return ResponseEntity.badRequest().body(null);
		}

		// Calcular el primer y último día del mes y año especificados
		LocalDate startOfMonth = LocalDate.of(year, month, 1); // Primer día del mes
		LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth()); // Último día del mes

		// Obtener los días de trabajo del usuario para ese mes y año
		List<WorkDay> workDays = workDayRepository.findByUserAndMonthAndYear(user, startOfMonth, endOfMonth);

		return ResponseEntity.ok(workDays);
	}

	@GetMapping
	public List<WorkDay> getAllWorkDays() {
		return workDayRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<WorkDay> getWorkDayById(@PathVariable int id) {
		WorkDay workDay = workDayRepository.findById(id).orElseThrow(() -> new RuntimeException("WorkDay not found"));
		return ResponseEntity.ok(workDay);
	}

	@PutMapping("/{id}")
	public ResponseEntity<WorkDay> updateWorkDay(@PathVariable int id, @RequestBody WorkDay workDayDetails) {
		WorkDay workDay = workDayRepository.findById(id).orElseThrow(() -> new RuntimeException("WorkDay not found"));

		workDay.setDay(workDayDetails.getDay());
		workDay.setAttended(workDayDetails.isAttended());
		workDay.setJustified(workDayDetails.isJustified());
		workDay.setDescription(workDayDetails.getDescription());
		WorkDay updatedWorkDay = workDayRepository.save(workDay);
		return ResponseEntity.ok(updatedWorkDay);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteWorkDay(@PathVariable int id) {
		WorkDay workDay = workDayRepository.findById(id).orElseThrow(() -> new RuntimeException("WorkDay not found"));
		workDayRepository.delete(workDay);
		return ResponseEntity.ok("WorkDay deleted successfully");
	}

}
