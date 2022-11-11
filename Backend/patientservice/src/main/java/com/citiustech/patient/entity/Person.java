package com.citiustech.patient.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {

	@Id
	@GenericGenerator(name = "custompId", strategy = "com.citiustech.patient.utility.CustomIdGenerator")
	@GeneratedValue(generator = "custompId")
	private String personId;

	private String title;

	private String firstName;

	private String lastName;

	private Gender gender;

	private LocalDate dateOfBirth;

	private String contactNumber;

}
