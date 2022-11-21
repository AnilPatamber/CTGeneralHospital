package com.citiustech.patient.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citiustech.patient.entity.PatientDetails;
import com.citiustech.patient.service.PatientDetailSevice;

@RestController
@RequestMapping("/patient-details")
public class PatientDetailsController {

	/*
	 * Logger declaration to generate logs
	 * 
	 * @param PatientDetailsController.class
	 */

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PatientDetailSevice patientDetailSevice;

	/*
	 * Rest endpoint to add patient details of existing patient into database as a
	 * Model of PatientDetails class
	 * 
	 * @param patientDetailsObj representing the PatientDetails
	 * 
	 * @return A ResponseEntity representing the PatientDetails class
	 */
	@PostMapping("/add")
	public ResponseEntity<PatientDetails> createPatientDetails(@RequestBody PatientDetails patientDetailsObj) {

		PatientDetails patientDetails = null;
		try {
			patientDetails = patientDetailSevice.addPatientDetails(patientDetailsObj);
			logger.info("Patient Details Added Successfull");
			return new ResponseEntity<PatientDetails>(patientDetails, HttpStatus.OK);

		} catch (Exception e) {
			logger.info("Patient Details Added Failed");
			return new ResponseEntity<PatientDetails>(patientDetails, HttpStatus.BAD_REQUEST);
		}
	}
	
	/*
	 * Rest endpoint to add patient details of existing patient into database as a
	 * Model of PatientDetails class
	 * 
	 * @param patientDetailsObj representing the PatientDetails
	 * 
	 * @return A ResponseEntity representing the PatientDetails class
	 */
	@GetMapping("/{patientId}")
	public ResponseEntity<PatientDetails> getPatientDetailsByPatientId(@PathVariable String patientId) {

		PatientDetails patientDetails = null;
		try {
			patientDetails = patientDetailSevice.getPatientDetailsByPatientId(patientId);
			logger.info("Patient Details Fetched Successfull");
			return new ResponseEntity<PatientDetails>(patientDetails, HttpStatus.OK);

		} catch (Exception e) {
			logger.info("Patient Details Fetch Failed");
			return new ResponseEntity<PatientDetails>(patientDetails, HttpStatus.BAD_REQUEST);
		}
	}

}
