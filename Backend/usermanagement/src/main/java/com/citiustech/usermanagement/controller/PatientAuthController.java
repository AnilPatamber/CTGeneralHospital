package com.citiustech.usermanagement.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citiustech.usermanagement.dto.ChangePasswordDto;
import com.citiustech.usermanagement.dto.LoginDto;
import com.citiustech.usermanagement.dto.PatientDto;
import com.citiustech.usermanagement.dto.ResponseObject;
import com.citiustech.usermanagement.entity.Patient;
import com.citiustech.usermanagement.entity.Status;
import com.citiustech.usermanagement.exception.StatusNotActiveException;
import com.citiustech.usermanagement.service.EmailSenderService;
import com.citiustech.usermanagement.service.UserService;
import com.citiustech.usermanagement.utililty.UtilityService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/patient")
public class PatientAuthController {

	/*
	 * Logger declaration to generate logs
	 * 
	 * @param PatientAuthController.class
	 */

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private UtilityService utilityService;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private AuthenticationManager authenticationManager;

	/*
	 * Rest endpoint to register new patient user into database as a Model of
	 * PatientDto class
	 * 
	 * @param patientDto
	 * 
	 * @return A ResponseEntity representing the ResponseObject class
	 */

	@PostMapping("/register")
	@ApiOperation("Register Patient")
	public ResponseEntity<ResponseObject> registerPatientUser(@Valid @RequestBody PatientDto patientDto) {

//		try {

			userService.registerPatientUser(patientDto);
			ResponseObject response = new ResponseObject(HttpStatus.CREATED.value(), "User created successfully",
					LocalDateTime.now());
			logger.info("Valid User response : {}", response);
			emailSenderService.sendWelcomeEmail(patientDto.getFirstName(), patientDto.getEmail());
			return new ResponseEntity<>(response, HttpStatus.CREATED);
//
//		} catch (Exception e) {
//
//			ResponseObject response = new ResponseObject(HttpStatus.BAD_REQUEST.value(),
//					"User was not created successfully", LocalDateTime.now());
//			logger.error("Error User response : {}", response);
//			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//
//		}

	}

	/*
	 * To login patient user into application
	 * 
	 * @param loginDto representing the email and password of Patient class
	 * 
	 * @return A ResponseEntity representing the ResponseObject class
	 */

	@PostMapping("/login")
	@ApiOperation("Login Patient")
	public ResponseEntity<ResponseObject> loginPatientUser(@Valid @RequestBody LoginDto loginDto) throws Exception {

		LocalDateTime currentTime = LocalDateTime.now();

		try {

			authenticatePatient(loginDto.getEmail(), loginDto.getPassword());
			utilityService.createLoginDetails(loginDto.getEmail(), currentTime);
			logger.info("Login Successfull");
			ResponseObject response = new ResponseObject(HttpStatus.OK.value(), "Login Successfull", currentTime);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {

			logger.error("Login Unsuccessfull, Please check the credentials and status is Active");
			ResponseObject response = new ResponseObject(HttpStatus.UNAUTHORIZED.value(), "Login Unsuccessfull",
					currentTime);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

		}

	}

	/*
	 * To authenticate patient user
	 * 
	 * @param email The Patient's email id
	 * 
	 * @param password The Patient's password
	 * 
	 */

	private void authenticatePatient(String email, String password) throws Exception {

		try {
			Status status = userService.getPatientByEmail(email).getStatus();

			if (!status.equals(Status.ACTIVE)) {
				throw new StatusNotActiveException("User Status is not active, Please contact to ADMIN");
			}
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			logger.info("Authentication successfull for the patient");

		} catch (DisabledException e) {

			logger.error("Error occured while authentication" + "User Disabled Exception" + e.getMessage());
			throw new Exception("User Disabled");

		} catch (BadCredentialsException e) {

			logger.error("Error occured while authentication" + "Bad Credentials Exception " + e.getMessage());
			throw new Exception("Bad Credentials");

		}

	}

	/*
	 * Rest endpoint to change the password for patient user
	 * 
	 * @param email representing the email
	 * 
	 * @param passwordDto representing the ChangePasswordDto class
	 * 
	 * @return A ResponseEntity representing the ResponseObject class
	 */

	@PostMapping("/changepassword/{email}")
	@ApiOperation("Change Password For Patient")
	public ResponseEntity<ResponseObject> changePasswordOfPatientUser(@PathVariable String email,
			@RequestBody ChangePasswordDto passwordDto) {

		try {
			Patient patientUser = userService.getPatientByEmail(email);
			userService.changePassword(patientUser, passwordDto);
			logger.info("Password change successfull");
			ResponseObject response = new ResponseObject(HttpStatus.OK.value(), "Password change Successfull",
					LocalDateTime.now());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);

		} catch (Exception e) {
			logger.info("Password change failed");
			ResponseObject response = new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Password change failed",
					LocalDateTime.now());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);

		}

	}

	/*
	 * Rest endpoint to update the password for patient user
	 * 
	 * @param email representing the email
	 * 
	 * @return A ResponseEntity representing the ResponseObject class
	 */

	@PostMapping("/forgotpassword/{email}")
	@ApiOperation("Forgot Password For Patient")
	public ResponseEntity<ResponseObject> forgotPasswordOfPatientUser(@PathVariable String email) {

		try {

			Patient patientUser = userService.getPatientByEmail(email);
			userService.forgotPassword(patientUser);
			logger.info("Default password sent successfull");
			ResponseObject response = new ResponseObject(HttpStatus.OK.value(), "Default password sent successfull",
					LocalDateTime.now());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("Default password sending failed");
			ResponseObject response = new ResponseObject(HttpStatus.BAD_REQUEST.value(),
					"Default password sending failed", LocalDateTime.now());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		}

	}

}
