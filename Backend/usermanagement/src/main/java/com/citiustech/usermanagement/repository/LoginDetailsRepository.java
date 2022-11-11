package com.citiustech.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citiustech.usermanagement.entity.LoginDetails;


@Repository
public interface LoginDetailsRepository extends JpaRepository<LoginDetails, Long>{

}
