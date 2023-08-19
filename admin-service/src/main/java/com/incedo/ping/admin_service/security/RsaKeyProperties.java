package com.incedo.ping.admin_service.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rsa")
@Getter
@Setter
public class RsaKeyProperties {

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

}
