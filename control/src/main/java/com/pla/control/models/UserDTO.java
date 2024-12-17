package com.pla.control.models;

public class UserDTO {
	private String username;
	private String name;
	private String password;
	private String lastName;
	private float requiredHours;
	private float eurosPerHour;
	private float eurosPerExtraHours;
	private boolean isFlexible;

	public UserDTO() {
	}

	public float getRequiredHours() {
		return requiredHours;
	}

	public void setRequiredHours(float requiredHours) {
		this.requiredHours = requiredHours;
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

	public boolean isFlexible() {
		return isFlexible;
	}

	public void setIsFlexible(boolean flexible) {
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
