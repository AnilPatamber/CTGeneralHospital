package com.citiustech.admin.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.citiustech.admin.entity.Gender;
import com.citiustech.admin.entity.Title;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

	// private int personID;
	@NotEmpty(message = "Kindly select the title")
	@Pattern(regexp = "Mr|Ms|Mrs|Dr", message = "Kindly select the title", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String title;

	@NotBlank(message = "Firstname cannot be empty")
	@Size(min = 2, message = "Please don’t use abbreviations, Firstname cannot be less than 2 characters")
	private String firstName;

	@NotBlank(message = "Lastname cannot be empty")
	@Size(min = 2, message = "Please don’t use abbreviations, Lastname cannot be less than 2 characters")
	private String lastName;

	@NotEmpty(message = "Kindly select the title")
	@Pattern(regexp = "M|F|O", message = "Kindly select the gender", flags = Pattern.Flag.CASE_INSENSITIVE)
	private Gender gender;

	@PastOrPresent(message = "Please enter a valid date of birth")
	private LocalDate dateOfBirth;

	@Pattern(regexp = "(^[+][0-9]+$)|(^$)", message = "Please enter a valid contact number")
	private String contactNumber;

}
