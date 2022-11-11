package com.citiustech.usermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citiustech.usermanagement.entity.LoginDetails;
import com.citiustech.usermanagement.repository.LoginDetailsRepository;

@Service
public class LoginDetailsServiceImpl implements LoginDetailsService {

	@Autowired
	private LoginDetailsRepository loginDetailsRepository;

	/*
	 * Method to save login details of a patient user into database as a Model of
	 * LoginDetails class
	 * 
	 * @param patientDto
	 * 
	 * @return A Patient representing the Patient class
	 */

	@Override
	public void saveLoginDetailsOfPatientUser(LoginDetails loginDetails) {

		loginDetailsRepository.save(loginDetails);

	}

}
