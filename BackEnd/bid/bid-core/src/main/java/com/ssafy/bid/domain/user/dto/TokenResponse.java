package com.ssafy.bid.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponse {
	private String accessToken;
	private String refreshToken;

	@Builder
	public TokenResponse(
		String accessToken,
		String refreshToken
	) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
