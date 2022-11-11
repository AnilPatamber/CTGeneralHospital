package com.citiustech.patient.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject {

	private Integer httpStatusCode;
	
	private String message;

	private LocalDateTime localDateTime;

}
