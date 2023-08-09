package com.incedo.ping.admin_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incedo.ping.admin_service.exception.ResourceNotFoundException;
import com.incedo.ping.admin_service.model.NotificationTemplate;
import com.incedo.ping.admin_service.repository.NotificationTemplateRepository;

@Service
public class NotificationTemplateService {

	@Autowired
	private NotificationTemplateRepository notificationTemplateRepository;
	
	public List<NotificationTemplate> getAll() {
		return notificationTemplateRepository.findAll();
	}
	
	public NotificationTemplate getOne(int id) throws ResourceNotFoundException {
		Optional<NotificationTemplate> notificationTemplateFound = notificationTemplateRepository.findById(id);
		if (notificationTemplateFound.isEmpty()) {
			throw new ResourceNotFoundException("Template not found");
		}
		return notificationTemplateFound.get();
	}
	
	public NotificationTemplate insert(NotificationTemplate notificationTemplate) {
		return notificationTemplateRepository.save(notificationTemplate);
	}
	
	public NotificationTemplate update(int id, NotificationTemplate notificationTemplate) throws ResourceNotFoundException {
		Optional<NotificationTemplate> notificationTemplateFound = notificationTemplateRepository.findById(id);
		if (notificationTemplateFound.isEmpty()) {
			throw new ResourceNotFoundException("Template not found");
		}
		notificationTemplate.setId(id);
		
		return notificationTemplateRepository.save(notificationTemplate);
	}
	
	public void delete(int id) throws ResourceNotFoundException {
		Optional<NotificationTemplate> notificationTemplateFound = notificationTemplateRepository.findById(id);
		if (notificationTemplateFound.isEmpty()) {
			throw new ResourceNotFoundException("Notification template not found");
		}
		
		notificationTemplateRepository.deleteById(id);
	}
	
}
