package com.citiustech.inbox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citiustech.inbox.entity.Message;

@Repository
public interface MessageRepoistory extends JpaRepository<Message, Integer>{
	

	public List<Message> findByReceiverEmployeeId(String id);
	
	public List<Message> findBySenderEmployeeId(String id);
}
