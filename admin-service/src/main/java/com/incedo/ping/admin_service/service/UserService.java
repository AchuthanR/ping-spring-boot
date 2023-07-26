package com.incedo.ping.admin_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incedo.ping.admin_service.exception.ResourceNotFoundException;
import com.incedo.ping.admin_service.model.User;
import com.incedo.ping.admin_service.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	public User getOne(int id) throws ResourceNotFoundException {
		Optional<User> userFound = userRepository.findById(id);
		if (userFound.isEmpty()) {
			throw new ResourceNotFoundException("User not found");
		}
		return userFound.get();
	}
	
}
