package com.citiustech.usermanagement.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loginDetailsId")
    private Long id;

    private LocalDateTime lastLoginTime;
    
    private String email;

//    private Integer loginFailCounter;
    
    @OneToOne
	@JoinColumn(name = "personId")
    private Person person;

  
}
