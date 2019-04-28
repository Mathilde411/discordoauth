package fr.bastoup.DiscordOAuth.beans;

import java.util.Date;

public class GivenToken implements Token {
	private String accessToken;
	private String refreshToken;
	private Date expiery;
	private String[] scopes;
	
	@Override
	public String getAccessToken() {
		// TODO Auto-generated method stub
		return accessToken;
	}

	@Override
	public String getRefreshToken() {
		// TODO Auto-generated method stub
		return refreshToken;
	}

	@Override
	public Date getExpiery() {
		// TODO Auto-generated method stub
		return expiery;
	}

	@Override
	public String[] getScopes() {
		// TODO Auto-generated method stub
		return scopes;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void setExpiery(Date expiery) {
		this.expiery = expiery;
	}

	public void setScopes(String[] scopes) {
		this.scopes = scopes;
	}

}
