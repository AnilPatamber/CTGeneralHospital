package com.citiustech.admin.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {

	private String oldPassword;
	private String newPassword;
	private String confirmPassword;

}