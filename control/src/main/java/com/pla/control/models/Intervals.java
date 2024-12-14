package com.pla.control.models;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "intervals")
public class Intervals {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalTime start_time;
	private LocalTime end_time;


	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "workday_id", nullable = false)
	private WorkDay workDay;

	public Intervals() {
		super();
	}
	public Intervals(LocalTime start_time, WorkDay workDay) {
		super();
		this.start_time = start_time;
		this.workDay = workDay;
	}

	
	public Intervals(LocalTime start_time, LocalTime end_time, WorkDay workDay) {
		super();
		this.start_time = start_time;
		this.end_time = end_time;
		this.workDay = workDay;
	}

	public void setId(int id) {
		this.id = id;
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



}
