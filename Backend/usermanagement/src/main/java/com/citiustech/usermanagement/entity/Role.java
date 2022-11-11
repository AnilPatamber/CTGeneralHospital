package com.citiustech.usermanagement.entity;

import java.io.Serializable;

public enum Role implements Serializable {

	ADMIN("ADMIN"), NURSE("NURSE"), PHYSICIAN("PHYSICIAN"), PATIENT("PATIENT");

	private String name;

	public String getName() {
		return name;
	}

	Role(String name){
		this.name = name;
	}

}
