package com.citiustech.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.citiustech.admin.entity.AuthUser;
import com.citiustech.admin.entity.Employee;
import com.citiustech.admin.repository.EmployeeRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Employee existingEmployee = employeeRepository.findByEmailID(email)
				.orElseThrow(() -> new UsernameNotFoundException("employee not found for the email : " + email));

		return new AuthUser(existingEmployee);
	}

}