package fr.bastoup.DiscordOAuth.beans;

public class OAuthToken {
	private String access_token;
	private String token_type;
	private String expires_in;
	private String refresh_token;
	private String scope;
	
	public String getAccessToken() {
		return access_token;
	}
	
	public void setAccessToken(String access_token) {
		this.access_token = access_token;
	}

	public String getTokenType() {
		return token_type;
	}

	public void setTokenType(String token_type) {
		this.token_type = token_type;
	}

	public String getExpiresIn() {
		return expires_in;
	}

	public void setExpiresIn(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getRefreshToken() {
		return refresh_token;
	}

	public void setRefreshToken(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	
}
