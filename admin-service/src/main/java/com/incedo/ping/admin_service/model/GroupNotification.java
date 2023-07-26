package com.incedo.ping.admin_service.model;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class GroupNotification {

	private Notification notification;
	
	private List<Integer> userIds;
	
}
