package com.incedo.ping.auth_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.incedo.ping.auth_service.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.username=?1")
	User findUserByUsername(String username);
	
	@Query("select u from User u where u.username=?1 and u.isActive=?2")
	User findUserByUsernameAndIsActive(String username, boolean isActive);
	
	@Query("select u from User u where u.isActive=true")
	List<User> findAllActiveUsers();
	
}
