package com.incedo.ping.user_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incedo.ping.user_service.exception.ResourceNotFoundException;
import com.incedo.ping.user_service.model.Notification;
import com.incedo.ping.user_service.model.NotificationStatusUpdate;
import com.incedo.ping.user_service.service.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;

	@GetMapping
	public List<Notification> getAllNotification(@AuthenticationPrincipal Jwt jwt) {
		String username = jwt.getSubject();
		return notificationService.getAll(username);
	}
	
	@GetMapping("/after/{id}")
	public List<Notification> getAllNotification(@PathVariable int id, @AuthenticationPrincipal Jwt jwt) {
		String username = jwt.getSubject();
		return notificationService.getAllAfterNotificationId(username, id);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getNotification(@PathVariable int id, @AuthenticationPrincipal Jwt jwt) {
		String username = jwt.getSubject();
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notificationService.getOne(id, username));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PatchMapping("/status/{id}")
	public ResponseEntity<?> markNotificationAsRead(@PathVariable int id, @RequestBody NotificationStatusUpdate notificationStatusUpdate) {
		try {
			notificationService.markAsRead(id, notificationStatusUpdate);
			return ResponseEntity.status(HttpStatus.OK).build();
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
