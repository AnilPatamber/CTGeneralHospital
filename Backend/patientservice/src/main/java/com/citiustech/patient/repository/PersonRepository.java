package com.citiustech.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citiustech.patient.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

}
