package com.citiustech.admin.service;

import java.util.List;

import com.citiustech.admin.dto.ChangePasswordDto;
import com.citiustech.admin.dto.EmployeeDto;
import com.citiustech.admin.entity.Employee;
import com.citiustech.admin.exception.EmployeeAlreadyExistException;

public interface EmployeeService {
	
	public List<Employee> getAllEmployees();

	public String addEmployee(EmployeeDto employeeDto) throws EmployeeAlreadyExistException;

	public Employee getEmployeeByID(String employeeID) throws Exception;

	public void updateEmployeeStatus(String employeeID, String newStatus) throws Exception;

	public void updateEmployeePassword(String employeeID, ChangePasswordDto changePasswordDto) throws Exception;

	public void updateEmployee(String employeeID, EmployeeDto employeeDto) throws Exception;

	public List<Employee> getEmployeeByFirstName(String firstName);

	public boolean forgotPassword(String emailID) throws Exception;

	//public String loginEmployee(String emailID, String password) throws Exception;
	
	public List<String> getPhysicianNames();
	
	public Employee getEmployeeByEmailID(String emailId) throws Exception;
	
	public void deleteEmployee(String emailId);

}
