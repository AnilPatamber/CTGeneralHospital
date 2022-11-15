package com.citiustech.appointment.entity;

public enum AppointmentStatus {

	PENDING("PENDING"), CONFIRM("CONFIRM");

	private String name;

	public String getName() {
		return name;
	}

	AppointmentStatus(String name) {
		this.name = name;
	}

}
