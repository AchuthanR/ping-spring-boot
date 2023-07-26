package com.incedo.ping.admin_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incedo.ping.admin_service.exception.ResourceNotFoundException;
import com.incedo.ping.admin_service.model.NotificationStatus;
import com.incedo.ping.admin_service.repository.NotificationStatusRepository;

@Service
public class NotificationStatusService {

	@Autowired
	private NotificationStatusRepository notificationStatusRepository;
	
	public List<NotificationStatus> getAll() {
		return notificationStatusRepository.findAll();
	}
	
	public NotificationStatus getOne(int id) throws ResourceNotFoundException {
		Optional<NotificationStatus> notificationStatusFound = notificationStatusRepository.findById(id);
		if (notificationStatusFound.isEmpty()) {
			throw new ResourceNotFoundException("Status not found");
		}
		return notificationStatusFound.get();
	}
	
	public NotificationStatus insert(NotificationStatus notificationStatus) {
		return notificationStatusRepository.save(notificationStatus);
	}
	
	public NotificationStatus update(int id, NotificationStatus notificationStatus) throws ResourceNotFoundException {
		Optional<NotificationStatus> notificationStatusFound = notificationStatusRepository.findById(id);
		if (notificationStatusFound.isEmpty()) {
			throw new ResourceNotFoundException("Status not found");
		}
		notificationStatus.setId(id);
		
		return notificationStatusRepository.save(notificationStatus);
	}
	
	public void delete(int id) throws ResourceNotFoundException {
		Optional<NotificationStatus> notificationStatusFound = notificationStatusRepository.findById(id);
		if (notificationStatusFound.isEmpty()) {
			throw new ResourceNotFoundException("Status not found");
		}
		
		notificationStatusRepository.deleteById(id);
	}
	
}
