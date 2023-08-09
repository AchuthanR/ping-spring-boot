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
import com.incedo.ping.admin_service.model.GroupNotification;
import com.incedo.ping.admin_service.model.Notification;
import com.incedo.ping.admin_service.service.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;

	@GetMapping
	public List<Notification> getAllNotification() {
		return notificationService.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getNotification(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notificationService.getOne(id));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> postNotification(@RequestBody Notification notification) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notificationService.insert(notification));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/group")
	public ResponseEntity<?> postGroupNotification(@RequestBody GroupNotification groupNotification) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notificationService.multipleInsert(groupNotification));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/fromTemplate/{id}")
	public ResponseEntity<?> postNotificationFromTemplate(@PathVariable int id, @RequestBody Notification notification) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notificationService.insertFromTemplate(id, notification));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/group/fromTemplate/{id}")
	public ResponseEntity<?> postGroupNotificationFromTemplate(@PathVariable int id, @RequestBody GroupNotification groupNotification) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notificationService.multipleInsertFromTemplate(id, groupNotification));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> putNotification(@PathVariable int id, @RequestBody Notification notification) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notificationService.update(id, notification));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNotification(@PathVariable int id) {
		try {
			notificationService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
