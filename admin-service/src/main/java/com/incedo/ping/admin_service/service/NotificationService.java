package com.incedo.ping.admin_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incedo.ping.admin_service.exception.ResourceNotFoundException;
import com.incedo.ping.admin_service.model.GroupNotification;
import com.incedo.ping.admin_service.model.Notification;
import com.incedo.ping.admin_service.model.NotificationCategory;
import com.incedo.ping.admin_service.model.NotificationStatus;
import com.incedo.ping.admin_service.model.User;
import com.incedo.ping.admin_service.repository.NotificationCategoryRepository;
import com.incedo.ping.admin_service.repository.NotificationRepository;
import com.incedo.ping.admin_service.repository.NotificationStatusRepository;
import com.incedo.ping.admin_service.repository.UserRepository;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private NotificationStatusRepository notificationStatusRepository;

	@Autowired
	private NotificationCategoryRepository notificationCategoryRepository;

	@Autowired
	private UserRepository userRepository;
	
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
	
	public Notification insert(Notification notification) throws ResourceNotFoundException {
		Optional<NotificationStatus> notificationStatusFound = notificationStatusRepository.findById(notification.getStatus().getId());
		if (notificationStatusFound.isEmpty()) {
			throw new ResourceNotFoundException("Status not found");
		}
		notification.setStatus(notificationStatusFound.get());		
		
		Optional<NotificationCategory> notificationCategoryFound = notificationCategoryRepository.findById(notification.getCategory().getId());
		if (notificationCategoryFound.isEmpty()) {
			throw new ResourceNotFoundException("Category not found");
		}
		notification.setCategory(notificationCategoryFound.get());
		
		Optional<User> userFound = userRepository.findById(notification.getRecipient().getId());
		if (userFound.isEmpty()) {
			throw new ResourceNotFoundException("User not found");
		}
		notification.setRecipient(userFound.get());
		
		return notificationRepository.save(notification);
	}
	
	public List<Notification> multipleInsert(GroupNotification groupNotification) throws ResourceNotFoundException {
		List<Notification> notifications = new ArrayList<>();
		
		for (Integer userId : groupNotification.getUserIds()) {
			Notification notification = groupNotification.getNotification();
			
			Optional<NotificationStatus> notificationStatusFound = notificationStatusRepository.findById(notification.getStatus().getId());
			if (notificationStatusFound.isEmpty()) {
				throw new ResourceNotFoundException("Status with id " + notification.getStatus().getId() + " not found");
			}
			notification.setStatus(notificationStatusFound.get());		
			
			Optional<NotificationCategory> notificationCategoryFound = notificationCategoryRepository.findById(notification.getCategory().getId());
			if (notificationCategoryFound.isEmpty()) {
				throw new ResourceNotFoundException("Category with id " + notification.getCategory().getId() + " not found");
			}
			notification.setCategory(notificationCategoryFound.get());
			
			Optional<User> userFound = userRepository.findById(userId);
			if (userFound.isEmpty()) {
				throw new ResourceNotFoundException("User with id " + userId + " not found");
			}
			notification.setRecipient(userFound.get());
			
			notifications.add(notification);
		}
		return notificationRepository.saveAll(notifications);
	}
	
	public Notification update(int id, Notification notification) throws ResourceNotFoundException {
		Optional<Notification> notificationFound = notificationRepository.findById(id);
		if (notificationFound.isEmpty()) {
			throw new ResourceNotFoundException("Notification not found");
		}
		notification.setId(notificationFound.get().getId());
		
		Optional<NotificationStatus> notificationStatusFound = notificationStatusRepository.findById(notification.getStatus().getId());
		if (notificationStatusFound.isEmpty()) {
			throw new ResourceNotFoundException("Status not found");
		}
		notification.setStatus(notificationStatusFound.get());
		
		Optional<NotificationCategory> notificationCategoryFound = notificationCategoryRepository.findById(notification.getCategory().getId());
		if (notificationCategoryFound.isEmpty()) {
			throw new ResourceNotFoundException("Category not found");
		}
		notification.setCategory(notificationCategoryFound.get());
		
		Optional<User> userFound = userRepository.findById(notification.getRecipient().getId());
		if (userFound.isEmpty()) {
			throw new ResourceNotFoundException("User not found");
		}
		notification.setRecipient(userFound.get());
		
		return notificationRepository.save(notification);
	}
	
	public void delete(int id) throws ResourceNotFoundException {
		Optional<Notification> notificationFound = notificationRepository.findById(id);
		if (notificationFound.isEmpty()) {
			throw new ResourceNotFoundException("Notification not found");
		}
		
		notificationRepository.deleteById(id);
	}
	
}
