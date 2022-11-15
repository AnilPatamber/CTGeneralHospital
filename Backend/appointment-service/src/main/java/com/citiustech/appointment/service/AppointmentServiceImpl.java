package com.citiustech.appointment.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.citiustech.appointment.dto.AppointmentDto;
import com.citiustech.appointment.dto.ResponseObject;
import com.citiustech.appointment.entity.Appointment;
import com.citiustech.appointment.repository.AppointmentRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Override
	public ResponseObject addAppointment(AppointmentDto appointmentDto) {

		ResponseObject response = new ResponseObject();
		Appointment appointment = new Appointment();
		
		try {
				BeanUtils.copyProperties(appointmentDto, appointment);
				appointmentRepository.save(appointment);
				logger.info(" Appointment added sucessfully");
				response.setHttpStatusCode(HttpStatus.CREATED.value());
				response.setLocalDateTime(LocalDateTime.now());
				response.setMessage("Appointment added");

		} catch (Exception e) {

			logger.error(" Appointment added failed");
			response.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
			response.setLocalDateTime(LocalDateTime.now());
			response.setMessage("Appointment not added");
		}

		return response;
	}

}
