package com.pla.control.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "workschedule")
public class WorkSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDateTime startdatetime;
	private LocalDateTime enddatetime;
	private LocalDateTime breakstart;
	private LocalDateTime breakend;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	private int extrahours;
}
