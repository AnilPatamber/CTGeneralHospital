package com.citiustech.appointment.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Appointment {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long appointmentId;

	    private LocalDate appointmentDate;

	    private String title;

	    private String description;

	    @Column(name="reason_of_visit")
	    private String reason;

	    @Column(name="patient_id")
	    private String patientId;

	    private LocalTime startAppointmentTime;

	    private	LocalTime endAppointmentTime;
	    
	    private AppointmentStatus status;

	    private String physicianId;

}
