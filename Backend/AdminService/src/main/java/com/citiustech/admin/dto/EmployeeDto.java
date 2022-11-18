package com.citiustech.admin.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.citiustech.admin.entity.Role;
import com.citiustech.admin.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

	
	private PersonDto personDto;
	
	@Email(message = "Please enter valid email address, e.g. exampleusername@xyzdomain.com")
	@NotNull(message = "Email cannot be empty")
	private String emailID;
	
	@NotNull
	private LocalDate dateOfJoining;
	
	@NotNull
	private Status status;
	
	@NotNull
	private Role role;
	
}
