package com.citiustech.appointment.service;

import java.time.LocalDate;
import java.util.List;

import com.citiustech.appointment.dto.AppointmentDto;
import com.citiustech.appointment.dto.ResponseObject;
import com.citiustech.appointment.entity.Appointment;

public interface AppointmentService {
	
	public ResponseObject addAppointment(AppointmentDto appointmentDto);
	
	public List<Appointment> getAllAppointments();
	
	public Appointment getByAppointmentId(Long appointmentId);
	
	public ResponseObject updateAppointment(Long appointmentId, AppointmentDto appointment);
	
	public ResponseObject deleteAppointment(Appointment appointment);
	
	public ResponseObject deleteAppointmentById(Long appointmentId);
	
	public List<Appointment> readByPhysicianId(String physicianId);
	
	public List<Appointment> readByPatientId(String patientId);
	
	public List<Appointment> readByDate(LocalDate startDate, LocalDate endDate);
	
	public Long getAppointmentscount();
	
	public Long getAppointmentscountByPhysicianId(String physicianId);
	
	public Long getAppointmentscountByPatientId(String patientId);
}
