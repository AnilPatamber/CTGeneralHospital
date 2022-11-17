package com.citiustech.patient.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Allergy extends Allergies {

	/*
	 * @Id private Integer allergyId;
	 * 
	 * private String allergyType;
	 * 
	 * private String allergyName;
	 * 
	 * @Column(name = "allergy_clinical_info") private String info;
	 */

	private Boolean isFatal;

}
