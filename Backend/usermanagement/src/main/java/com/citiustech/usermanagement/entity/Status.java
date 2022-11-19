package com.citiustech.usermanagement.entity;

public enum Status {
	
	ACTIVE("ACTIVE"), INACTIVE("INACTIVE"), BLOCKED("BLOCKED");
	private String status;

	Status(String status) {
		this.setStatus(status);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
