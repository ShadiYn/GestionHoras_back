package com.pla.control.models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String last_name;
	private String username;
	private String password;
	private boolean isFlexible;
	private float eurosPerHour;
	private float eurosPerExtraHours;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	@OneToMany(mappedBy = "user")
	private List<WorkDay> workDays;

	public User() {
	}

	public User(String name, String last_name, String username, String password, float eurosPerHour,
			float eurosPerExtraHours, boolean isFlexible) {
		super();
		this.name = name;
		this.last_name = last_name;
		this.username = username;
		this.password = password;
		this.created_at = LocalDateTime.now();
		this.updated_at = LocalDateTime.now();
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;
		this.eurosPerHour = eurosPerHour;
		this.eurosPerExtraHours = eurosPerExtraHours;
		this.isFlexible = isFlexible;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public int getId() {
		return id;
	}

	public boolean isFlexible() {
		return isFlexible;
	}

	public void setFlexible(boolean flexible) {
		isFlexible = flexible;
		this.updated_at = LocalDateTime.now();
	}

	public float getEurosPerHour() {
		return eurosPerHour;
	}

	public void setEurosPerHour(float eurosPerHour) {
		this.eurosPerHour = eurosPerHour;
		this.updated_at = LocalDateTime.now();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.updated_at = LocalDateTime.now();
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public void setUsername(String username) {
		this.username = username;
		this.updated_at = LocalDateTime.now();
	}

	public void setPassword(String password) {
		this.password = password;
		this.updated_at = LocalDateTime.now();
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
		this.updated_at = LocalDateTime.now();
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
		this.updated_at = LocalDateTime.now();
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
		this.updated_at = LocalDateTime.now();
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		this.updated_at = LocalDateTime.now();
	}

	public List<WorkDay> getWorkDays() {
		return workDays;
	}

	public void setWorkDays(List<WorkDay> workDays) {
		this.workDays = workDays;
		this.updated_at = LocalDateTime.now();
	}

	public float getEurosPerExtraHours() {
		return eurosPerExtraHours;
	}

	public void setEurosPerExtraHours(float eurosPerExtraHours) {
		this.eurosPerExtraHours = eurosPerExtraHours;
		this.updated_at = LocalDateTime.now();
	}
}
