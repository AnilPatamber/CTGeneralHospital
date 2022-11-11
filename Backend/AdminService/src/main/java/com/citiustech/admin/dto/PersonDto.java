package com.citiustech.admin.dto;

import java.time.LocalDate;

import com.citiustech.admin.entity.Gender;
import com.citiustech.admin.entity.Title;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
	//private int personID;
	private Title title;
	private String firstName;
	private String lastName;
	private Gender gender;
	private LocalDate dateOfBirth;
	private String contactNumber;

}
