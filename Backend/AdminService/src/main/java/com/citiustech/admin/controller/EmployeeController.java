package com.citiustech.admin.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citiustech.admin.dto.ChangePasswordDto;
import com.citiustech.admin.dto.EmployeeDto;
import com.citiustech.admin.dto.LoginDto;
import com.citiustech.admin.dto.ResponseObject;
import com.citiustech.admin.entity.Employee;
import com.citiustech.admin.entity.Status;
import com.citiustech.admin.exception.EmployeeAlreadyExistException;
import com.citiustech.admin.service.EmailSenderService;
import com.citiustech.admin.service.EmployeeService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/ct-pms")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private AuthenticationManager authenticationManager;

	/*
	 * Logger declaration to generate logs
	 * 
	 * @param PatientAuthController.class
	 */
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * Rest endpoint to register new employee user into database as a Model of
	 * EmployeeDto class
	 * 
	 * @param employeeDto
	 * 
	 * @return A ResponseEntity representing the ResponseObject class
	 */
	@PostMapping(value = "/employees")
	@ApiOperation("Add Employee")
	public ResponseEntity<ResponseObject> addEmployee(@RequestBody EmployeeDto employeeDto)
			throws EmployeeAlreadyExistException {
//		try {
		employeeService.addEmployee(employeeDto);
		emailSenderService.sendWelcomeEmail(employeeDto.getPersonDto().getFirstName(), employeeDto.getEmailID());
		ResponseObject response = new ResponseObject(HttpStatus.CREATED.value(), "User created successfully",
				LocalDateTime.now());
		logger.info("add employee response : {}", response);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
//		} catch (Exception e) {
//			ResponseObject response = new ResponseObject(HttpStatus.BAD_REQUEST.value(),
//					"User was not created successfully", LocalDateTime.now());
//			logger.error("Error employee response : {}", response);
//			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//		}
	}

	/*
	 * Rest endpoint to find employee using employeeID
	 * 
	 * 
	 * @param emplyoeeID
	 * 
	 * @return A ResponseEntity representing the employeeDto object
	 */
	@GetMapping(value = "/employees/{emplyoeeID}")
	@ApiOperation("Get Employee By Employee ID")
	public ResponseEntity<Employee> getEmplyoeeByID(@PathVariable String emplyoeeID) {
		Employee employee = null;
		try {
			employee = employeeService.getEmployeeByID(emplyoeeID);
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Employee not exist");
			return new ResponseEntity<Employee>(employee, HttpStatus.BAD_REQUEST);
		}

	}

	/*
	 * Rest endpoint to change/reset employee password
	 * 
	 * 
	 * @param changePasswordDto
	 * 
	 * @return A ResponseEntity representing the ResponseObject class
	 */
	@PutMapping(value = "/employees/pass/{emailID}")
	@ApiOperation("Change Password")
	public ResponseEntity<ResponseObject> updateEmployeePassword(@PathVariable String emailID,
			@RequestBody ChangePasswordDto changePasswordDto) {
		try {

			employeeService.updateEmployeePassword(emailID, changePasswordDto);

			ResponseObject response = new ResponseObject(HttpStatus.OK.value(), "Password change Successfull",
					LocalDateTime.now());
			logger.info("update employee password response : {}", response);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);

		} catch (Exception e) {
			ResponseObject response = new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Password change failed",
					LocalDateTime.now());
			logger.error("Error update password employee response : {}", response);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);

		}
	}

	/*
	 * Rest endpoint to get employee from database using firstName
	 *
	 * @param firstName
	 * 
	 * @return A ResponseEntity representing the ResponseObject class
	 */
	@GetMapping(value = "/employees/name/{firstName}")
	@ApiOperation("Get Employee By First Name")
	public ResponseEntity<List<Employee>> getEmplyoeeByFirstName(@PathVariable String firstName) {
		List<Employee> employee = employeeService.getEmployeeByFirstName(firstName);
		logger.info("fetching employee by firstName successfully");
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	/*
	 * Rest endpoint to get employee from database using firstName
	 * 
	 * @param firstName
	 * 
	 * @return A ResponseEntity representing the ResponseObject class
	 */
	@GetMapping(value = "/employees/pnames")
	@ApiOperation("Get All Physician Names")
	public ResponseEntity<List<String>> getPhysicianNames() {

		List<String> physicianNamesList = employeeService.getPhysicianNames();
		logger.info("fetching physician names successfull");
		return new ResponseEntity<List<String>>(physicianNamesList, HttpStatus.OK);

	}

	/*
	 * Rest endpoint to upadate/change employee status
	 * 
	 * @param emplyeeID,status
	 * 
	 * @return A ResponseEntity representing the ResponseObject class
	 */
	@PutMapping(value = "/employees/{employeeID}")
	@ApiOperation("Update Employee Status")
	public ResponseEntity<ResponseObject> updateEmployeeStatus(@PathVariable String employeeID,
			@RequestBody String status) {
		try {
			employeeService.updateEmployeeStatus(employeeID, status);
			ResponseObject response = new ResponseObject(HttpStatus.OK.value(), "Status updated Successfully",
					LocalDateTime.now());
			logger.info("update  employee status response : {}", response);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);

		} catch (Exception e) {
			ResponseObject response = new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Status updation failed",
					LocalDateTime.now());
			logger.error("Error employee status response : {}", response);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		}

	}
	/*
	 * Rest endpoint to upadate employee in database as model of Class Employee
	 * 
	 * @param emplyeeID,employeeDto
	 * 
	 * @return A ResponseEntity representing the ResponseObject class
	 */

	@PutMapping(value = "/employees/update/{employeeID}")
	@ApiOperation("Update Employee")
	public ResponseEntity<ResponseObject> updateEmployee(@PathVariable String employeeID,
			@RequestBody EmployeeDto employeeDto) {
		try {
			employeeService.updateEmployee(employeeID, employeeDto);
			ResponseObject response = new ResponseObject(HttpStatus.OK.value(), "employee updated Successfully",
					LocalDateTime.now());
			logger.info("update  whole employee  response : {}", response);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (Exception e) {
			ResponseObject response = new ResponseObject(HttpStatus.BAD_REQUEST.value(), "employee updation failed",
					LocalDateTime.now());
			logger.error("Error whole employee  response : {}", response);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		}
	}

	/*
	 * Rest endpoint to forgot password functionality
	 * 
	 * @paramemailID
	 * 
	 * @return A ResponseEntity representing the ResponseObject class
	 */
	@PutMapping(value = "/employees/forgot/{emailID}")
	@ApiOperation("Forgot Password")
	public ResponseEntity<ResponseObject> forgotPassword(@PathVariable String emailID) {
		try {
			employeeService.forgotPassword(emailID);
			ResponseObject response = new ResponseObject(HttpStatus.OK.value(), "employee password reset Successfully",
					LocalDateTime.now());
			logger.info("forgot password employee  response : {}", response);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);

		} catch (Exception e) {
			ResponseObject response = new ResponseObject(HttpStatus.BAD_REQUEST.value(),
					"employee password reset failed", LocalDateTime.now());
			logger.error("Error employee password  response : {}", response);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		}

	}

	/*
	 * Rest endpoint to loging employee
	 * 
	 * @param emailID,password
	 * 
	 * @return A ResponseEntity representing the ResponseObject class
	 */
	@PostMapping("/employees/login")
	@ApiOperation("Login Employee")
	public ResponseEntity<ResponseObject> loginEmployeeUser(@RequestBody LoginDto loginDto) throws Exception {

		LocalDateTime currentTime = LocalDateTime.now();

		try {

			authenticatePatient(loginDto.getEmail(), loginDto.getPassword());
			ResponseObject response;
			boolean isDefault = employeeService.getEmployeeByEmailID(loginDto.getEmail()).isDefault();
			if (isDefault) {
				logger.info("Login Successfull with Default Password");
				response = new ResponseObject(HttpStatus.ALREADY_REPORTED.value(),
						"Login Successfull with Default Password", currentTime);
			}

			else {
				logger.info("Login Successfull");
				response = new ResponseObject(HttpStatus.OK.value(), "Login Successfull", currentTime);
			}
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {

			logger.error("Login Unsuccessfull, Please check the credentials and status is Active");
			ResponseObject response = new ResponseObject(HttpStatus.UNAUTHORIZED.value(), "Login Unsuccessfull",
					currentTime);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

		}

	}

	private void authenticatePatient(String email, String password) throws Exception {

		try {
			Status status = employeeService.getEmployeeByEmailID(email).getStatus();

			if (!status.equals(Status.ACTIVE)) {
				throw new Exception("User Status is not active, Please contact to ADMIN");
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
	 * Rest endpoint to get all employee list from database
	 * 
	 * 
	 * @return A ResponseEntity representing the list of employee
	 */
	@GetMapping(value = "/employees")
	@ApiOperation("Get All Employees")
	public ResponseEntity<List<Employee>> getAllEmployee() {
		List<Employee> employee = employeeService.getAllEmployees();
		logger.info("fetching employees successfully");
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@GetMapping(value = "/physician")
	@ApiOperation("Get All Physicains")
	public ResponseEntity<List<Employee>> getAllPhysicians() {

		List<Employee> employee = employeeService.getAllPhysician();
		logger.info("fetching physicians successfully");
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@DeleteMapping("employee/delete/{email}")
	@ApiOperation("Delete Employee By Email")
	public String deleteEmployee(@PathVariable String email) {

		employeeService.deleteEmployee(email);
		return " soft deleted";

	}

	@GetMapping
	@ApiOperation("Welcome")
	public String getAdminService() {
		return "Welcome to Admin Service";
	}
}