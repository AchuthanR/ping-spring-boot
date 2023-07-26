package com.incedo.ping.admin_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.ping.admin_service.model.NotificationCategory;

public interface NotificationCategoryRepository extends JpaRepository<NotificationCategory, Integer> {

}
