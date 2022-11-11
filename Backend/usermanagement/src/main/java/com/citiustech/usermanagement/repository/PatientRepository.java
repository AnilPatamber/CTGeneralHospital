package com.citiustech.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.citiustech.usermanagement.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {

	public Boolean existsByEmail(String email);

	public Optional<Patient> findByEmail(String email);

}
