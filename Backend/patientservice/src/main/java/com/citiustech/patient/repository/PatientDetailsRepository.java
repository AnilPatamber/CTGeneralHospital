package com.citiustech.patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citiustech.patient.entity.PatientDetails;

@Repository
public interface PatientDetailsRepository extends JpaRepository<PatientDetails, String> {
	
	
	public Optional<PatientDetails> findByPatientId(String patientId);
	


}
