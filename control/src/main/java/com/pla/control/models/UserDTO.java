package com.pla.control.models;

public class UserDTO {
	private String username;
	private String name;
	private String password;
	private String lastName;
	private float eurosPerHour;
	private float eurosPerExtraHours;
	private Boolean isFlexible;

	public UserDTO() {
	}

	public UserDTO(String username, String name, String lastName, float eurosPerHour, float eurosPerExtraHours, Boolean isFlexible) {
		this.username = username;
		this.name = name;
		this.lastName = lastName;
		this.eurosPerHour = eurosPerHour;
		this.eurosPerExtraHours = eurosPerExtraHours;
		this.isFlexible = isFlexible;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public float getEurosPerHour() {
		return eurosPerHour;
	}

	public void setEurosPerHour(float eurosPerHour) {
		this.eurosPerHour = eurosPerHour;
	}

	public Boolean isFlexible() {
		return isFlexible;
	}

	public void setIsFlexible(Boolean flexible) {
		isFlexible = flexible;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public float getEurosPerExtraHours() {
		return eurosPerExtraHours;
	}

	public void setEurosPerExtraHours(float eurosPerExtraHours) {
		this.eurosPerExtraHours = eurosPerExtraHours;
	}
	
}
