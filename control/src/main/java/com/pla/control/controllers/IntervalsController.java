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

	// ------------------------------------------------------------------------
	// Create Interval
	// ------------------------------------------------------------------------

	/****************************************************************************
	 * Crea un intervalo nuevo.													*
	 * 	Recibe:																	*
	 * 		- Int workdayId: la id del workday correspondiente al intervalo		*
	 * 		- Intervals interval: el objeto intervalo con su informacion		*
	 * 	Devuelve:																*
	 *		- ResponseEntity.status(HttpStatus.CREATED).body(savedInterval);	*
	 ****************************************************************************/
	
	@PostMapping("/{workdayId}")
	public ResponseEntity<Intervals> createInterval(@PathVariable int workdayId, @RequestBody Intervals interval) {

		WorkDay workDay = workDayRepository.findById(workdayId)
				.orElseThrow(() -> new RuntimeException("WorkDay not found"));

		interval.setWorkDay(workDay);
		Intervals savedInterval = intervalsRepository.save(interval);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedInterval);
	}

	// ------------------------------------------------------------------------
	// Get all Intervals
	// ------------------------------------------------------------------------

	/****************************************************************************
	 *  Busca y devuelve todos los intervalos en la base de datos				*
	 * Crea un intervalo nuevo.													*
	 *	Recibe:																	*
	 *		- Nada.																*
	 *	Devuelve:																*
	 *		- findAll(): Todos los intervalos de la base de datos.				*
	 ****************************************************************************/
	
	@GetMapping
	public List<Intervals> getAllIntervals() {
		return intervalsRepository.findAll();
	}

	// ------------------------------------------------------------------------
	// Get Interval by ID
	// ------------------------------------------------------------------------

	/********************************************************************************
	 * Busca y devuelve la informacion si existe del intervalo de la id recibida.	*
	 * 	Recibe:																		*
	 * 		- int id: id del intervalo a buscar.									*
	 *	Devuelve:																	*
	 * 		- ResponseEntity.ok(interval): El objeto intervalo.						*
	 ********************************************************************************/
	
	@GetMapping("/{id}")
	public ResponseEntity<Intervals> getIntervalById(@PathVariable int id) {
		Intervals interval = intervalsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Interval not found"));
		return ResponseEntity.ok(interval);
	}

	// ------------------------------------------------------------------------
	// Update Interval
	// ------------------------------------------------------------------------
	
	/********************************************************************************
	 * Actualizamos el intervalo de empezar y acabar horas.							*
	 * 	Recibe:																		*
	 * 		- int id: id del intervalo a buscar.									*
	 * 		- Intervals interval detail: Objeto entero de intervals					*
	 * 	Devuelve:																	*
	 * 		- ResponseEntity.ok(updatedInterval): El objeto intervalo modificado.	*
	 ********************************************************************************/
	@PutMapping("/{id}")
	public ResponseEntity<Intervals> updateInterval(@PathVariable int id, @RequestBody Intervals intervalDetails) {

		Intervals interval = intervalsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Interval not found"));

		interval.setStart_time(intervalDetails.getStart_time());
		interval.setEnd_time(intervalDetails.getEnd_time());

		Intervals updatedInterval = intervalsRepository.save(interval);
		return ResponseEntity.ok(updatedInterval);
	}

	// ------------------------------------------------------------------------
	// Set Start and End Intervals
	// ------------------------------------------------------------------------
	
	/*
	 * Con un click de un boton guarda el comienzo de la hora al intervalo.
	 * 	Recibe:
	 * 		- int id: id del intervalo a buscar.
	 * 	Devuelve:
	 * 		- LocalTime.now(): Devolvemos la hora actual.
	 */
	@GetMapping("start/{id}")
	public LocalTime setIntervalStart(@PathVariable int id) {
		Intervals interval = intervalsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Interval not found"));
		interval.setStart_time(LocalTime.now());
		intervalsRepository.save(interval);
		return LocalTime.now();

	}

	/************************************************************************
	 * Con un click de un boton guarda el final de la hora al intervalo.	*
	 * 	Recibe:																*
	 * 		- int id: id del intervalo a buscar.							*
	 *	Devuelve:															*
	 * 		- LocalTime.now(): Devolvemos la hora actual.					*
	 ************************************************************************/
	@GetMapping("end/{id}")
	public LocalTime setIntervalEnd(@PathVariable int id) {
		Intervals interval = intervalsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Interval not found"));
		interval.setEnd_time(LocalTime.now());
		intervalsRepository.save(interval);
		return LocalTime.now();

	}

	// ------------------------------------------------------------------------
	// Delete Interval
	// ------------------------------------------------------------------------
	
	/****************************************************************************************************
	 * Borramos un Intervalo.																			*
	 * 	Recibe:																							*
	 * 		- int id: id del intervalo a buscar.														*
	 * 	Devuelve:																						*
	 * 		- ResponseEntity.ok("Interval deleted successfully"): Devolvemos una String con un mensaje.	*
	 ****************************************************************************************************/
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteInterval(@PathVariable int id) {
		Intervals interval = intervalsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Interval not found"));
		intervalsRepository.delete(interval);
		return ResponseEntity.ok("Interval deleted successfully");
	}

}
