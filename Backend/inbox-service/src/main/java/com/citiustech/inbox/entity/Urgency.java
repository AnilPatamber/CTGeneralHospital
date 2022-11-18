package com.citiustech.inbox.entity;

public enum Urgency {

	URGENT("URGENT"), NOTURGENT("NOTURGENT");

	private String name;

	public String getName() {
		return name;
	}

	Urgency(String name) {
		this.name = name;
	}

}
