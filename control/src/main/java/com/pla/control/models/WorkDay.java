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
    @JoinColumn(name = "user_id", nullable = false)
	private int user_id;
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
	
	
}
