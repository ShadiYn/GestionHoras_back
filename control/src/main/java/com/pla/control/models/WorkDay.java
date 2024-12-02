package com.pla.control.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;
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
	private User user;
	private LocalDate day;
	private boolean attended;
	private boolean justified;
	private String description;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	@OneToMany(mappedBy = "workDay", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Intervals> intervalsList;
}
