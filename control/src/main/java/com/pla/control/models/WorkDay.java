package com.pla.control.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "workday")
public class WorkDay {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
	private User user;
	private LocalDate day;
	private LocalTime start_time;
	private LocalTime end_time;
	private LocalTime break_start;
	private LocalTime break_end;

	
	private boolean attended;
	private boolean justified;
	private String description;
	
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	
	
	
	public WorkDay() {
		super();
	}
	public WorkDay(User user, LocalDate day, LocalTime start_time, LocalTime end_time, LocalTime break_start,
			LocalTime break_end, boolean attended, boolean justified, String description) {
		super();
		this.user = user;
		this.day = day;
		this.start_time = start_time;
		this.end_time = end_time;
		this.break_start = break_start;
		this.break_end = break_end;
		this.attended = attended;
		this.justified = justified;
		this.description = description;
		this.created_at = LocalDateTime.now();
		this.updated_at = LocalDateTime.now();
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
		this.updated_at = LocalDateTime.now();
	}
	public LocalDate getDay() {
		return day;
	}
	public void setDay(LocalDate day) {
		this.day = day;
		this.updated_at = LocalDateTime.now();
	}
	public LocalTime getStart_time() {
		return start_time;
	}
	public void setStart_time(LocalTime start_time) {
		this.start_time = start_time;
		this.updated_at = LocalDateTime.now();
	}
	public LocalTime getEnd_time() {
		return end_time;
	}
	public void setEnd_time(LocalTime end_time) {
		this.end_time = end_time;
		this.updated_at = LocalDateTime.now();
	}
	public LocalTime getBreak_start() {
		return break_start;
	}
	public void setBreak_start(LocalTime break_start) {
		this.break_start = break_start;
		this.updated_at = LocalDateTime.now();
	}
	public LocalTime getBreak_end() {
		return break_end;
	}
	public void setBreak_end(LocalTime break_end) {
		this.break_end = break_end;
		this.updated_at = LocalDateTime.now();
	}
	public boolean isAttended() {
		return attended;
	}
	public void setAttended(boolean attended) {
		this.attended = attended;
		this.updated_at = LocalDateTime.now();
	}
	public boolean isJustified() {
		return justified;
	}
	public void setJustified(boolean justified) {
		this.justified = justified;
		this.updated_at = LocalDateTime.now();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
		this.updated_at = LocalDateTime.now();
	}
	public int getId() {
		return id;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
	
	
	
	
	
}
