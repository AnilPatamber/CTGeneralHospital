package com.citiustech.inbox.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citiustech.inbox.dto.ReplyMessageDto;
import com.citiustech.inbox.dto.SendMessageDto;
import com.citiustech.inbox.entity.Message;
import com.citiustech.inbox.service.MessageService;

@RestController
@CrossOrigin("*")
@RequestMapping("inbox/message")
public class NotesController {

	@Autowired
	private MessageService messageService;

	@GetMapping("/")
	public String welcome() {
		return "Welcome to patient inbox controller";
	}

	@PostMapping("/send")
	public ResponseEntity<String> sendNotes(@RequestBody SendMessageDto message) {
		return new ResponseEntity<String>(messageService.sendNote(message), HttpStatus.OK);
	}

	@PutMapping("/reply")
	public ResponseEntity<String> sendNotes(@RequestBody ReplyMessageDto message) throws Exception {
		return new ResponseEntity<String>(messageService.replyNote(message), HttpStatus.OK);
	}

	@GetMapping("/received/{empId}")
	public ResponseEntity<List<Message>> receivedNotes(@PathVariable String empId) {
		return new ResponseEntity<List<Message>>(messageService.recievedNotes(empId), HttpStatus.OK);
	}

	@GetMapping("/sent/{empId}")
	public ResponseEntity<List<Message>> sentNotes(@PathVariable String empId) {
		return new ResponseEntity<List<Message>>(messageService.sentNotes(empId), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public String deleteNotes(@RequestBody Message message) {
		messageService.deleteNotes(message);
		return "deleted";
	}

	@DeleteMapping("/delete/{id}")
	public String deleteNotes(@PathVariable Integer id) {
		messageService.deleteNotesById(id);
		return "deleted";
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Message>> getAllNotes(){
		return new ResponseEntity<List<Message>>(messageService.getAllNotes(),HttpStatus.OK);
	}

}
