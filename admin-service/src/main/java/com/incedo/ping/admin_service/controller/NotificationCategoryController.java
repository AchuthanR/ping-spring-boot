package com.incedo.ping.admin_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incedo.ping.admin_service.exception.ResourceNotFoundException;
import com.incedo.ping.admin_service.model.NotificationCategory;
import com.incedo.ping.admin_service.service.NotificationCategoryService;

@RestController
@RequestMapping("/notificationCategory")
public class NotificationCategoryController {
	
	@Autowired
	private NotificationCategoryService notificationCategoryService;

	@GetMapping
	public List<NotificationCategory> getAllNotificationCategory() {
		return notificationCategoryService.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getNotificationCategory(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notificationCategoryService.getOne(id));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping
	public NotificationCategory postNotificationCategory(@RequestBody NotificationCategory notificationCategory) {
		return notificationCategoryService.insert(notificationCategory);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> putNotificationCategory(@PathVariable int id, @RequestBody NotificationCategory notificationCategory) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notificationCategoryService.update(id, notificationCategory));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNotificationCategory(@PathVariable int id) {
		try {
			notificationCategoryService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
