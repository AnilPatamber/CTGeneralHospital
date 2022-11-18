package com.citiustech.admin.dto;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class ChangePasswordDto {

	private String oldPassword;
	@Pattern(regexp = "^(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password is weak. Please re-enter the password with a minimum of one Uppercase letter, one lowercase letter, one number and minimum length of 8 characters\"")
	private String newPassword;
	private String confirmPassword;

}