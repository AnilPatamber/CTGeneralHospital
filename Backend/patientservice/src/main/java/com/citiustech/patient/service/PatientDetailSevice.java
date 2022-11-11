package com.citiustech.patient.service;

import com.citiustech.patient.entity.PatientDetails;

public interface PatientDetailSevice {
	
	public PatientDetails addPatientDetails(PatientDetails patientDetails);
	
	public PatientDetails getPatientDetailsByPatientId(String patientId);
	
	public PatientDetails updatePatientDetails(PatientDetails patientDetails);
	
	

}
