package com.citiustech.usermanagement.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patient")
public class Patient {

	@Id
	@GenericGenerator(name = "custom_patientId", strategy = "com.citiustech.usermanagement.utililty.CustomIdGenerator")
	@GeneratedValue(generator = "custom_patientId")
	private String patientId;

	private String email;
	
	@JsonIgnore
	private String password;
	
	private Status status;
	
	private Role role;
	
	private LocalDateTime defaultExpiryTime;

	@Column(name = "registered_date", nullable = false, updatable = false)
	@CreationTimestamp
	private Timestamp createdDate;

	@Column(name = "updated_date")
	@UpdateTimestamp
	private Timestamp updatedDate;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "personId")
	private Person person;
	

}
