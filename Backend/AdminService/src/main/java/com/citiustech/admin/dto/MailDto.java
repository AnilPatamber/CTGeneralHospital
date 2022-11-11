package com.citiustech.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
	
	private String name;
	private String to;
	private String from;
	private String subject;
	private String mailBody;

}
