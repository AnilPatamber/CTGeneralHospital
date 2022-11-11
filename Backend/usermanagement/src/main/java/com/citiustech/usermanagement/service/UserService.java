package com.citiustech.usermanagement.service;

import java.util.List;

import com.citiustech.usermanagement.dto.ChangePasswordDto;
import com.citiustech.usermanagement.dto.PatientDto;
import com.citiustech.usermanagement.entity.Patient;

public interface UserService {

	public Patient registerPatientUser(PatientDto user);

	public Patient getPatientByEmail(String email);
	
	public Patient getPatientById(String id);

	public List<Patient> getAllPatients();

	public Patient getLoggedInPatientUser();

	public Patient readPatientUser();

	public void changePassword(Patient patientUser, ChangePasswordDto changePasswordDto) throws Exception;

	public void forgotPassword(Patient patientUser);
	
	public Long patientCount();

}
