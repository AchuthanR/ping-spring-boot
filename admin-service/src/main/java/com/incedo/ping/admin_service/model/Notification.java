package com.incedo.ping.admin_service.model;

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
	
	public Notification() {
		
	}
	
	public Notification(Notification notification) {
		super();
		this.id = notification.getId();
		this.title = notification.getTitle();
		this.content = notification.getContent();
		this.timestamp = notification.getTimestamp();
		this.status = notification.getStatus();
		this.recipient = notification.getRecipient();
		this.category = notification.getCategory();
		this.readTimestamp = notification.getReadTimestamp();
	}
	
	public Notification(NotificationTemplate notificationTemplate) {
		super();
		this.title = notificationTemplate.getTitle();
		this.content = notificationTemplate.getContent();
		this.category = notificationTemplate.getCategory();
	}
	
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
