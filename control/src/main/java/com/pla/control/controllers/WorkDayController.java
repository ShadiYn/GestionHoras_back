package com.pla.control.controllers;

import com.pla.control.models.Intervals;
import com.pla.control.models.User;
import com.pla.control.models.WorkDay;
import com.pla.control.repositories.IntervalsRepository;
import com.pla.control.repositories.UsersRepository;
import com.pla.control.repositories.WorkDayRepository;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workdays")
public class WorkDayController {

	@Autowired
	WorkDayRepository workDayRepository;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	IntervalsRepository intervalsRepository;

	@PostMapping
	public ResponseEntity<WorkDay> createWorkDay(UsernamePasswordAuthenticationToken upa,
			@RequestBody WorkDay workDay) {
		User user = (User) upa.getPrincipal();
		workDay.setUser(user);
		WorkDay savedWorkDay = workDayRepository.save(workDay);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkDay);
	}

	@PostMapping("/checkandcreateautoworkday")
	public ResponseEntity<String> checkAndCreateAutoWorkday(UsernamePasswordAuthenticationToken upa) {
		// Get the authenticated user
		User user = (User) upa.getPrincipal();

		// Try to find the WorkDay for the user and the current day
		Optional<WorkDay> workDayOptional = workDayRepository.findByUserAndDay(user, LocalDate.now());

		if (workDayOptional.isPresent()) {
			// If the WorkDay is found, check for existing intervals
			WorkDay workDay = workDayOptional.get();

			// Check if the WorkDay already has an interval with a non-null start_time
			List<Intervals> existingIntervals = intervalsRepository.findByWorkDayIn(List.of(workDay));
			for (Intervals interval : existingIntervals) {
				if (interval.getStart_time() != null && interval.getEnd_time() == null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Interval with start time");
				} else {
					// If no such interval exists, create a new one (if necessary)
					Intervals newInterval = new Intervals(LocalTime.now(), workDay);
					intervalsRepository.save(newInterval);
					return ResponseEntity.status(HttpStatus.CREATED).body("New Interval Created");
				}
			}

		} else {
			if (!user.isFlexible()) {
				// If the user is not flexible, create WorkDay with required hours from the
				// profile
				WorkDay newWorkDay = new WorkDay(user, LocalDate.now(), user.getRequiredHours());
				workDayRepository.save(newWorkDay);
				
				Intervals newInterval = new Intervals(LocalTime.now(), newWorkDay);
				intervalsRepository.save(newInterval);
				
				return ResponseEntity.status(HttpStatus.CREATED).body("New Workday and Interval Created");

			} 
				// If the user is flexible, prompt for required hours and direct to flexible
				// workday creation
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is flexible.");
			

		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("kboom");
	}
	
	@PostMapping("createworkdayflexible")
	public ResponseEntity<String> createWorkDayFlexible(UsernamePasswordAuthenticationToken upa,int hours) {
		User user = (User) upa.getPrincipal();
		WorkDay newWorkDay = new WorkDay(user, LocalDate.now(), hours);
		return ResponseEntity.status(HttpStatus.CREATED).body("created");
	}

	@GetMapping("/current")
	public ResponseEntity<WorkDay> getTodayWorkDay(UsernamePasswordAuthenticationToken upa) {
	    User user = (User) upa.getPrincipal();

	    Optional<WorkDay> workDayOptional = workDayRepository.findByUserAndDay(user, LocalDate.now());

	    if (workDayOptional.isPresent()) {
	        // If WorkDay is found, return it
	        return ResponseEntity.ok(workDayOptional.get());
	    } else {
	        // If no WorkDay is found, return a NOT FOUND status
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body(null);
	    }
	}
	
	/*
		 * @GetMapping("/current") public ResponseEntity<WorkDay>
		 * getOrCreateCurrentWorkDay(UsernamePasswordAuthenticationToken upa) { User
		 * user = (User) upa.getPrincipal(); LocalDate today = LocalDate.now();
		 * 
		 * WorkDay workDay = workDayRepository.findByUserAndDay(user,
		 * today).orElseGet(() -> { WorkDay newWorkDay = new WorkDay(today,0,0, false,
		 * false, "", LocalDateTime.now(), LocalDateTime.now());
		 * newWorkDay.setUser(user); return workDayRepository.save(newWorkDay); });
		 * 
		 * return ResponseEntity.ok(workDay); }
		 */

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
