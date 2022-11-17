package com.citiustech.appointment.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.citiustech.appointment.dto.AppointmentDto;
import com.citiustech.appointment.dto.ResponseObject;
import com.citiustech.appointment.entity.Appointment;
import com.citiustech.appointment.exception.ResourceNotFoundException;
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

			LocalTime startTime = appointmentDto.getStartAppointmentTime();
			LocalTime endTime = appointmentDto.getEndAppointmentTime();

			if (endTime.isBefore(startTime)) {
				throw new Exception("Start Time should be less than end time");
			}

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
			response.setMessage("Appointment not added, Check start and end time/date format");
		}

		return response;
	}

	@Override
	public List<Appointment> getAllAppointments() {

		return appointmentRepository.findAll();
	}

	@Override
	public ResponseObject updateAppointment(Long appointmentId, AppointmentDto appointment) {

		Appointment existingAppointment = getByAppointmentId(appointmentId);
		ResponseObject response = new ResponseObject();

		existingAppointment
				.setTitle(appointment.getTitle() != null ? appointment.getTitle() : existingAppointment.getTitle());
		existingAppointment
				.setAppointmentDate(appointment.getAppointmentDate() != null ? appointment.getAppointmentDate()
						: existingAppointment.getAppointmentDate());
		existingAppointment.setPhysicianId(appointment.getPhysicianId() != null ? appointment.getPhysicianId()
				: existingAppointment.getPhysicianId());
		existingAppointment.setStartAppointmentTime(
				appointment.getStartAppointmentTime() != null ? appointment.getStartAppointmentTime()
						: existingAppointment.getStartAppointmentTime());
		existingAppointment
				.setEndAppointmentTime(appointment.getEndAppointmentTime() != null ? appointment.getEndAppointmentTime()
						: existingAppointment.getEndAppointmentTime());
		existingAppointment.setDescription(appointment.getDescription() != null ? appointment.getDescription()
				: existingAppointment.getDescription());
		existingAppointment
				.setReason(appointment.getReason() != null ? appointment.getReason() : existingAppointment.getReason());
		existingAppointment
				.setStatus(appointment.getStatus() != null ? appointment.getStatus() : existingAppointment.getStatus());

		try {
			appointmentRepository.save(existingAppointment);
			logger.info("Appointment updated sucessfully");
			response.setHttpStatusCode(HttpStatus.CREATED.value());
			response.setLocalDateTime(LocalDateTime.now());
			response.setMessage("Appointment updated sucessfully");

		} catch (Exception e) {

			logger.error(" Appointment updated failed");
			response.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
			response.setLocalDateTime(LocalDateTime.now());
			response.setMessage("Appointment updated failed");
		}

		return response;
	}

	@Override
	public ResponseObject deleteAppointment(Appointment appointment) {

		ResponseObject response = new ResponseObject();
		try {
			appointmentRepository.delete(appointment);
			logger.info("Appointment deleted sucessfully");
			response.setHttpStatusCode(HttpStatus.CREATED.value());
			response.setLocalDateTime(LocalDateTime.now());
			response.setMessage("Appointment deleted sucessfully");

		} catch (Exception e) {

			logger.error(" Appointment deleted failed");
			response.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
			response.setLocalDateTime(LocalDateTime.now());
			response.setMessage("Appointment deleted failed");
		}

		return response;
	}

	@Override
	public ResponseObject deleteAppointmentById(Long appointmentId) {

		Appointment appointment = getByAppointmentId(appointmentId);
		ResponseObject response = new ResponseObject();

		try {
			appointmentRepository.deleteById(appointment.getAppointmentId());
			logger.info("Appointment deleted sucessfully");
			response.setHttpStatusCode(HttpStatus.CREATED.value());
			response.setLocalDateTime(LocalDateTime.now());
			response.setMessage("Appointment deleted sucessfully");

		} catch (Exception e) {

			logger.error(" Appointment deleted failed");
			response.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
			response.setLocalDateTime(LocalDateTime.now());
			response.setMessage("Appointment deleted failed");
		}

		return response;
	}

	@Override
	public Appointment getByAppointmentId(Long appointmentId) {

		return appointmentRepository.findById(appointmentId).orElseThrow(() -> new ResourceNotFoundException(
				"Appointment  not found with the appointmentId : " + appointmentId));
	}

	@Override
	public List<Appointment> readByPhysicianId(String physicianId) {

		return appointmentRepository.findByPhysicianId(physicianId);

	}

	@Override
	public List<Appointment> readByPatientId(String patientId) {
		return appointmentRepository.findByPatientId(patientId);
	}

	@Override
	public List<Appointment> readByDate(LocalDate startDate, LocalDate endDate) {

		return appointmentRepository.findByAppointmentDateBetween(startDate, endDate);

	}

	@Override
	public Long getAppointmentscount() {
		return appointmentRepository.count();
	}

	@Override
	public Long getAppointmentscountByPhysicianId(String physicianId) {
		return (long) readByPhysicianId(physicianId).size();
	}

	@Override
	public Long getAppointmentscountByPatientId(String patientId) {

		return (long) readByPatientId(patientId).size();
	}

}
