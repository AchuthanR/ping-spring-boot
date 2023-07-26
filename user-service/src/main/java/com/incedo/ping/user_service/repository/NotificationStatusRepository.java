
package com.incedo.ping.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.ping.user_service.model.NotificationStatus;

public interface NotificationStatusRepository extends JpaRepository<NotificationStatus, Integer> {

}
