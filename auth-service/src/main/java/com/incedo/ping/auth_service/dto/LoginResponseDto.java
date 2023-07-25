package com.incedo.ping.auth_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {

	private String username;
	
	private String role;
	
	private String accessToken;
	
}
