package com.incedo.ping.user_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incedo.ping.user_service.exception.ResourceNotFoundException;
import com.incedo.ping.user_service.model.NotificationStatus;
import com.incedo.ping.user_service.repository.NotificationStatusRepository;

@Service
public class NotificationStatusService {

	@Autowired
	private NotificationStatusRepository notificationStatusRepository;
	
	public NotificationStatus getOne(int id) throws ResourceNotFoundException {
		Optional<NotificationStatus> notificationStatusFound = notificationStatusRepository.findById(id);
		if (notificationStatusFound.isEmpty()) {
			throw new ResourceNotFoundException("Status not found");
		}
		return notificationStatusFound.get();
	}
	
}
