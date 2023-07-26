package com.incedo.ping.user_service.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String title;
	private String content;
	private LocalDateTime timestamp;
	@ManyToOne
	private NotificationStatus status;
	@ManyToOne
	private User recipient;
	@ManyToOne
	private NotificationCategory category;
	private LocalDateTime readTimestamp;
	
}
