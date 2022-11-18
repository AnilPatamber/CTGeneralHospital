package com.citiustech.inbox.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer messageId;

	/*
	 * @ManyToOne()
	 * 
	 * @JoinColumn(name = "sender_emp_id", nullable = false) private Employee
	 * senderEmployee;
	 */
	
	private String senderEmployeeId;

	private String sendMessage;

	@CreationTimestamp
	private LocalDateTime sentTime;

	/*
	 * @ManyToOne()
	 * 
	 * @JoinColumn(name = "receiver_emp_id", nullable = false) private Employee
	 * receiverEmployee;
	 */
	
	private String receiverEmployeeId;

	private String replyMessage;

	@UpdateTimestamp
	private LocalDateTime replyTime;
	
	private Urgency urgency;

}
