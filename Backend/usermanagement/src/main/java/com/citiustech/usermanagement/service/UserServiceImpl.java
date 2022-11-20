package com.citiustech.usermanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.citiustech.usermanagement.dto.ChangePasswordDto;
import com.citiustech.usermanagement.dto.PatientDto;
import com.citiustech.usermanagement.entity.Gender;
import com.citiustech.usermanagement.entity.Patient;
import com.citiustech.usermanagement.entity.Person;
import com.citiustech.usermanagement.entity.Role;
import com.citiustech.usermanagement.entity.Status;
import com.citiustech.usermanagement.exception.ItemAlreadyExistException;
import com.citiustech.usermanagement.exception.ResourceNotFoundException;
import com.citiustech.usermanagement.exception.StatusNotActiveException;
import com.citiustech.usermanagement.repository.PatientRepository;
import com.citiustech.usermanagement.utililty.UtilityService;

@Service
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private UtilityService utilityService;

	@Autowired
	private EmailSenderService emailService;

	/*
	 * Method to register new patient user into database as a Model of PatientDto
	 * class
	 * 
	 * @param patientDto
	 * 
	 * @return A Patient representing the Patient class
	 */
	@Override
	public Patient registerPatientUser(PatientDto user) {

		if (patientRepository.existsByEmail(user.getEmail())) {
			throw new ItemAlreadyExistException("Patient user already exist with the email " + user.getEmail());
		}

		Patient newPatientUser = new Patient();
		Person person = new Person();

		person.setContactNumber(user.getContactNumber());
		person.setDateOfBirth(user.getDateOfBirth());
		person.setFirstName(user.getFirstName());
		person.setGender(Gender.valueOf(user.getGender()));
		person.setLastName(user.getLastName());
		person.setTitle(user.getTitle());

		newPatientUser.setEmail(user.getEmail());
		newPatientUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newPatientUser.setStatus(Status.ACTIVE);
		newPatientUser.setRole(Role.PATIENT);
		newPatientUser.setPerson(person);

		return patientRepository.save(newPatientUser);
	}

	/*
	 * Method to retrieve the Patient user
	 * 
	 * @param email representing the Patient's email id
	 * 
	 * @return A Patient representing the Patient class
	 */
	@Override
	public Patient getPatientByEmail(String email) throws Exception {

		Patient patient = patientRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Patient user not found with the email : " + email));
		if(patient.isDeleted() == true)
			throw new Exception(" Patient already got deleted");
		return patient;
	}

	/*
	 * Method to retrieve All Patient user
	 * 
	 * @return A list of patient's representing the Patient class
	 */
	@Override
	public List<Patient> getAllPatients() {
		
		List<Patient> patientList = patientRepository.findAll();

		return patientList.stream().filter( p -> p.isDeleted() == false).collect(Collectors.toList());
	}

	/*
	 * Method to read the Patient user
	 * 
	 * @return A Patient representing the Patient class
	 */
	@Override
	public Patient readPatientUser() throws ResourceNotFoundException {

		String email = getLoggedInPatientUser().getEmail();
		return patientRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Patient user not found with the email : " + email));
		
		
	}

	/*
	 * Method to retrieve the Logged in Patient user
	 * 
	 * @return A Patient representing the Patient class
	 */
	@Override
	public Patient getLoggedInPatientUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();
		
		Status status = null;
		try {
			status = getPatientByEmail(email).getStatus();
		} catch (Exception e) {
			logger.error("Patient already got deleted");
		}
		if(!status.equals(Status.ACTIVE)) {
			throw new  StatusNotActiveException("Status of "+email+" is not Active, Please check with admin");
		}

		return patientRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Patient user not exist for the email : " + email));

	}

	/*
	 * Method to Change the password of patient user
	 *
	 */
	@Override
	public void changePassword(Patient patientUser, ChangePasswordDto changePasswordDto) throws Exception {

		String currentPassword = changePasswordDto.getOldPassword();
		String newPassword = changePasswordDto.getNewPassword();
		String confirmNewPassword = changePasswordDto.getConfirmPassword();

		if (!(bcryptEncoder.matches(currentPassword, patientUser.getPassword()))) {
			throw new Exception("Password not matching");
		} else if (!newPassword.equals(confirmNewPassword)) {
			throw new Exception("New Password and Confirm password should be same");
		}

		patientUser.setPassword(bcryptEncoder.encode(newPassword));
		patientRepository.save(patientUser);

	}

	/*
	 * Method used to forgot password
	 *
	 */
	@Override
	public void forgotPassword(Patient patientUser) {

		String randomPassword = utilityService.generateRandomPassword();

		patientUser.setPassword(bcryptEncoder.encode(randomPassword));
		patientRepository.save(patientUser);

		emailService.sendDefaultPasswordEmail(patientUser, randomPassword);

	}

	/*
	 * Method to retrieve the Patient user
	 * 
	 * @param id representing the unique identity of Patient
	 * 
	 * @return A Patient representing the Patient class
	 */
	@Override
	public Patient getPatientById(String id) throws Exception {

		Patient patient =  patientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Patient user not found with the id : " + id));
		
		if(patient.isDeleted() == true)
			throw new Exception(" Patient already got deleted");
		return patient;
		
		
	}

	@Override
	public Long patientCount() {

		return patientRepository.findAll().stream().filter( p -> p.isDeleted() == false).count();

		
	}

	@Override
	public String deletePatientByEmail(String emailId) {
		
		Patient patient;
		try {
			patient = getPatientByEmail(emailId);
			patient.setDeleted(true);
			patientRepository.save(patient);
			return "Patient soft deleted";
		} catch (Exception e) {
			logger.error("Patient Not Deleted");
		}
		
		return "Patient Not deleted";
		
	}

	@Override
	public String deletePatient(Patient patient) {
		
		try {
			patient.setDeleted(true);
			patientRepository.save(patient);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "Patient soft deleted";
		
	}

}
