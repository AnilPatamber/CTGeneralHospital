package com.citiustech.appointment.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citiustech.appointment.dto.AppointmentDto;
import com.citiustech.appointment.dto.ResponseObject;
import com.citiustech.appointment.service.AppointmentService;

@RestController
@RequestMapping("/patient")
public class PatientScheduleController {
	
	@Autowired
	private AppointmentService appointmentService;
	
	
	@GetMapping("/")
	public String welcome() {
		return "Welcome to Patient Schedule controller";
	}
	
	@PostMapping("/appointment/add")
	public ResponseEntity<ResponseObject> addAppointment(@Valid @RequestBody AppointmentDto appointmentDto){
		
		return new ResponseEntity<ResponseObject>(appointmentService.addAppointment(appointmentDto),HttpStatus.CREATED);
		
	}

}
