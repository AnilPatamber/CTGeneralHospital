package com.citiustech.inbox.dto;

import com.citiustech.inbox.entity.Urgency;

import lombok.Data;

@Data
public class SendMessageDto {
		
	private String sendMessage;
	
	private String senderEmployeeId;
	
	private Urgency urgency;
	
	private String receiverEmployeeId;

}
