package fr.bastoup.DiscordOAuth.oauth;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import fr.bastoup.DiscordOAuth.beans.AppLogs;
import fr.bastoup.DiscordOAuth.beans.Connection;
import fr.bastoup.DiscordOAuth.beans.ConnectionBean;
import fr.bastoup.DiscordOAuth.beans.ErrorBean;
import fr.bastoup.DiscordOAuth.beans.Guild;
import fr.bastoup.DiscordOAuth.beans.GuildBean;
import fr.bastoup.DiscordOAuth.beans.IssuedTokenBean;
import fr.bastoup.DiscordOAuth.beans.Token;
import fr.bastoup.DiscordOAuth.beans.User;
import fr.bastoup.DiscordOAuth.beans.UserBean;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OAuthToken {
	private final Gson gson = new Gson();
	
	private static final String HOST = "discordapp.com";
	private static final String TOKEN = "api/oauth2/token";
	private static final String USERS = "api/users/@me";
	private static final String GUILDS = "api/users/@me/guilds";
	private static final String CONNECTIONS = "api/users/@me/connections";
	
	private AppLogs logs;
	private Token token;
	
	protected OAuthToken(AppLogs logs, Token token) {
		this.setLogs(logs);
		this.setToken(token);
		if(token.getExpiry().compareTo(new Date()) < 0) {
			try {
				refresh();
			} catch (OAuthException e) {
				e.printStackTrace();
			}
		}
	}
	
	public OAuthToken(AppLogs logs, Token token, List<Scope> scopes) {
		this(logs, token);
		this.logs.setScopes(scopes);
	}

	private void refresh() throws OAuthException {
		OkHttpClient client = new OkHttpClient();
		HttpUrl url = new HttpUrl.Builder().scheme("https").host(HOST).addPathSegments(TOKEN).build();
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("client_id", logs.getClientId()).addFormDataPart("client_secret", logs.getClientSecret())
				.addFormDataPart("grant_type", "refresh_token").addFormDataPart("refresh_token", token.getRefreshToken())
				.addFormDataPart("redirect_uri", logs.getRedirectUri()).addFormDataPart("scope", logs.getScopesString())
				.build();

		Request req = new Request.Builder().url(url).addHeader("Content-Type", "application/x-www-form-urlencoded")
				.post(body).build();

		try {
			Response resp = client.newCall(req).execute();
			String respBody = resp.body().string();
			hasErrors(respBody);
			setToken(gson.fromJson(respBody, IssuedTokenBean.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public User getUser() throws OAuthException {
		if (!(logs.getScopes().contains(Scope.IDENTIFY) || logs.getScopes().contains(Scope.EMAIL)))
			return null;
		OkHttpClient client = new OkHttpClient();
		HttpUrl url = new HttpUrl.Builder().scheme("https").host(HOST).addPathSegments(USERS).build();

		Request req = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + token.getAccessToken())
				.get().build();

		try {
			Response resp = client.newCall(req).execute();
			String respBody = resp.body().string();
			hasErrors(respBody);
			UserBean usr = gson.fromJson(respBody, UserBean.class);
			usr.setGuilds(logs.getScopes().contains(Scope.GUILDS) ? getUserGuilds() : null);
			usr.setConnections(logs.getScopes().contains(Scope.GUILDS) ? getConnections() : null);
			return usr;
		} catch (IOException e) {
			return null;
		}
	}
	
	private List<Connection> getConnections() throws OAuthException {
		if (!logs.getScopes().contains(Scope.CONNECTIONS))
			return null;
		OkHttpClient client = new OkHttpClient();
		HttpUrl url = new HttpUrl.Builder().scheme("https").host(HOST).addPathSegments(CONNECTIONS).build();

		Request req = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + token.getAccessToken())
				.get().build();

		try {
			Response resp = client.newCall(req).execute();
			String respBody = resp.body().string();
			hasErrors(respBody);
			Type listType = new TypeToken<ArrayList<ConnectionBean>>(){}.getType();
			return gson.fromJson(respBody, listType);
		} catch (IOException e) {
			return null;
		}
	}
	
	private List<Guild> getUserGuilds() throws OAuthException {
		if (!(logs.getScopes().contains(Scope.GUILDS)))
			return null;
		OkHttpClient client = new OkHttpClient();
		HttpUrl url = new HttpUrl.Builder().scheme("https").host(HOST).addPathSegments(GUILDS).build();

		Request req = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + token.getAccessToken())
				.get().build();

		try {
			Response resp = client.newCall(req).execute();
			String respBody = resp.body().string();
			hasErrors(respBody);
			Type listType = new TypeToken<ArrayList<GuildBean>>(){}.getType();
			return gson.fromJson(respBody, listType);
		} catch (IOException e) {
			return null;
		}
	}
	
	private void hasErrors(String body) throws OAuthException {
		try {
			ErrorBean err = gson.fromJson(body, ErrorBean.class);
			if (err.getError() != null || err.getErrorDescription() != null) {
				throw new OAuthException(err.getError() + ":" + err.getErrorDescription());
			}
		} catch (JsonSyntaxException e) {
			// Do Nothing
		}
	}

	private void setLogs(AppLogs logs) {
		this.logs = logs;
	}

	public Token getToken() {
		return token;
	}

	private void setToken(Token token) {
		this.token = token;
	}
}
