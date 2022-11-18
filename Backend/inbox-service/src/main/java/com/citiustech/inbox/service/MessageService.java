package com.citiustech.inbox.service;

import java.util.List;

import com.citiustech.inbox.dto.ReplyMessageDto;
import com.citiustech.inbox.dto.SendMessageDto;
import com.citiustech.inbox.entity.Message;

public interface MessageService {

	public String sendNote(SendMessageDto message);

	public String replyNote(ReplyMessageDto message) throws Exception;
	
	public List<Message> recievedNotes(String id);
	
	public List<Message> sentNotes(String id);
	
	public void deleteNotes(Message message);
	
	public void deleteNotesById(Integer id);
	
	public List<Message> getAllNotes();

}
