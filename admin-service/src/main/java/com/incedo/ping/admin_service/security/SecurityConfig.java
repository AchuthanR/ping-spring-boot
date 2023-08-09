package com.incedo.ping.admin_service.security;

import java.security.interfaces.RSAPublicKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private RsaKeyProperties rsaKeyProperties;
	
	public SecurityConfig(RsaKeyProperties rsaKeyProperties) {
		this.rsaKeyProperties = rsaKeyProperties;
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
            .cors(Customizer.withDefaults())
			.csrf(csrf -> csrf.disable())
			.authorizeRequests(auth -> auth
					.antMatchers("/swagger-ui/**", "/v2/api-docs/**", "/swagger-ui.html").permitAll()
					.anyRequest().hasAuthority("ADMIN"))
			.oauth2ResourceServer().jwt()
			.jwtAuthenticationConverter(jwtAuthenticationConverter());		
	}
	
	private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new CustomJwtGrantedAuthoritiesConverter());
        return converter;
    }
	
	@Bean
    public JwtDecoder jwtDecoder() {
        RSAPublicKey publicKey = rsaKeyProperties.publicKey();
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }
	
}
