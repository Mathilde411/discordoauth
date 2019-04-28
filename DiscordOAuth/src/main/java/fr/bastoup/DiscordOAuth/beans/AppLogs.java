package fr.bastoup.DiscordOAuth.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.bastoup.DiscordOAuth.oauth.Scope;

public class AppLogs {
	private String clientId;
	private String clientSecret;
	private List<Scope> scopes;
	private String redirectUri;
	
	public AppLogs(String clientId, String clientSecret, Scope[] scopes, String redirectUri) {
		this.setClientId(clientId);
		this.setClientSecret(clientSecret);
		this.setScopes(new ArrayList<Scope>(Arrays.asList(scopes)));
		this.setRedirectUri(redirectUri);
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public List<Scope> getScopes() {
		return scopes;
	}
	
	public String getScopesString() {
		String str = "";
		for (Scope scope : scopes) {
			str += scope.toString() + " ";
		}
		return str.trim();
	}

	public void setScopes(List<Scope> scopes) {
		this.scopes = scopes;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
}
