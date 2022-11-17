package com.citiustech.appointment.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.citiustech.appointment.dto.AppointmentDto;
import com.citiustech.appointment.dto.ResponseObject;
import com.citiustech.appointment.entity.Appointment;
import com.citiustech.appointment.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("/")
	public String welcome() {
		return "Welcome to Patient Schedule controller";
	}

	@PostMapping("/add")
	public ResponseEntity<ResponseObject> addAppointment(@Valid @RequestBody AppointmentDto appointmentDto) {

		ResponseObject response = appointmentService.addAppointment(appointmentDto);

		if (response.getHttpStatusCode() >= 400) {
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);

	}

	@GetMapping("/all")
	public ResponseEntity<List<Appointment>> getAllAppointments() {

		return new ResponseEntity<List<Appointment>>(appointmentService.getAllAppointments(), HttpStatus.OK);

	}

	@PutMapping("/update/{appointmentId}")
	public ResponseEntity<ResponseObject> updateAppointment(@PathVariable Long appointmentId,
			@RequestBody AppointmentDto appointment) {

		return new ResponseEntity<ResponseObject>(appointmentService.updateAppointment(appointmentId, appointment),
				HttpStatus.OK);

	}

	@DeleteMapping("/delete/{appointmentId}")
	public ResponseEntity<ResponseObject> deleteAppointmentById(@PathVariable Long appointmentId) {

		return new ResponseEntity<ResponseObject>(appointmentService.deleteAppointmentById(appointmentId),
				HttpStatus.OK);

	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseObject> deleteAppointment(@RequestBody Appointment appointment) {

		return new ResponseEntity<ResponseObject>(appointmentService.deleteAppointment(appointment), HttpStatus.OK);

	}

	@GetMapping("/physician")
	public List<Appointment> getAllAppointmentsByPhisicianId(@RequestParam String physicianId) {
		return appointmentService.readByPhysicianId(physicianId);
	}

	@GetMapping("/patient")
	public List<Appointment> getAllAppointmentsByPatientId(@RequestParam String patientId) {
		return appointmentService.readByPatientId(patientId);
	}

	@GetMapping("/date")
	public List<Appointment> getAllAppointmentsBetweenDates(
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {

		return appointmentService.readByDate(startDate, endDate);

	}

	@GetMapping("/count")
	public ResponseEntity<Long> getAppointmentsCount() {

		return new ResponseEntity<Long>(appointmentService.getAppointmentscount(), HttpStatus.OK);

	}

	@GetMapping("/count/{physicainId}")
	public ResponseEntity<Long> getAppointmentsCountByPhysician(@PathVariable String physicainId) {

		return new ResponseEntity<Long>(appointmentService.getAppointmentscountByPhysicianId(physicainId),
				HttpStatus.OK);

	}

	@GetMapping("/count/{patientId}")
	public ResponseEntity<Long> getAppointmentsCountByPatient(@PathVariable String patientId) {

		return new ResponseEntity<Long>(appointmentService.getAppointmentscountByPatientId(patientId), HttpStatus.OK);

	}

}
