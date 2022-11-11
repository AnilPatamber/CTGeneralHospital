package com.citiustech.admin.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.citiustech.admin.dto.MailDto;
import com.citiustech.admin.dto.ResponseObject;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
	@Autowired
	private JavaMailSender mailSender;
	
	/**
	 * Method to send email using SMTP
	 * 
	 * @param Mail request details
	 * @return Status of sent email response
	 */

	@Override
	public ResponseObject sendEmail(MailDto request) {
		ResponseObject response = new ResponseObject();

		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			helper.setTo(request.getTo());
			helper.setFrom(request.getFrom());
			helper.setSubject(request.getSubject());
			helper.setText(request.getMailBody(), true);

			mailSender.send(message);

			response.setMessage("Email sent to : " + request.getTo());
			response.setHttpStatusCode(HttpStatus.OK.value());
			response.setLocalDateTime(LocalDateTime.now());

		} catch (Exception e) {

			response.setMessage("Email Sending failure : " + e.getMessage());
			response.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
			response.setLocalDateTime(LocalDateTime.now());
		}
		return response;
	}

	/**
	 * Method to send welcome email
	 * 
	 * @param Patient user details
	 * @return Mail request object
	 */

	@Override
	public MailDto sendWelcomeEmail(String firstName, String email) {
		System.out.println("inside sendWelcomeEmail");
		String url = "http://localhost:8080";
		String subject = "Welcome to CT  Hospital";
		String emailBody = "Hi " + firstName + ",<br><br>"
				+ "</br>Welcome to CT  Hospital, please visit CT  Hospital website to get more details.</br>URL: " + url
				+ "<br><br><br> <p>" + "Regards, <br />" + "<em>CT Hospital</em>" + "</p><br> ";
		MailDto welcomeRequest = new MailDto(firstName, email, "cthospital2022@outlook.com", subject, emailBody);
		sendEmail(welcomeRequest);
		return welcomeRequest;
	}

	public MailDto sendOtpOverEmail(String firstName, String email,String otp) {
		String url = "http://localhost:8080";
		String subject = "OTP For Lgin CT Hospital";
		System.out.println("Anil..................................................");
	
		
		String emailBody = "Hi " + firstName + ",<br><br>" + "</br>Welcome to CT  Hospital,OTP for</br> Login : " + otp
				+ "</br> <br>" + "please visit CT  Hospital website to get more details.</br>URL: " + url
				+ "<br><br><br> <p>" + "Regards, <br />" + "<em>CT Hospital</em>" + "</p><br> ";
		MailDto welcomeRequest = new MailDto(firstName, email, "cthospital2022@outlook.com", subject, emailBody);
		sendEmail(welcomeRequest);
		return welcomeRequest;
	}

	@Override
	public MailDto deactivationEmail(String firstName, String email) {
		System.out.println("inside sendWelcomeEmail");
		String url = "http://localhost:8080";
		String subject = "Deactivation Notification";
		String emailBody = "Hi " + firstName + ",<br><br>"
				+ "</br>Your CT Account is deactivated,to activate please contact admin.</br>URL: " + url
				+ "<br><br><br> <p>" + "Regards, <br />" + "<em>CT Hospital</em>" + "</p><br> ";
		MailDto deactivateRequest = new MailDto(firstName, email, "cthospital2022@outlook.com", subject, emailBody);
		sendEmail(deactivateRequest);
		return deactivateRequest;

	}

	

}
