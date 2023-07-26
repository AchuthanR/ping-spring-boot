package com.incedo.ping.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.ping.user_service.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
