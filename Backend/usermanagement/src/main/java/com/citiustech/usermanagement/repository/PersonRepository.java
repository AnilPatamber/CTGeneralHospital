package com.citiustech.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citiustech.usermanagement.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, String>{
	
	

}
