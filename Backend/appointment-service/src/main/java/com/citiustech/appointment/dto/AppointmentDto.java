package com.citiustech.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.citiustech.appointment.entity.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


@Data
public class AppointmentDto {

	@NotNull(message = "AppointmentDate cannot be null")
	@FutureOrPresent(message = "Please select correct date")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate appointmentDate;

	@NotNull(message = "Title cannot be null")
	private String title;

	private String reason;

	@NotNull(message = "Description cannot be null")
	private String description;

	@NotNull(message = "patientId cannot be null")
	private String patientId;

	@NotNull(message = "StartAppointmentTime cannot be null")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private LocalTime startAppointmentTime;

	@NotNull(message = "EndAppointmentTime cannot be null")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private LocalTime endAppointmentTime;
	
	@NotNull
	private AppointmentStatus status;

	@NotNull(message = "PhysicianId cannot be null")
	private String physicianId;

}
