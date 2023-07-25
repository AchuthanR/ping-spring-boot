package com.incedo.ping.auth_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incedo.ping.auth_service.dto.LoginResponseDto;
import com.incedo.ping.auth_service.exception.ResourceAlreadyExistsException;
import com.incedo.ping.auth_service.model.LoginRequest;
import com.incedo.ping.auth_service.model.User;
import com.incedo.ping.auth_service.service.TokenService;
import com.incedo.ping.auth_service.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final TokenService tokenService;
    
    private final AuthenticationManager authenticationManager;
    
    @Autowired
	private UserService userService;

    public UserController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginWithUsernameAndPassword(@RequestBody LoginRequest userLogin) {
    	User user = userService.loadUserByUsername(userLogin.username());
    	
    	if (user == null) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist");
    	}
    	
    	if (!BCrypt.checkpw(userLogin.password(), user.getPassword())) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password is wrong");
    	}
    	
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
        String token = tokenService.generateToken(authentication);
        LOG.info("Token has been generated for username={}", userLogin.username());
        
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setUsername(user.getUsername());
        loginResponseDto.setRole(user.getRole());
        loginResponseDto.setAccessToken(token);
        
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerWithUsernameAndPassword(@RequestBody User user) {
    	try {
			userService.insert(user);
		} catch (ResourceAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
    	
    	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String token = tokenService.generateToken(authentication);
        LOG.info("Token has been generated for username={}", user.getUsername());
        
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setUsername(user.getUsername());
        loginResponseDto.setRole(user.getRole());
        loginResponseDto.setAccessToken(token);
        
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);
    }
	
}