package com.incedo.ping.admin_service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

	@Override
	public Collection<GrantedAuthority> convert(Jwt jwt) {
		List<String> roles = new ArrayList<>();
		roles.add(String.valueOf(jwt.getClaims().get("scope")));

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
	}

}
