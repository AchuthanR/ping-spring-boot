package com.incedo.ping.auth_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.incedo.ping.auth_service.exception.ResourceAlreadyExistsException;
import com.incedo.ping.auth_service.exception.ResourceNotFoundException;
import com.incedo.ping.auth_service.model.User;
import com.incedo.ping.auth_service.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findUserByUsername(username);
	}
	
	public List<User> getAll(boolean includeInactiveUsers) {
		if (includeInactiveUsers) {
			return userRepository.findAll();
		}
		else {
			return userRepository.findAllActiveUsers();
		}
	}
	
	public User getOne(String username) {
		return userRepository.findUserByUsernameAndIsActive(username, true);
	}
	
	public User insert(User user) throws ResourceAlreadyExistsException {
		User userFound = userRepository.findUserByUsername(user.getUsername());
		if (userFound != null) {
			throw new ResourceAlreadyExistsException("Username is already in use");
		}
		
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		user.setCreatedAt(LocalDateTime.now());
		user.setModifiedAt(LocalDateTime.now());
		user.setRole("USER");
		user.setActive(true);
		User saved = userRepository.save(user);
		LOG.info("New user with username {} has been created", user.getUsername());
		return saved;
	}
	
	public User update(String username, User user) throws ResourceNotFoundException, ResourceAlreadyExistsException {
		User existingUser = userRepository.findUserByUsername(username);
		
		if (existingUser == null) {
			throw new ResourceNotFoundException("User with given username does not exist");
		}
		
		if (username != null && !username.equals(user.getUsername())) {
			User userFound = userRepository.findUserByUsername(user.getUsername());
			if (userFound != null) {
				throw new ResourceAlreadyExistsException("Username is already in use");
			}
		}

		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		user.setModifiedAt(LocalDateTime.now());
		user.setRole("USER");
		user.setActive(true);
		User saved = userRepository.save(user);
		LOG.info("Details of user with username {} has been updated", user.getUsername());
		return saved;
	}
	
	public void delete(String username) throws ResourceNotFoundException {
		User user = userRepository.findUserByUsername(username);
		
		if (user == null) {
			throw new ResourceNotFoundException("User with given username does not exist");
		}
		
		user.setModifiedAt(LocalDateTime.now());
		user.setActive(false);
		userRepository.save(user);
		LOG.info("User with username {} has been disabled", user.getUsername());
	}

}
