package com.citiustech.patient.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient_details")
public class PatientDetails {

	@Id
	@GenericGenerator(name = "custom_patId", strategy = "com.citiustech.patient.utility.CustomIdGenerator")
	@GeneratedValue(generator = "custom_patId")
	private String patientDetailsId;

	private String race;

	private String ethnicity;

	private String language;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "emergencyContactDetailsId")
	private EmergencyContactDetails emergencyContactDetails;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "addressId", nullable = false)
	private Address address;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "patient_allergy", joinColumns = @JoinColumn(name = "patientDetailsId"), inverseJoinColumns = @JoinColumn(name = "allergyId"))
	private List<Allergy> allergyList;

	private String patientId;

}
