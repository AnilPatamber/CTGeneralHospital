package com.citiustech.usermanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD:Backend/usermanagement/usermanagement/src/main/java/com/citiustech/usermanagement/controller/UserController.java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
=======
>>>>>>> 15a41976897eea1bb51c65d030f0fccb627bed34:Backend/usermanagement/src/main/java/com/citiustech/usermanagement/controller/UserController.java
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.citiustech.usermanagement.entity.Patient;
import com.citiustech.usermanagement.service.UserService;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class UserController {

	/*
	 * Logger declaration to generate logs
	 * 
	 * @param UserController.class
	 */

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	/*
	 * Rest endpoint to fetch a patient user from the database
	 * 
	 * @param email representing the patient's email id
	 * 
	 * @return A ResponseEntity representing the Patient class
	 */

	@GetMapping("/patient/{email}")
	public ResponseEntity<Patient> getPatientUserByEmailId(@PathVariable String email) {

		Patient patient = null;

		try {

			patient = userService.getPatientByEmail(email);
			logger.info("Patient retrieved successfull");
			return new ResponseEntity<Patient>(patient, HttpStatus.OK);

		} catch (Exception e) {

			logger.info("Patient retrieved failed");
			return new ResponseEntity<Patient>(patient, HttpStatus.BAD_REQUEST);
		}

	}

	/*
	 * Rest endpoint to fetch a patient user from the database
	 * 
	 * @param email representing the patient's email id
	 * 
	 * @return A ResponseEntity representing the Patient class
	 */

	@GetMapping("/patient")
	public ResponseEntity<Patient> getPatientUserById(@RequestParam("id") String id) {

		Patient patient = null;

		try {

			patient = userService.getPatientById(id);
			logger.info("Patient retrieved successfull");
			return new ResponseEntity<Patient>(patient, HttpStatus.OK);

		} catch (Exception e) {

			logger.info("Patient retrieved failed");
			return new ResponseEntity<Patient>(patient, HttpStatus.BAD_REQUEST);
		}

	}

	/*
	 * Rest endpoint to fetch all patient users from the database
	 * 
	 * @return A ResponseEntity representing the list of patient's
	 */

	@GetMapping("/patients")
	public ResponseEntity<List<Patient>> getAllPatientUsers() {

		List<Patient> patientList = new ArrayList<>();

		try {

			patientList = userService.getAllPatients();
			logger.info("Patient list retrieved successfull");
			return new ResponseEntity<List<Patient>>(patientList, HttpStatus.OK);

		} catch (Exception e) {

			logger.info("Patient list retrieved failed");
			return new ResponseEntity<List<Patient>>(patientList, HttpStatus.BAD_REQUEST);

		}

	}

	/*
	 * Rest endpoint to fetch logged patient user as a Model of Patient class
	 * 
	 * @return A ResponseEntity representing the Patient class
	 */

	@GetMapping("/patient/get-login-user")
	public ResponseEntity<Patient> getLoginUser() {

		Patient patientUser = null;

		try {

			patientUser = userService.getLoggedInPatientUser();
			logger.info("Logged in User Authorities"
					+ SecurityContextHolder.getContext().getAuthentication().getAuthorities());
			logger.info("Logged in User fetched");
			return new ResponseEntity<Patient>(patientUser, HttpStatus.OK);
		} catch (Exception e) {

			logger.info("Logged in User fetched failed");
			return new ResponseEntity<Patient>(patientUser, HttpStatus.BAD_REQUEST);
		}

	}

	/*
	 * Rest endpoint to fetch logged patient user as a Model of Patient class
	 * 
	 * @return A ResponseEntity representing the Patient class
	 */

	@GetMapping("patient/get-login-user-authorities")
	public ResponseEntity<List<GrantedAuthority>> getLoginUserAuthority() {

		List<GrantedAuthority> authorities = new ArrayList<>();

		try {
			authorities.addAll(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
			logger.info("Logged in User fetched");
			return new ResponseEntity<List<GrantedAuthority>>(authorities, HttpStatus.OK);
		} catch (Exception e) {

			logger.info("Logged in User fetched failed");
			return new ResponseEntity<List<GrantedAuthority>>(authorities, HttpStatus.BAD_REQUEST);
		}

	}

	
	@GetMapping("/patient/count")
	public ResponseEntity<Long> getPatientCount() {

		Long patientCount = null;
		try {
			patientCount = userService.patientCount();
			logger.info("Patient Count Successfull: " + patientCount);
			return new ResponseEntity<Long>(patientCount, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("Patient Count failed: " + patientCount);
			return new ResponseEntity<Long>(patientCount, HttpStatus.BAD_REQUEST);
		}
	}

}
