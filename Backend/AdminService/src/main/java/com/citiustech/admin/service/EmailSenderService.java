package com.citiustech.admin.service;

import com.citiustech.admin.dto.MailDto;
import com.citiustech.admin.dto.ResponseObject;

public interface EmailSenderService {
	public ResponseObject sendEmail(MailDto request);
   public MailDto sendWelcomeEmail(String firstName,String email);

	public MailDto deactivationEmail(String firstName, String email);
	public MailDto sendOtpOverEmail(String firstName, String emailID, String otp);
}
