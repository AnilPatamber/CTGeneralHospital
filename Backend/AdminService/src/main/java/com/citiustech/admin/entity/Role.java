package com.citiustech.admin.entity;

public enum Role {
	ADMIN("ADMIN"), NURSE("NURSE"), PHYSICIAN("PHYSICIAN"), PATIENT("PATIENT");

	private String name;

	public String getName() {
		return name;
	}

	Role(String name){
		this.name = name;
	}

}
