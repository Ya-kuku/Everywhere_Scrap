package com.example.backend.security;

public class JwtAuthentication {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthentication(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

	@Override
	public String toString() {
		return "JwtAuthentication [accessToken=" + accessToken + ", tokenType=" + tokenType + "]";
	}
    
}
