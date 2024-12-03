package com.pla.control.controllers;

import com.pla.control.models.Intervals;
import com.pla.control.models.WorkDay;
import com.pla.control.repositories.IntervalsRepository;
import com.pla.control.repositories.WorkDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/intervals")
public class IntervalsController {

	@Autowired
	private IntervalsRepository intervalsRepository;

	@Autowired
	private WorkDayRepository workDayRepository;

	@PostMapping("/{workdayId}")
	public ResponseEntity<Intervals> createInterval(@PathVariable int workdayId, @RequestBody Intervals interval) {

		WorkDay workDay = workDayRepository.findById(workdayId)
				.orElseThrow(() -> new RuntimeException("WorkDay not found"));

		interval.setWorkDay(workDay);
		Intervals savedInterval = intervalsRepository.save(interval);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedInterval);
	}

	@GetMapping
	public List<Intervals> getAllIntervals() {
		return intervalsRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Intervals> getIntervalById(@PathVariable int id) {
		Intervals interval = intervalsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Interval not found"));
		return ResponseEntity.ok(interval);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Intervals> updateInterval(@PathVariable int id, @RequestBody Intervals intervalDetails) {

		Intervals interval = intervalsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Interval not found"));

		interval.setStart_time(intervalDetails.getStart_time());
		interval.setEnd_time(intervalDetails.getEnd_time());

		Intervals updatedInterval = intervalsRepository.save(interval);
		return ResponseEntity.ok(updatedInterval);
	}
	
	@GetMapping("start/{id}")
	public LocalTime setIntervalStart(@PathVariable int id) {
		Intervals interval = intervalsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Interval not found"));
		interval.setStart_time(LocalTime.now());
		intervalsRepository.save(interval);
		return LocalTime.now();
		
	}
	@GetMapping("end/{id}")
	public LocalTime setIntervalEnd(@PathVariable int id) {
		Intervals interval = intervalsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Interval not found"));
		interval.setEnd_time(LocalTime.now());
		intervalsRepository.save(interval);
		return LocalTime.now();
		
	}
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteInterval(@PathVariable int id) {
		Intervals interval = intervalsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Interval not found"));
		intervalsRepository.delete(interval);
		return ResponseEntity.ok("Interval deleted successfully");
	}
	
	
	
	
}
