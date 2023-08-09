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
import com.incedo.ping.admin_service.model.NotificationTemplate;
import com.incedo.ping.admin_service.service.NotificationTemplateService;

@RestController
@RequestMapping("/notificationTemplate")
public class NotificationTemplateController {
	
	@Autowired
	private NotificationTemplateService notificationTemplateService;

	@GetMapping
	public List<NotificationTemplate> getAllNotificationTemplate() {
		return notificationTemplateService.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getNotificationTemplate(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notificationTemplateService.getOne(id));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping
	public NotificationTemplate postNotificationTemplate(@RequestBody NotificationTemplate notificationTemplate) {
		return notificationTemplateService.insert(notificationTemplate);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> putNotificationTemplate(@PathVariable int id, @RequestBody NotificationTemplate notificationTemplate) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notificationTemplateService.update(id, notificationTemplate));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNotificationTemplate(@PathVariable int id) {
		try {
			notificationTemplateService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
