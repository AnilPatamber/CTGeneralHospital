package com.citiustech.inbox.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

	@Id
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
