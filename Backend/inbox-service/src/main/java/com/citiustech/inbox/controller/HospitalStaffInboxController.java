package com.citiustech.inbox.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("inbox/staff/")
public class HospitalStaffInboxController {
	
	
	@GetMapping("/")
	public String welcome() {
		return "Welcome to Hospital Staff inbox controller";
	}

}
