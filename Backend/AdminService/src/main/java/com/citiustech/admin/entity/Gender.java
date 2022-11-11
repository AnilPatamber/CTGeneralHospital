package com.citiustech.admin.entity;

public enum Gender {
	M("M"), F("F"), O("O");

	private String gender;

	Gender(String gender) {
		this.setGender(gender);
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
