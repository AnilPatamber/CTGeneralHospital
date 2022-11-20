package com.citiustech.usermanagement.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.citiustech.usermanagement.dto.LoginDto;
import com.citiustech.usermanagement.dto.ResponseObject;
import com.citiustech.usermanagement.entity.Patient;
import com.citiustech.usermanagement.entity.Status;
import com.citiustech.usermanagement.exception.StatusNotActiveException;
import com.citiustech.usermanagement.service.UserService;
import com.citiustech.usermanagement.utililty.UtilityService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class PatientAuthController {

	/*
	 * Logger declaration to generate logs
	 * 
	 * @param UserController.class
	 */

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UtilityService utilityService;

	/*
	 * Rest endpoint to fetch a patient user from the database
	 * 
	 * @param email representing the patient's email id
	 * 
	 * @return A ResponseEntity representing the Patient class
	 */

	@GetMapping("/patient/{email}")
	@ApiOperation("Get Patient By Email")
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
	@ApiOperation("Get Patient By ID")
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
	@ResponseStatus(value = HttpStatus.FOUND)
	@GetMapping("/patients")
	@ApiOperation("Get All Patients")
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

	@GetMapping("/patient/count")
	@ApiOperation("Get Patient Count")
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

	@DeleteMapping("/patient/delete/{email}")
	@ApiOperation("Delete Patient By Email")
	public ResponseEntity<String> deletePatientByEmail(@PathVariable String email) {

		return new ResponseEntity<String>(userService.deletePatientByEmail(email), HttpStatus.OK);
	}

	@DeleteMapping("/patient/delete")
	@ApiOperation("Delete Patient")
	public ResponseEntity<String> deletePatient(@RequestBody Patient patient) {

		return new ResponseEntity<String>(userService.deletePatient(patient), HttpStatus.OK);
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

}
