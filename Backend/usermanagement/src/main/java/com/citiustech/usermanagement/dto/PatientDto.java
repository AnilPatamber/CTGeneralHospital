package com.citiustech.usermanagement.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PatientDto {

	@Email(message = "Please enter valid email address, e.g. exampleusername@xyzdomain.com")
	@NotNull(message = "Email cannot be empty")
	private String email;

	@Pattern(regexp = "^(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password is weak. Please re-enter the password with a minimum of one Uppercase letter, one lowercase letter, one number and minimum length of 8 characters\"")
	private String password;

	@NotEmpty(message = "Kindly select the title")
	@Pattern(regexp = "Mr|Ms|Mrs|Dr", message = "Kindly select the title", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String title;
	
	@NotEmpty(message = "Kindly select the title")
	@Pattern(regexp = "M|F|O", message = "Kindly select the gender", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String gender;

	@NotBlank(message = "Firstname cannot be empty")
	@Size(min = 2, message = "Please don’t use abbreviations, Firstname cannot be less than 2 characters")
	private String firstName;

	@NotBlank(message = "Lastname cannot be empty")
	@Size(min = 2, message = "Please don’t use abbreviations, Lastname cannot be less than 2 characters")
	private String lastName;

	@PastOrPresent(message = "Please enter a valid date of birth")
	private LocalDate dateOfBirth;

	@Pattern(regexp = "(^[+][0-9]+$)|(^$)", message = "Please enter a valid contact number")
	private String contactNumber;

	// private Role role=Role.PATIENT;

}
