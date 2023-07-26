package com.incedo.ping.user_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incedo.ping.user_service.exception.ResourceNotFoundException;
import com.incedo.ping.user_service.model.Notification;
import com.incedo.ping.user_service.model.NotificationStatus;
import com.incedo.ping.user_service.model.NotificationStatusUpdate;
import com.incedo.ping.user_service.repository.NotificationRepository;
import com.incedo.ping.user_service.repository.NotificationStatusRepository;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private NotificationStatusRepository notificationStatusRepository;
	
	public List<Notification> getAll() {
		return notificationRepository.findAll();
	}
	
	public Notification getOne(int id) throws ResourceNotFoundException {
		Optional<Notification> notificationFound = notificationRepository.findById(id);
		if (notificationFound.isEmpty()) {
			throw new ResourceNotFoundException("Notification not found");
		}
		return notificationFound.get();
	}
	
	public void markAsRead(int id, NotificationStatusUpdate notificationStatusUpdate) throws ResourceNotFoundException {
		Optional<Notification> notificationFound = notificationRepository.findById(id);
		if (notificationFound.isEmpty()) {
			throw new ResourceNotFoundException("Notification not found");
		}
		
		Optional<NotificationStatus> notificationStatusFound = notificationStatusRepository.findById(notificationStatusUpdate.getStatus().getId());
		if (notificationStatusFound.isEmpty()) {
			throw new ResourceNotFoundException("Status not found");
		}
		
		notificationFound.get().setStatus(notificationStatusFound.get());
		if (notificationStatusUpdate.getReadTimestamp() == null) {
			notificationFound.get().setReadTimestamp(LocalDateTime.now());
		}
		
		notificationRepository.save(notificationFound.get());
	}
	
	public void delete(int id) throws ResourceNotFoundException {
		Optional<Notification> notificationFound = notificationRepository.findById(id);
		if (notificationFound.isEmpty()) {
			throw new ResourceNotFoundException("Notification not found");
		}
		
		notificationRepository.deleteById(id);
	}
	
}
