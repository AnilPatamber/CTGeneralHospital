package com.citiustech.admin.entity;

public enum Title {
 MR("MR"), MRS("MRS"), MISS("MISS");
    
    private String title;

	Title(String title) {
		this.setTitle(title);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
    
  


}
