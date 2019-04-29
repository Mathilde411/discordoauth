package fr.bastoup.DiscordOAuth.beans;

import java.util.Date;

public interface Token {
	public String getAccessToken();
	public String getRefreshToken();
	public Date getExpiry();
	public String[] getScopes();
}
