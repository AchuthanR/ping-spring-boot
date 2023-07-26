package com.incedo.ping.admin_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.ping.admin_service.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
