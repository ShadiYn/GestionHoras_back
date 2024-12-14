package com.pla.control.controllers;

import com.pla.control.models.Intervals;
import com.pla.control.models.User;
import com.pla.control.models.WorkDay;
import com.pla.control.repositories.IntervalsRepository;
import com.pla.control.repositories.WorkDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.time.Duration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/intervals")
public class IntervalsController {

	private static final Logger logger = LoggerFactory.getLogger(IntervalsController.class);

	@Autowired
	private IntervalsRepository intervalsRepository;

	@Autowired
	private WorkDayRepository workDayRepository;

	// ------------------------------------------------------------------------
	// Create Interval
	// ------------------------------------------------------------------------

	/****************************************************************************
	 * Crea un intervalo nuevo. * Recibe: * - Int workdayId: la id del workday
	 * correspondiente al intervalo * - Intervals interval: el objeto intervalo con
	 * su informacion * Devuelve: * -
	 * ResponseEntity.status(HttpStatus.CREATED).body(savedInterval); *
	 ****************************************************************************/

	/*
	 * @PostMapping("/{workdayId}") public ResponseEntity<Intervals>
	 * createInterval(@PathVariable int workdayId, @RequestBody Intervals interval)
	 * {
	 * 
	 * WorkDay workDay = workDayRepository.findById(workdayId) .orElseThrow(() ->
	 * new RuntimeException("WorkDay not found"));
	 * 
	 * interval.setWorkDay(workDay); Intervals savedInterval =
	 * intervalsRepository.save(interval);
	 * 
	 * return ResponseEntity.status(HttpStatus.CREATED).body(savedInterval); }
	 */
	// calcular intevalos
	@GetMapping("/total-hours/{workdayId}")
	public float getTotalHoursForMonth(@PathVariable int workdayId) {
		WorkDay workDay = workDayRepository.findById(workdayId)
				.orElseThrow(() -> new RuntimeException("WorkDay not found"));

		List<Intervals> intervals = intervalsRepository.findAll().stream()
				.filter(interval -> interval.getWorkDay().getId() == workdayId)
				.filter(interval -> interval.getStart_time() != null && interval.getEnd_time() != null).toList();

		// Filtrar los intervalos que corresponden al mes actual
		LocalDate today = LocalDate.now();
		float totalHours = 0;

		for (Intervals interval : intervals) {
			LocalDate intervalDate = workDay.getDay(); // Asume que tienes un campo de fecha en WorkDay
			if (intervalDate.getYear() == today.getYear() && intervalDate.getMonth() == today.getMonth()) {
				Duration duration = Duration.between(interval.getStart_time(), interval.getEnd_time());
				totalHours += duration.toHours() + (duration.toMinutesPart() / 60.0); // Total en horas con decimales
			}
		}

		return totalHours;
	}

	// ------------------------------------------------------------------------
	// Get all Intervals
	// ------------------------------------------------------------------------

	/****************************************************************************
	 * Busca y devuelve todos los intervalos en la base de datos * Crea un intervalo
	 * nuevo. * Recibe: * - Nada. * Devuelve: * - findAll(): Todos los intervalos de
	 * la base de datos. *
	 ****************************************************************************/

	@GetMapping
	public ResponseEntity<List<Intervals>> getAllIntervals() {
		return ResponseEntity.ok(intervalsRepository.findAll());
	}

	// ------------------------------------------------------------------------
	// Get Interval by ID
	// ------------------------------------------------------------------------

	/********************************************************************************
	 * Busca y devuelve la informacion si existe del intervalo de la id recibida. *
	 * Recibe: * - int id: id del intervalo a buscar. * Devuelve: * -
	 * ResponseEntity.ok(interval): El objeto intervalo. *
	 ********************************************************************************/
	@GetMapping("/interval/{id}")
	public ResponseEntity<Intervals> getInterval(@PathVariable int id) {
		Intervals interval = intervalsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Interval not found"));

		// Si se encuentra, se devuelve el intervalo con un código 200 OK
		return ResponseEntity.ok(interval);
	}

	// ------------------------------------------------------------------------
	// Update Interval
	// ------------------------------------------------------------------------

	/********************************************************************************
	 * Actualizamos el intervalo de empezar y acabar horas. * Recibe: * - int id: id
	 * del intervalo a buscar. * - Intervals interval detail: Objeto entero de
	 * intervals * Devuelve: * - ResponseEntity.ok(updatedInterval): El objeto
	 * intervalo modificado. *
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
	// Get Latest Interval created
	// ------------------------------------------------------------------------

	@GetMapping("/currentinterval")
	public ResponseEntity<Intervals> currentInterval(UsernamePasswordAuthenticationToken upa) {
	    User user = (User) upa.getPrincipal();

	    Optional<WorkDay> workDayOptional = workDayRepository.findByUserAndDay(user, LocalDate.now());

	    if (workDayOptional.isPresent()) {
	        WorkDay workDay = workDayOptional.get();
	        
	        // Obtener el último intervalo asociado a este WorkDay
	        List<Intervals> intervals = intervalsRepository.findByWorkDay(workDay);
	        
	        if (intervals.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Si no hay intervalos
	        }

	        // Ordenar los intervalos por id 
	        Intervals lastInterval = intervals.stream()
	                                          .max(Comparator.comparingInt(Intervals::getId)) // Devuelve el intervalo con el id más alto
	                                          .orElseThrow(() -> new RuntimeException("No interval found"));

	        return ResponseEntity.ok(lastInterval);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Si no se encuentra el WorkDay
	    }
	}

	// ------------------------------------------------------------------------
	// Set Start and End Intervals
	// ------------------------------------------------------------------------

	/*
	 * Con un click de un boton guarda el comienzo de la hora al intervalo. Recibe:
	 * - int id: id del intervalo a buscar. Devuelve: - LocalTime.now(): Devolvemos
	 * la hora actual.
	 */

	// En teoria no hay necesidad de que interval start exista ya que al clicar todo
	// se maneja en workdaycontroller
	/*
	 * @PostMapping("/setIntervalStart") public String
	 * setIntervalStart(@RequestParam LocalTime startTime) { // Obtener la fecha
	 * actual LocalDate currentDate = LocalDate.now();
	 * 
	 * // Verificar si ya existe un WorkDay para el día actual Optional<WorkDay>
	 * existingWorkDay = workDayRepository.findByDate(currentDate);
	 * 
	 * WorkDay workDay; if (existingWorkDay.isPresent()) { // Si el WorkDay ya
	 * existe, obtenemos el WorkDay workDay = existingWorkDay.get();
	 * 
	 * // Verificar si ya existe un intervalo con start_time no nulo y end_time nulo
	 * List<Intervals> existingIntervals =
	 * intervalsRepository.findByWorkDayIn(List.of(workDay)); for (Intervals
	 * interval : existingIntervals) { if (interval.getStart_time() != null &&
	 * interval.getEnd_time() == null) { return
	 * "Error: Ya existe un intervalo en este WorkDay con start_time no nulo y end_time nulo."
	 * ; } } } else { // Si el WorkDay no existe, lo creamos workDay = new
	 * WorkDay(); workDay.setDate(currentDate); // Establecer la fecha actual
	 * workDayRepository.save(workDay); // Guardar el nuevo WorkDay }
	 * 
	 * 
	 * // Crear el nuevo intervalo con start_time igual al valor recibido y end_time
	 * como null Intervals newInterval = new Intervals(startTime, null, 0, 0,
	 * workDay); intervalsRepository.save(newInterval);
	 * 
	 * return "Intervalo creado correctamente."; } }
	 */

	// Con un click de un boton guarda el final de la hora al intervalo.
	// Recibe:
	// - int id: id del intervalo a buscar.
	// Devuelve:
	// - LocalTime.now(): Devolvemos la hora actual.

	@GetMapping("end/{id}")
	public ResponseEntity<LocalTime> setIntervalEnd(@PathVariable int id) {
		Optional<Intervals> intervalOpt = intervalsRepository.findById(id);
		if (intervalOpt.isPresent()) {
			Intervals interval = intervalOpt.get();
			if (interval.getStart_time() != null && interval.getEnd_time() == null) {
				interval.setEnd_time(LocalTime.now());
				intervalsRepository.save(interval);				
			} 
			return ResponseEntity.ok(interval.getEnd_time());
		} else {
			
			throw new RuntimeException("Interval not found");
		}
	}

	// ------------------------------------------------------------------------
	// Delete Interval
	// ------------------------------------------------------------------------

	/****************************************************************************************************
	 * Borramos un Intervalo. * Recibe: * - int id: id del intervalo a buscar. *
	 * Devuelve: * - ResponseEntity.ok("Interval deleted successfully"): Devolvemos
	 * una String con un mensaje. *
	 ****************************************************************************************************/
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteInterval(@PathVariable int id) {
		Intervals interval = intervalsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Interval not found"));
		intervalsRepository.delete(interval);
		return ResponseEntity.ok("Interval deleted successfully");
	}

}
