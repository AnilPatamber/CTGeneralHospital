
package com.citiustech.usermanagement.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.citiustech.usermanagement.dto.MailDto;
import com.citiustech.usermanagement.dto.ResponseObject;
import com.citiustech.usermanagement.entity.Patient;

/**
 * Service class to send email
 */

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
		String url = "http://localhost:8001";
		String subject = "Welcome to CT  Hospital";
		String emailBody = "Hi " + firstName + ",<br><br>"
				+ "</br>Welcome to CT  Hospital, please visit CT  Hospital website to get more details.</br>URL: " + url
				+ "<br><br><br> <p>" + "Regards, <br />" + "<em>CT Hospital</em>" + "</p><br> ";
		MailDto welcomeRequest = new MailDto(firstName, email, "cthospital2022@outlook.com", subject, emailBody);
		sendEmail(welcomeRequest);
		return welcomeRequest;
	}

	/**
	 * Method to send default password mail
	 * 
	 * @param User details
	 * @return Mail request object
	 */

	@Override
	public MailDto sendDefaultPasswordEmail(Patient patientUser, String defaultPassword) {
		String subject = "Default Password for CT General Hospital";
		String emailBody = "Hi " + patientUser.getPerson().getFirstName() + ",<br><br>"
				+ "</br>Please use the default password provided in the mail to log in.</br></br>Password: " + "<b>"
				+ defaultPassword + "</b></br><br>" + "<br><br> <p>\r\n" + "Regards, <br />\r\n"
				+ "<em>PMS Hospital</em>\r\n" + "</p> ";

		MailDto request = new MailDto(patientUser.getPerson().getFirstName(), patientUser.getEmail(),
				"cthospital2022@outlook.com", subject, emailBody);
		sendEmail(request);
		return request;
	}

}
