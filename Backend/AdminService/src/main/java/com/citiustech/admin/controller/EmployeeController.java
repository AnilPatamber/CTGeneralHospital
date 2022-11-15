package com.citiustech.admin.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citiustech.admin.dto.ChangePasswordDto;
import com.citiustech.admin.dto.EmployeeDto;
import com.citiustech.admin.dto.ResponseObject;
import com.citiustech.admin.entity.Employee;
import com.citiustech.admin.service.EmailSenderService;
import com.citiustech.admin.service.EmployeeService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/ct-pms")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmailSenderService emailSenderService;
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
	public ResponseEntity<ResponseObject> addEmployee(@RequestBody EmployeeDto employeeDto) {
		try {
			employeeService.addEmployee(employeeDto);
			emailSenderService.sendWelcomeEmail(employeeDto.getPersonDto().getFirstName(), employeeDto.getEmailID());
			ResponseObject response = new ResponseObject(HttpStatus.CREATED.value(), "User created successfully",
					LocalDateTime.now());
			logger.info("add employee response : {}", response);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			ResponseObject response = new ResponseObject(HttpStatus.BAD_REQUEST.value(),
					"User was not created successfully", LocalDateTime.now());
			 logger.error("Error employee response : {}", response);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
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
	public ResponseEntity<Employee> getEmplyoeeByID(@PathVariable String emplyoeeID) {
		Employee employee = employeeService.getEmployeeByID(emplyoeeID);
		return new ResponseEntity<>(employee, HttpStatus.OK);

	}

	/*
	 * Rest endpoint to change/reset employee password
	 * 
	 * 
	 * @param changePasswordDto
	 * 
	 * @return A ResponseEntity representing the ResponseObject class
	 */
	@PutMapping(value = "/employees/pass/{employeeID}")
	public ResponseEntity<ResponseObject> updateEmployeePassword(@PathVariable String employeeID,
			@RequestBody ChangePasswordDto changePasswordDto) {
		try {

			employeeService.updateEmployeePassword(employeeID, changePasswordDto);

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
	 * 
	 * @param firstName
	 * 
	 * @return A ResponseEntity representing the ResponseObject class
	 */
	@GetMapping(value = "/employees/name/{firstName}")
	public ResponseEntity<List<Employee>> getEmplyoeeByFirstName(@PathVariable String firstName) {
		List<Employee> employee = employeeService.getEmployeeByFirstName(firstName);
		logger.info("fetching employee by firstName successfully");
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}
	
	
	

	/*
	 * Rest endpoint to upadate/change employee status
	 * 
	 * @param emplyeeID,status
	 * 
	 * @return A ResponseEntity representing the ResponseObject class
	 */
	@PutMapping(value = "/employees/{employeeID}")
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
	@PostMapping("/employees/login/{emailID}")
	public ResponseEntity<ResponseObject> loginEmployeeUser(@PathVariable String emailID, @RequestBody String password)
			throws Exception {

		LocalDateTime currentTime = LocalDateTime.now();
		System.out.println(password);

		try {
			String result = employeeService.loginEmployee(emailID, password);

			logger.info("Login Successfull");
			ResponseObject response = new ResponseObject(HttpStatus.OK.value(), result, currentTime);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {

			logger.error("Error occured while fetching access token");
			ResponseObject response = new ResponseObject(HttpStatus.UNAUTHORIZED.value(),
					"Login Unsuccessfull,Bad Credentials", currentTime);
		
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

		}

	}
	
	/*
	 * Rest endpoint  to get all employee list from database
	 * 
	 * 
	 * @return A ResponseEntity representing the list of employee
     */
	@GetMapping(value = "/employees")
	public ResponseEntity<List<Employee>> getAllEmployee() {
		List<Employee> employee = employeeService.getAllEmployees();
		logger.info("fetching employees successfully");
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}
	
	@GetMapping
	public String getAdminService() {
		return "Welcome to Admin Service";
	}
}