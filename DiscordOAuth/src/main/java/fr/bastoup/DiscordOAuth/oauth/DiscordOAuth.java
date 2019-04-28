package fr.bastoup.DiscordOAuth.oauth;

import java.io.IOException;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import fr.bastoup.DiscordOAuth.beans.AppLogs;
import fr.bastoup.DiscordOAuth.beans.ErrorBean;
import fr.bastoup.DiscordOAuth.beans.GivenToken;
import fr.bastoup.DiscordOAuth.beans.IssuedTokenBean;
import fr.bastoup.DiscordOAuth.beans.Token;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DiscordOAuth {
	private final Gson gson = new Gson();

	private static final String HOST = "discordapp.com";
	private static final String AUTHORIZE = "api/oauth2/authorize";
	private static final String TOKEN = "api/oauth2/token";

	private AppLogs logs;
	
	public static DiscordOAuth newInstance(String clientId, String clientSecret, Scope[] scopes, String redirectUri)
			throws OAuthConfigurationException {
		if (clientId == null || clientId == "" || clientSecret == null || clientSecret == "" || redirectUri == null
				|| redirectUri == "" || scopes.length == 0)
			throw new OAuthConfigurationException("Build information is not complete.");
		DiscordOAuth oauth = new DiscordOAuth();
		oauth.setLogs(new AppLogs(clientId, clientSecret, scopes, redirectUri));
		return oauth;
	}

	public String buildURL(String state) {
		HttpUrl url = new HttpUrl.Builder().scheme("https").host(HOST).addPathSegments(AUTHORIZE)
				.addQueryParameter("response_type", "code").addQueryParameter("client_id", logs.getClientId())
				.addQueryParameter("scope", logs.getScopesString()).addQueryParameter("state", state)
				.addQueryParameter("redirect_uri", logs.getRedirectUri()).build();
		return url.toString();
	}

	public OAuthToken getOAuthToken(String code) throws OAuthException {
		OkHttpClient client = new OkHttpClient();
		HttpUrl url = new HttpUrl.Builder().scheme("https").host(HOST).addPathSegments(TOKEN).build();
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("client_id", logs.getClientId()).addFormDataPart("client_secret", logs.getClientSecret())
				.addFormDataPart("grant_type", "authorization_code").addFormDataPart("code", code)
				.addFormDataPart("redirect_uri", logs.getRedirectUri()).build();

		Request req = new Request.Builder().url(url).addHeader("Content-Type", "application/x-www-form-urlencoded")
				.post(body).build();

		try {
			Response resp = client.newCall(req).execute();
			String respBody = resp.body().string();
			hasErrors(respBody);
			Token token = gson.fromJson(respBody, IssuedTokenBean.class);
			return new OAuthToken(getLogs(), token);
		} catch (IOException e) {
			return null;
		}
	}
	
	public OAuthToken getOAuthToken(String token, String refresh, Date expiry, String[] scope) throws OAuthException {
		GivenToken tokenObj = new GivenToken();
		tokenObj.setAccessToken(token);
		tokenObj.setScopes(scope);
		tokenObj.setRefreshToken(refresh);
		tokenObj.setExpiery(expiry);
		return new OAuthToken(getLogs(), tokenObj, Scope.getScopes(scope));
	}
	
	public OAuthToken getOAuthToken(Token token) throws OAuthException {
		return new OAuthToken(getLogs(), token, Scope.getScopes(token.getScopes()));
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

	private AppLogs getLogs() {
		return logs;
	}

	private void setLogs(AppLogs logs) {
		this.logs = logs;
	}
}
