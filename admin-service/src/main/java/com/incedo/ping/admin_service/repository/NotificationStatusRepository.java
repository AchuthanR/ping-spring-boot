package com.incedo.ping.admin_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.ping.admin_service.model.NotificationStatus;

public interface NotificationStatusRepository extends JpaRepository<NotificationStatus, Integer> {

}
