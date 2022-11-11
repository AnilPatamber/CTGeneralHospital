package com.citiustech.patient.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Allergy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer allergyId;

	private String allergyType;

	private Boolean isFatal;

}
