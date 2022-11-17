package com.citiustech.appointment.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citiustech.appointment.entity.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
	
	public List<Appointment> findByPatientId(String patientId);
	
	public List<Appointment> findByPhysicianId(String physicianId);
	
	public List<Appointment> findByAppointmentDateBetween(LocalDate startDate, LocalDate endDate);

}
