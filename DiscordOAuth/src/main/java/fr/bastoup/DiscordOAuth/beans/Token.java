package fr.bastoup.DiscordOAuth.beans;

import java.util.Date;

public interface Token {
	public String getAccessToken();
	public String getRefreshToken();
	public Date getExpiery();
	public String[] getScopes();
}
