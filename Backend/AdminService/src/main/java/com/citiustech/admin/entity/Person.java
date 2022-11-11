package com.citiustech.admin.entity;


import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Person")
public class Person {
	@Id
	@GenericGenerator(name = "custom_personId", strategy = "com.citiustech.admin.utility.CustomIdGenerator")
	@GeneratedValue(generator = "custom_personId")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String personId;
	private Title title;
	private String firstName;
	private String lastName;
	private Gender gender;
	private LocalDate dateOfBirth;
	private String contactNumber;

}
