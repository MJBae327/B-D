package com.ssafy.bid.domain.user.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
	private final String secret;
	private final long expirationTime;

	public JwtProperties(String secret, long expirationTime) {
		this.secret = secret;
		this.expirationTime = expirationTime;
	}
}
