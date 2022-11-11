package com.citiustech.patient.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citiustech.patient.entity.PatientDetails;
import com.citiustech.patient.exception.ResourceNotFoundException;
import com.citiustech.patient.repository.PatientDetailsRepository;

@Service
public class PatientDetailServiceImpl implements PatientDetailSevice {

	@Autowired
	private PatientDetailsRepository patientDetailsRepository;

	@Override
	public PatientDetails addPatientDetails(PatientDetails patientDetails) {
		PatientDetails newPatientDetails = new PatientDetails();
		BeanUtils.copyProperties(patientDetails, newPatientDetails);

		// fetch patient details through logged in patient from user module
		newPatientDetails.setPatientId("patient000001");
		return patientDetailsRepository.save(newPatientDetails);

	}

	@Override
	public PatientDetails getPatientDetailsByPatientId(String patientId) {
		return patientDetailsRepository.findByPatientId(patientId).orElseThrow(
				() -> new ResourceNotFoundException("Patient details not found from patient id : " + patientId));
	}


	@Override
	public PatientDetails updatePatientDetails(PatientDetails patientDetails) {
		// TODO Auto-generated method stub
		return null;
	}

}
