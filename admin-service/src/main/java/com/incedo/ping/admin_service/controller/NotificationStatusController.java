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
import com.incedo.ping.admin_service.model.NotificationStatus;
import com.incedo.ping.admin_service.service.NotificationStatusService;

@RestController
@RequestMapping("/notificationStatus")
public class NotificationStatusController {
	
	@Autowired
	private NotificationStatusService notificationStatusService;

	@GetMapping
	public List<NotificationStatus> getAllNotificationStatus() {
		return notificationStatusService.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getNotificationStatus(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notificationStatusService.getOne(id));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping
	public NotificationStatus postNotificationStatus(@RequestBody NotificationStatus notificationStatus) {
		return notificationStatusService.insert(notificationStatus);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> putNotificationStatus(@PathVariable int id, @RequestBody NotificationStatus notificationStatus) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notificationStatusService.update(id, notificationStatus));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNotificationStatus(@PathVariable int id) {
		try {
			notificationStatusService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
