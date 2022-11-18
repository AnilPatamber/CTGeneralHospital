package com.citiustech.inbox.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citiustech.inbox.dto.ReplyMessageDto;
import com.citiustech.inbox.dto.SendMessageDto;
import com.citiustech.inbox.entity.Message;
import com.citiustech.inbox.repository.MessageRepoistory;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepoistory messageRepoistory;

	@Override
	public String sendNote(SendMessageDto message) {
		
		Message msg = new Message();
		
		BeanUtils.copyProperties(message, msg);
		messageRepoistory.save(msg);

		return "Sent";

	}

	@Override
	public String replyNote(ReplyMessageDto message) throws Exception {

		Message existingMessage = messageRepoistory.findById(message.getMessageId())
				.orElseThrow(() -> new Exception("message not found with id :" + message.getMessageId()));

		existingMessage.setReplyMessage(message.getReplyMessage());

		messageRepoistory.save(existingMessage);

		return "Replied";
	}

	@Override
	public List<Message> recievedNotes(String id) {

		return messageRepoistory.findByReceiverEmployeeId(id);
	}

	@Override
	public List<Message> sentNotes(String id) {
		
		return messageRepoistory.findBySenderEmployeeId(id);
	}

	@Override
	public void deleteNotes(Message message) {
		messageRepoistory.delete(message);
	}

	@Override
	public void deleteNotesById(Integer id) {
		messageRepoistory.deleteById(id);
	}

	@Override
	public List<Message> getAllNotes() {
		return messageRepoistory.findAll();
	}

}
