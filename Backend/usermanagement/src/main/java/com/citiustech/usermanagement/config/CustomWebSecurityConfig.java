package com.citiustech.usermanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.citiustech.usermanagement.service.CustomUserDetailsService;

@Configuration
//@EnableWebSecurity
public class CustomWebSecurityConfig {

	@Autowired
	private CustomUserDetailsService customUserDetialsService;

	//"/swagger-ui/**", "/v3/api-docs/**","/actuator/**", 
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		http.csrf().disable();
<<<<<<< HEAD:Backend/usermanagement/usermanagement/src/main/java/com/citiustech/usermanagement/config/CustomWebSecurityConfig.java
		
		http.cors().and().authorizeHttpRequests((auth) -> auth.antMatchers("/patient/**").permitAll()
				.antMatchers("/auth/**").authenticated());
				//.anyRequest().authenticated());
=======

		http.cors().and().authorizeHttpRequests((auth) -> auth.antMatchers("/patient/register", "/patient/login","/patient/changepassword/**","/sendemail/**").permitAll()
				);
		//.anyRequest().authenticated()
>>>>>>> 15a41976897eea1bb51c65d030f0fccb627bed34:Backend/usermanagement/src/main/java/com/citiustech/usermanagement/config/CustomWebSecurityConfig.java

		http.httpBasic().and().formLogin().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetialsService).passwordEncoder(passwordEncoder());
		return authenticationManagerBuilder.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}