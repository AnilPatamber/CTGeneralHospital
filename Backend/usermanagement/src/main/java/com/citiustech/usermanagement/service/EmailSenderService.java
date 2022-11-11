package com.citiustech.usermanagement.service;

import com.citiustech.usermanagement.dto.MailDto;
import com.citiustech.usermanagement.dto.ResponseObject;
import com.citiustech.usermanagement.entity.Patient;

public interface EmailSenderService {

	public ResponseObject sendEmail(MailDto request);

	public MailDto sendWelcomeEmail(String firstName,String email);
	
	public MailDto sendDefaultPasswordEmail(Patient patientUser, String defaultPassword);

	
	

}
