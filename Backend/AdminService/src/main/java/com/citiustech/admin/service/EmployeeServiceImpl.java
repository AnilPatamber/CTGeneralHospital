package com.citiustech.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citiustech.admin.dto.ChangePasswordDto;
import com.citiustech.admin.dto.EmployeeDto;
import com.citiustech.admin.entity.Employee;
import com.citiustech.admin.entity.Person;
import com.citiustech.admin.entity.Status;
import com.citiustech.admin.exception.EmployeeAlreadyExistException;
import com.citiustech.admin.exception.EmployeeNotFoundException;
import com.citiustech.admin.repository.EmployeeRepository;
import com.citiustech.admin.utility.DataTransfer;
import com.citiustech.admin.utility.RandomPassword;

@Service
@Transactional

public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	public EmployeeRepository employeeRepository;
	@Autowired
	private RandomPassword randomPassword;

	@Autowired
	EmailSenderService emailSenderService;
	@Autowired
	DataTransfer dataTransfer;

	@Override
	public List<Employee> getAllEmployees() {
		//Iterable<Employee> employees = employeeRepository.findAll();
//		List<EmployeeDto> employeeDto = new ArrayList<>();
//		employees.forEach(employee -> {
//			employeeDto.add(dataTransfer.addDatatoDto(employee));
//		});
//		return employeeDto;
		return employeeRepository.findAll();

	}

	/*
	 * Method to register new employee user into database as a Model of EmployeeDto
	 * class
	 * 
	 * @param employeeDto
	 * 
	 * @return A employee Name which registerd in database
	 */
	@Override
	public String addEmployee(EmployeeDto employeeDto) throws EmployeeAlreadyExistException {
		if (employeeRepository.existsByEmailID(employeeDto.getEmailID())) {
			throw new EmployeeAlreadyExistException(
					"Patient user already exist with the email " + employeeDto.getEmailID());
		}
		Employee employee =dataTransfer.addDatatoEntity(employeeDto);
		System.out.println(employee);
		Employee employee1 = employeeRepository.save(employee);
		return employee1.getPerson().getFirstName();

	}

	@Override
	public Employee getEmployeeByID(String employeeID) {
		//Optional<Employee> employee = employeeRepository.findById(employeeID);
		//EmployeeDto emplyoeeDto = dataTransfer.addDatatoDto(employee.get());
		return employeeRepository.findById(employeeID).orElseThrow(() -> new EmployeeNotFoundException("Employee user not found with the employeeID : " + employeeID));
	}

	@Override
	public void updateEmployeeStatus(String employeeID, String newStatus) {
		Optional<Employee> optional = employeeRepository.findById(employeeID);
		Employee employee = optional.orElseThrow(
				() -> new EmployeeNotFoundException("Employee user not found with the employeeID : " + employeeID));

		System.out.println(newStatus);
		employee.setStatus(Status.valueOf(newStatus));

		switch (newStatus) {
		case "ACTIVE":
			System.out.println("In active");
			String otp = randomPassword.generateRandomPassword();

			emailSenderService.sendOtpOverEmail(employee.getPerson().getFirstName(), employee.getEmailID(), otp);
			// emailSenderService.sendWelcomeEmail(employee.getPerson().getFirstName(),
			// employee.getEmailID());
			break;
		case "INACTIVE":
			emailSenderService.deactivationEmail(employee.getPerson().getFirstName(), employee.getEmailID());
			break;
		case "BLOCKED":
			// code block
			break;
		default:
			// code block
		}

	}

	@Override
	public void updateEmployeePassword(String employeeID, ChangePasswordDto changePasswordDto) throws Exception {
		Optional<Employee> optional = employeeRepository.findById(employeeID);
		Employee employee = optional.orElseThrow(
				() -> new EmployeeNotFoundException("Employee user not found with the employeeID : " + employeeID));
		
		String currentPassword = changePasswordDto.getOldPassword();
		String newPassword = changePasswordDto.getNewPassword();
		String confirmNewPassword = changePasswordDto.getConfirmPassword();
		if(!(currentPassword.equals(employee.getPassword()))){
			throw new Exception("Password not matching");
		}else if (!newPassword.equals(confirmNewPassword)) {
		throw new Exception("New Password and Confirm password should be same");
	}
		employee.setPassword(newPassword);
		
//		if (!(bcryptEncoder.matches(currentPassword, patientUser.getPassword()))) {
//			throw new Exception("Password not matching");
//		} else if (!newPassword.equals(confirmNewPassword)) {
//			throw new Exception("New Password and Confirm password should be same");
//		}
		
		
	}

	@Override
	public List<Employee> getEmployeeByFirstName(String firstName) {
//	

		return employeeRepository.findAll();
	}

	
	@Override
	public void updateEmployee(String employeeID, EmployeeDto employeedto) {
		Employee employee = employeeRepository.findById(employeeID).
		orElseThrow(()->new EmployeeNotFoundException("employee doesn't exit by given id"));
	//employee1 = dataTransfer.addDatatoEntity(employeeDto);
	
		employee.setDateOfJoining(employeedto.getDateOfJoining() != null ? employeedto.getDateOfJoining() : employee.getDateOfJoining());
        employee.setEmailID(employeedto.getEmailID() != null ? employeedto.getEmailID() : employee.getEmailID());
       
        employee.setRole(employeedto.getRole()!=null?employeedto.getRole():employee.getRole());
        employee.setStatus((employeedto.getStatus()!=null?employeedto.getStatus():employee.getStatus()));
        employee.setPassword(
              employeedto.getPassword() != null ? employeedto.getPassword() : employee.getPassword());
        Person person=new Person();
        person.setContactNumber(employeedto.getPersonDto().getContactNumber()!=null?
        		employeedto.getPersonDto().getContactNumber():employee.getPerson().getContactNumber());
        person.setDateOfBirth(employeedto.getPersonDto().getDateOfBirth()!=null?
        		employeedto.getPersonDto().getDateOfBirth():employee.getPerson().getDateOfBirth());
        person.setFirstName(employeedto.getPersonDto().getFirstName()!=null?
        		employeedto.getPersonDto().getFirstName():employee.getPerson().getFirstName());
        person.setLastName(employeedto.getPersonDto().getLastName()!=null?
        		employeedto.getPersonDto().getLastName():employee.getPerson().getLastName());
        person.setGender(employeedto.getPersonDto().getGender()!=null?
        		employeedto.getPersonDto().getGender():employee.getPerson().getGender());
        
        person.setTitle(employeedto.getPersonDto().getTitle()!=null?
        		employeedto.getPersonDto().getTitle():employee.getPerson().getTitle());

        employee.setPerson(person);
      


			employeeRepository.save(employee);
		

	}

	public String loginEmployee(String emailID, String password) throws Exception {

		Optional<Employee> employee = employeeRepository.findByEmailID(emailID);

		Employee employee1 = employee.get();

		if (employee1.getStatus().equals(Status.ACTIVE)) {

			if (employee1.getPassword().equals(password)) {
				
				return ("employee Logined Successfully ");
			} else {
				  throw new Exception("Wrong Credentials ");

			}
		} else if (employee1.getStatus().equals(Status.INACTIVE)) {
			 throw new Exception("Wrong Credentials ");
                
		} else {
			 throw new Exception("Wrong Credentials ");
			 // return ("can't login, you are blocked");
		}
		

	}

	@Override
	public boolean forgotPassword(String emailID) {
		Optional<Employee> employee = employeeRepository.findByEmailID(emailID);
		Employee employee1 = employee.orElseThrow(
				() -> new EmployeeNotFoundException("Employee user not found with the emailID : " + emailID));
		String otp = randomPassword.generateRandomPassword();
		employee1.setPassword(otp);
		emailSenderService.sendOtpOverEmail(employee1.getPerson().getFirstName(), emailID, otp);
		
		return true;
	}

}