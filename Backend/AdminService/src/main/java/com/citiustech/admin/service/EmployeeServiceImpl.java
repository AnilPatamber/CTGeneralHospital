package com.citiustech.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.citiustech.admin.dto.ChangePasswordDto;
import com.citiustech.admin.dto.EmployeeDto;
import com.citiustech.admin.entity.Employee;
import com.citiustech.admin.entity.Person;
import com.citiustech.admin.entity.Role;
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

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public List<Employee> getAllEmployees() {

		List<Employee> list = employeeRepository.findAll();

		return list.stream().filter(e -> e.isDeleted() == false).collect(Collectors.toList());

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
		Employee employee = dataTransfer.addDatatoEntity(employeeDto);
		String defaultPassword = randomPassword.generateRandomPassword();
		employee.setPassword(bcryptEncoder.encode(defaultPassword));
		System.out.println("Default Password: " + defaultPassword);
		employee.setDefault(true);
		emailSenderService.sendDefaultPasswordEmail(employee, defaultPassword);
		Employee employee1 = employeeRepository.save(employee);
		return employee1.getPerson().getFirstName();

	}

	@Override
	public Employee getEmployeeByID(String employeeID) throws Exception {
		// Optional<Employee> employee = employeeRepository.findById(employeeID);
		// EmployeeDto emplyoeeDto = dataTransfer.addDatatoDto(employee.get());
		Employee emp = employeeRepository.findById(employeeID).orElseThrow(
				() -> new EmployeeNotFoundException("Employee user not found with the employeeID : " + employeeID));
		if (emp.isDeleted() == true)
			throw new Exception(" Employee already got deleted");
		return emp;
	}

	@Override
	public void updateEmployeeStatus(String employeeID, String newStatus) throws Exception {

		Employee employee = employeeRepository.findById(employeeID).orElseThrow(
				() -> new EmployeeNotFoundException("Employee user not found with the employeeID : " + employeeID));

		employee.setStatus(Status.valueOf(newStatus));

		if (employee.isDeleted() == false) {

			switch (newStatus) {
			case "ACTIVE":

				String defaultPassword = randomPassword.generateRandomPassword();
				employee.setPassword(bcryptEncoder.encode(defaultPassword));
				System.out.println("Default Password: " + defaultPassword);
				employee.setDefault(true);
				emailSenderService.sendDefaultPasswordEmail(employee, defaultPassword);

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

		else {
			throw new Exception(" Employee already got deleted");
		}

	}

	@Override
	public void updateEmployeePassword(String emailId, ChangePasswordDto changePasswordDto) throws Exception {

		Employee employee = employeeRepository.findByEmailID(emailId).orElseThrow(
				() -> new EmployeeNotFoundException("Employee user not found with the email : " + emailId));
		System.out.println("employee : " + employee);

		if (employee.isDeleted() == true) {
			throw new Exception(" Employee already got deleted");
		}

		String currentPassword = changePasswordDto.getOldPassword();
		String newPassword = changePasswordDto.getNewPassword();
		String confirmNewPassword = changePasswordDto.getConfirmPassword();

		if (!(bcryptEncoder.matches(currentPassword, employee.getPassword()))) {
			System.out.println("Password not matching");
			throw new Exception("Password not matching");
		} else if (!newPassword.equals(confirmNewPassword)) {
			System.out.println("New Password and Confirm password should be same");
			throw new Exception("New Password and Confirm password should be same");
		}

		employee.setPassword(bcryptEncoder.encode(newPassword));
		employee.setDefault(false);
		employeeRepository.save(employee);

	}

	@Override
	public List<Employee> getEmployeeByFirstName(String firstName) {

		List<Employee> list = employeeRepository.findAll();

		return list.stream().filter(e -> e.isDeleted() == false && e.getPerson().getFirstName().equals(firstName))
				.collect(Collectors.toList());

	}

	@Override
	public void updateEmployee(String employeeID, EmployeeDto employeedto) throws Exception {

		Employee employee = employeeRepository.findById(employeeID)
				.orElseThrow(() -> new EmployeeNotFoundException("employee doesn't exit by given id"));

		if (employee.isDeleted() == true) {
			throw new Exception(" Employee already got deleted");
		}

		employee.setDateOfJoining(
				employeedto.getDateOfJoining() != null ? employeedto.getDateOfJoining() : employee.getDateOfJoining());
		employee.setEmailID(employeedto.getEmailID() != null ? employeedto.getEmailID() : employee.getEmailID());

		employee.setRole(employeedto.getRole() != null ? employeedto.getRole() : employee.getRole());
		employee.setStatus((employeedto.getStatus() != null ? employeedto.getStatus() : employee.getStatus()));
		Person person = new Person();
		person.setContactNumber(
				employeedto.getPersonDto().getContactNumber() != null ? employeedto.getPersonDto().getContactNumber()
						: employee.getPerson().getContactNumber());
		person.setDateOfBirth(
				employeedto.getPersonDto().getDateOfBirth() != null ? employeedto.getPersonDto().getDateOfBirth()
						: employee.getPerson().getDateOfBirth());
		person.setFirstName(
				employeedto.getPersonDto().getFirstName() != null ? employeedto.getPersonDto().getFirstName()
						: employee.getPerson().getFirstName());
		person.setLastName(employeedto.getPersonDto().getLastName() != null ? employeedto.getPersonDto().getLastName()
				: employee.getPerson().getLastName());
		person.setGender(employeedto.getPersonDto().getGender() != null ? employeedto.getPersonDto().getGender()
				: employee.getPerson().getGender());

		person.setTitle(employeedto.getPersonDto().getTitle() != null ? employeedto.getPersonDto().getTitle()
				: employee.getPerson().getTitle());

		employee.setPerson(person);

		employeeRepository.save(employee);

	}

	@Override
	public boolean forgotPassword(String emailID) throws Exception {
		Employee employee = employeeRepository.findByEmailID(emailID).orElseThrow(
				() -> new EmployeeNotFoundException("Employee user not found with the emailID : " + emailID));

		if (employee.isDeleted() == true) {
			throw new Exception(" Employee already got deleted");
		}

		String defaultPassword = randomPassword.generateRandomPassword();
		employee.setPassword(bcryptEncoder.encode(defaultPassword));
		System.out.println("Default Password: " + defaultPassword);
		employee.setDefault(true);
		emailSenderService.sendDefaultPasswordEmail(employee, defaultPassword);

		return true;
	}

	@Override
	public List<String> getPhysicianNames() {

		List<String> physicianNamesList = new ArrayList<>();
		List<Employee> employeeList = getAllEmployees();
		employeeList.forEach((emp) -> {
			if (emp.getRole().equals(Role.PHYSICIAN) && emp.isDeleted() == false)
				physicianNamesList.add(emp.getPerson().getFirstName() + " " + emp.getPerson().getLastName());
		});

		return physicianNamesList;
	}

	@Override
	public Employee getEmployeeByEmailID(String emailId) throws Exception {
		Employee emp = employeeRepository.findByEmailID(emailId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not exist with email : " + emailId));

		if (emp.isDeleted() == true)
			throw new Exception(" Employee already got deleted");
		return emp;
	}

	@Override
	public void deleteEmployee(String emailId) {

		Employee employee;
		try {
			employee = getEmployeeByEmailID(emailId);
			employee.setDeleted(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Employee> getAllPhysician() {

		List<Employee> employeeList = getAllEmployees();

		return employeeList.stream().filter(e -> e.getRole().equals(Role.PHYSICIAN) && e.isDeleted() == false).collect(Collectors.toList());

		
	}

}