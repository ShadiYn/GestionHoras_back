package com.pla.control.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;


@Entity
@Table(name = "workday")
public class WorkDay {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	private LocalDate day;
	private float requiredHours;
	private boolean attended;
	private boolean justified;
	private String description;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	@OneToMany(mappedBy = "workDay", cascade = CascadeType.ALL, orphanRemoval = true)

	private List<Intervals> intervalsList;

	public WorkDay() {
		super();
	}

	//noflexible
	public WorkDay(User user, LocalDate day, float requiredHours) {
		super();
		this.user = user;
		this.day = day;
		this.requiredHours = requiredHours;
		this.attended = true;
		this.created_at = LocalDateTime.now();
		this.updated_at = LocalDateTime.now();
	}
	
	
	public WorkDay(User user, LocalDate day, float requiredHours, boolean attended, boolean justified,
			String description) {
		super();
		this.user = user;
		this.day = day;
		this.requiredHours = requiredHours;
		this.attended = attended;
		this.justified = justified;
		this.description = description;
		this.created_at = LocalDateTime.now();
		this.updated_at = LocalDateTime.now();
	}

	public int getId() {
		return id;
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

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public List<Intervals> getIntervalsList() {
		return intervalsList;
	}

	public void setIntervalsList(List<Intervals> intervalsList) {
		this.intervalsList = intervalsList;
		this.updated_at = LocalDateTime.now();
	}

	public float getRequiredHours() {
		return requiredHours;
	}

	public void setRequiredHours(float requiredHours) {
		this.requiredHours = requiredHours;
	}

}
