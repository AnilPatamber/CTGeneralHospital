package com.citiustech.usermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.citiustech.usermanagement.entity.AuthUser;
import com.citiustech.usermanagement.entity.Patient;
import com.citiustech.usermanagement.repository.PatientRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private PatientRepository patientUserRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Patient existingUser = patientUserRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("user not found for the email : " + email));

		return new AuthUser(existingUser);
	}

}