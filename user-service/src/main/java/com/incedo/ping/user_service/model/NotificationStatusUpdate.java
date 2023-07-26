package com.incedo.ping.user_service.model;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class NotificationStatusUpdate {

	private NotificationStatus status;
	private LocalDateTime readTimestamp;
	
}
