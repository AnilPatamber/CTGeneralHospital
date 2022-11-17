package com.citiustech.patient.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Allergies {
	
	@Id
	private Integer allergyId;

	private String allergyType;
	
	private String allergyName;
	
	@Column(name = "allergy_clinical_info")
	private String info;

}
