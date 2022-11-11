package com.citiustech.admin.dto;

import java.time.LocalDate;

import com.citiustech.admin.entity.Role;
import com.citiustech.admin.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
	//private int employeeID;

	private PersonDto personDto;
	private String emailID;
	private String password;
	private LocalDate dateOfJoining;
	private Status status;
	private Role role;
	
}
