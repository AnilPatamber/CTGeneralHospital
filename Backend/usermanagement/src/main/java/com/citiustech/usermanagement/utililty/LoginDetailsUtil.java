package com.citiustech.usermanagement.utililty;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citiustech.usermanagement.entity.LoginDetails;
import com.citiustech.usermanagement.repository.PatientRepository;
import com.citiustech.usermanagement.service.LoginDetailsService;

@Component
public class LoginDetailsUtil {

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private PatientRepository patientRepository;

	public void createLoginDetails(String email, LocalDateTime loginTime) {

		LoginDetails loginDetails = new LoginDetails();

		loginDetails.setLastLoginTime(loginTime);
		loginDetails.setPerson(patientRepository.findByEmail(email).get().getPerson());

		loginDetailsService.saveLoginDetailsOfPatientUser(loginDetails);

	}

}
