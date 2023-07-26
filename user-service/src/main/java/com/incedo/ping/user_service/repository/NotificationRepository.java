package com.incedo.ping.user_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.incedo.ping.user_service.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	@Query("select n from Notification n where n.recipient.username=?1")
	List<Notification> findAllByUserUsername(String username);
	
	@Query("select n from Notification n where n.recipient.username=?1 and n.id>?2")
	List<Notification> findAllByUserUsernameAfterNotificationId(String username, int afterNotificationId);

	@Query("select n from Notification n where n.id=?1 and n.recipient.username=?2")
	Optional<Notification> findByIdAndUserUsername(int id, String username);
	
}
