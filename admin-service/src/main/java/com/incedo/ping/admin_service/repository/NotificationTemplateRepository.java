package com.incedo.ping.admin_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.ping.admin_service.model.NotificationTemplate;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Integer> {

}
