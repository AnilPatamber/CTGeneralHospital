package com.citiustech.admin.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Employee")
public class Employee {

	@Id
	@GenericGenerator(name = "custom_personId", strategy = "com.citiustech.admin.utility.CustomIdGenerator")
	@GeneratedValue(generator = "custom_personId")
	private String employeeId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "personId")
	private Person person;
	
	private String emailID;

	@JsonIgnore
	private String password;
	
	private LocalDate dateOfJoining;
	
	private Status status;
	
	private Role role;
	
	private boolean isDefault;
	
	private boolean isDeleted;

}
