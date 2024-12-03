package com.pla.control.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "intervals")
public class Intervals {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalTime start_time;
	private LocalTime end_time;
	private float requiredHours;
	private float overtime;

	@ManyToOne
	@JoinColumn(name = "workday_id", nullable = false)
	private WorkDay workDay;

	public Intervals() {
		super();
	}

	public Intervals(LocalTime start_time, LocalTime end_time, float requiredHours, float overtime, WorkDay workDay) {
		super();
		this.start_time = start_time;
		this.end_time = end_time;
		this.requiredHours = requiredHours;
		this.overtime = overtime;
		this.workDay = workDay;
	}

	public int getId() {
		return id;
	}

	public WorkDay getWorkDay() {
		return workDay;
	}

	public void setWorkDay(WorkDay workDay) {
		this.workDay = workDay;
	}

	public LocalTime getStart_time() {
		return start_time;
	}

	public void setStart_time(LocalTime start_time) {
		this.start_time = start_time;
	}

	public LocalTime getEnd_time() {
		return end_time;
	}

	public void setEnd_time(LocalTime end_time) {
		this.end_time = end_time;
	}

	public float getRequiredHours() {
		return requiredHours;
	}

	public void setRequiredHours(float requiredHours) {
		this.requiredHours = requiredHours;
	}

	public float getOvertime() {
		return overtime;
	}

	public void setOvertime(float overtime) {
		this.overtime = overtime;
	}

}
