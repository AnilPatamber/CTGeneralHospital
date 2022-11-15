package com.citiustech.appointment.service;

import org.springframework.http.ResponseEntity;

import com.citiustech.appointment.dto.AppointmentDto;
import com.citiustech.appointment.dto.ResponseObject;

public interface AppointmentService {
	
	public ResponseObject addAppointment(AppointmentDto appointmentDto);
}
