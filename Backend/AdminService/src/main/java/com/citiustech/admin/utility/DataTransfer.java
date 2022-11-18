package com.citiustech.admin.utility;

import org.springframework.stereotype.Component;

import com.citiustech.admin.dto.EmployeeDto;
import com.citiustech.admin.dto.PersonDto;
import com.citiustech.admin.entity.Employee;
import com.citiustech.admin.entity.Person;
import com.citiustech.admin.entity.Title;

@Component
public class DataTransfer {
	
	// to transfer data betn dto to entity and vice versa
		public EmployeeDto addDatatoDto(Employee employee) {
			EmployeeDto employeeDto = new EmployeeDto();
			PersonDto personDto = new PersonDto();

			//personDto.setPersonID(employee.getPerson().getPersonID());
			personDto.setFirstName(employee.getPerson().getFirstName());
			personDto.setLastName(employee.getPerson().getLastName());
			personDto.setTitle(employee.getPerson().getTitle());
			personDto.setGender(employee.getPerson().getGender());
			personDto.setDateOfBirth(employee.getPerson().getDateOfBirth());
			personDto.setContactNumber(employee.getPerson().getContactNumber());

			employeeDto.setPersonDto(personDto);
			//employeeDto.setEmployeeID(employee.getEmployeeID());
			employeeDto.setDateOfJoining(employee.getDateOfJoining());
			employeeDto.setEmailID(employee.getEmailID());
			employeeDto.setRole(employee.getRole());
			employeeDto.setStatus(employee.getStatus());

			return employeeDto;

		}

		public  Employee addDatatoEntity(EmployeeDto employeeDto) {
			Employee employee = new Employee();
			Person person = new Person();

		//	person.setPersonID(employeeDto.getPersonDto().getPersonID());
			person.setFirstName(employeeDto.getPersonDto().getFirstName());
			person.setLastName(employeeDto.getPersonDto().getLastName());
			person.setTitle(employeeDto.getPersonDto().getTitle());
			person.setGender(employeeDto.getPersonDto().getGender());
			person.setDateOfBirth(employeeDto.getPersonDto().getDateOfBirth());
			person.setContactNumber(employeeDto.getPersonDto().getContactNumber());

			employee.setPerson(person);
			//employee.setEmployeeID(employeeDto.getEmployeeID());
			employee.setDateOfJoining(employeeDto.getDateOfJoining());
			employee.setEmailID(employeeDto.getEmailID());
			employee.setRole(employeeDto.getRole());
			employee.setStatus(employeeDto.getStatus());

			return employee;

		}
		



		   }



