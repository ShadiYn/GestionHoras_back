package com.pla.control.controllers;

import com.pla.control.models.Intervals;
import com.pla.control.models.User;
import com.pla.control.models.WorkDay;
import com.pla.control.repositories.IntervalsRepository;
import com.pla.control.repositories.UsersRepository;
import com.pla.control.repositories.WorkDayRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
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
	        WorkDay workDay = workDayOptional.get();
	        List<Intervals> existingIntervals = intervalsRepository.findByWorkDayIn(List.of(workDay));

	        if (!existingIntervals.isEmpty()) {
	            boolean hasIncompleteInterval = existingIntervals.stream()
	                .anyMatch(interval -> interval.getStart_time() != null && interval.getEnd_time() == null);

	            if (hasIncompleteInterval) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Check-in");
	            } else {
	                intervalsRepository.save(new Intervals(LocalTime.now(), workDay));
	                return ResponseEntity.status(HttpStatus.CREATED).body("New Interval Created");
	            }
	        } else {
	            intervalsRepository.save(new Intervals(LocalTime.now(), workDay));
	            return ResponseEntity.status(HttpStatus.CREATED).body("New Interval Created");
	        }
	    } else {
	        if (!user.isFlexible()) {
	            // Create WorkDay and Interval for non-flexible users
	            WorkDay newWorkDay = new WorkDay(user, LocalDate.now(), user.getRequiredHours());
	            workDayRepository.save(newWorkDay);
	            intervalsRepository.save(new Intervals(LocalTime.now(), newWorkDay));
	            return ResponseEntity.status(HttpStatus.CREATED).body("New Workday and Interval Created");
	        }

	        // If the user is flexible, prompt for required hours
	        return ResponseEntity.status(HttpStatus.OK).body("User is flexible.");
	    }
	}


	@PostMapping("/createworkdayflexible")
	public ResponseEntity<String> createWorkDayFlexible(UsernamePasswordAuthenticationToken upa,
			@RequestBody String hours) {
		User user = (User) upa.getPrincipal();
		WorkDay newWorkDay = new WorkDay(user, LocalDate.now(), Integer.parseInt(hours));
		workDayRepository.save(newWorkDay);
		Intervals newInterval = new Intervals(LocalTime.now(), newWorkDay);
		intervalsRepository.save(newInterval);
		return ResponseEntity.status(HttpStatus.CREATED).body("created:");
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
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
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

	// Total
	@GetMapping("/unattendednumber")
	public ResponseEntity<Integer> getUnattendedNumberWorkDay(UsernamePasswordAuthenticationToken upa) {
		User user = (User) upa.getPrincipal();
		// Obtener el primer día del mes actual y el último día del mes actual
		LocalDate now = LocalDate.now();
		LocalDate startOfMonth = now.withDayOfMonth(1); // Primer día del mes
		LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth()); // Último día del mes

		// Obtener los días de trabajo del usuario para el mes actual
		List<WorkDay> aWorkdays = workDayRepository.findByUserAndMonth(user, startOfMonth, endOfMonth);
		int counter = 0;
		for (int i = 0; i < aWorkdays.size() - 1; i++) {
			if (aWorkdays.get(i).isAttended() == false) {
				counter++;
			}
		}
		return ResponseEntity.ok(counter);

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
