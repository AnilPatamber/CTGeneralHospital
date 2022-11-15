package com.citiustech.appointment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hospital")
public class HospitalScheduleController {
	
	
	@GetMapping("/")
	public String welcome() {
		return "Welcome to Hospital Schedule controller";
	}

}
