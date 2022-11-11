package com.citiustech.usermanagement.utililty;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.citiustech.usermanagement.entity.LoginDetails;
import com.citiustech.usermanagement.repository.PatientRepository;
import com.citiustech.usermanagement.service.LoginDetailsService;

@Component
public class UtilityService {
	
	/*
	 * Logger declaration to generate logs
	 * 
	 * @param UtilityService.class
	 */

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private PatientRepository patientRepository;
	


	public void createLoginDetails(String email, LocalDateTime loginTime) {

		LoginDetails loginDetails = new LoginDetails();

		loginDetails.setLastLoginTime(loginTime);
		loginDetails.setEmail(email);
		loginDetails.setPerson(patientRepository.findByEmail(email).get().getPerson());

		loginDetailsService.saveLoginDetailsOfPatientUser(loginDetails);

	}

	/**
	 * Generating random password
	 */

	public String generateRandomPassword() {
		
		String generatedPassword = "";

		generatedPassword = RandomStringUtils.randomAlphabetic(1).toUpperCase();
		generatedPassword += RandomStringUtils.randomAlphabetic(1).toLowerCase();
		generatedPassword += RandomStringUtils.randomAlphanumeric(5);
		generatedPassword += RandomStringUtils.randomNumeric(1);
		generatedPassword += RandomStringUtils.randomAlphabetic(2).toLowerCase();

		return generatedPassword;
	}

}
