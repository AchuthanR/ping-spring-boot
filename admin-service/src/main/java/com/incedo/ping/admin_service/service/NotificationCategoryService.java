package com.incedo.ping.admin_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incedo.ping.admin_service.exception.ResourceNotFoundException;
import com.incedo.ping.admin_service.model.NotificationCategory;
import com.incedo.ping.admin_service.repository.NotificationCategoryRepository;

@Service
public class NotificationCategoryService {

	@Autowired
	private NotificationCategoryRepository notificationCategoryRepository;
	
	public List<NotificationCategory> getAll() {
		return notificationCategoryRepository.findAll();
	}
	
	public NotificationCategory getOne(int id) throws ResourceNotFoundException {
		Optional<NotificationCategory> notificationCategoryFound = notificationCategoryRepository.findById(id);
		if (notificationCategoryFound.isEmpty()) {
			throw new ResourceNotFoundException("Category not found");
		}
		return notificationCategoryFound.get();
	}
	
	public NotificationCategory insert(NotificationCategory notificationCategory) {
		return notificationCategoryRepository.save(notificationCategory);
	}
	
	public NotificationCategory update(int id, NotificationCategory notificationCategory) throws ResourceNotFoundException {
		Optional<NotificationCategory> notificationCategoryFound = notificationCategoryRepository.findById(id);
		if (notificationCategoryFound.isEmpty()) {
			throw new ResourceNotFoundException("Category not found");
		}
		notificationCategory.setId(id);
		
		return notificationCategoryRepository.save(notificationCategory);
	}
	
	public void delete(int id) throws ResourceNotFoundException {
		Optional<NotificationCategory> notificationCategoryFound = notificationCategoryRepository.findById(id);
		if (notificationCategoryFound.isEmpty()) {
			throw new ResourceNotFoundException("Notification not found");
		}
		
		notificationCategoryRepository.deleteById(id);
	}
	
}
