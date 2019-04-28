package fr.bastoup.DiscordOAuth;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import fr.bastoup.DiscordOAuth.beans.DiscordError;
import fr.bastoup.DiscordOAuth.beans.OAuthToken;
import fr.bastoup.DiscordOAuth.beans.OAuthUser;
import fr.bastoup.DiscordOAuth.oauth.OAuthConfigurationException;
import fr.bastoup.DiscordOAuth.oauth.OAuthException;
import fr.bastoup.DiscordOAuth.oauth.Scopes;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DiscordOAuth {
	private final Gson gson = new Gson();
	private String _clientId;
	private String _clientSecret;
	private String[] _scopes;
	private String _redirectUri;

	private static final String HOST = "discordapp.com";
	private static final String AUTHORIZE = "api/oauth2/authorize";
	private static final String TOKEN = "api/oauth2/token";
	private static final String USERS = "api/users/@me";

	public static DiscordOAuth newInstance(String clientId, String clientSecret, Scopes[] scopes, String redirectUri)
			throws OAuthConfigurationException {
		if (clientId == null || clientId == "" || clientSecret == null || clientSecret == "" || redirectUri == null
				|| redirectUri == "" || scopes.length == 0)
			throw new OAuthConfigurationException("Build information is not complete.");
		DiscordOAuth oauth = new DiscordOAuth();
		oauth.setClientId(clientId);
		oauth.setClientSecret(clientSecret);
		oauth.setScopes(scopes.length);
		for (int i = 0; i < scopes.length; i++) {
			oauth._scopes[i] = scopes[i].toString();
		}
		oauth.setRedirectUri(redirectUri);
		return oauth;
	}

	private void setClientId(String clientId) {
		_clientId = clientId;
	}

	private void setClientSecret(String clientSecret) {
		_clientSecret = clientSecret;
	}

	private void setScopes(int nbr) {
		_scopes = new String[nbr];
	}

	private void setRedirectUri(String redirectUri) {
		_redirectUri = redirectUri;
	}

	public String buildURL(String state) {
		HttpUrl url = new HttpUrl.Builder().scheme("https").host(HOST).addPathSegments(AUTHORIZE)
				.addQueryParameter("response_type", "code").addQueryParameter("client_id", _clientId)
				.addQueryParameter("scope", String.join(" ", _scopes)).addQueryParameter("state", state)
				.addQueryParameter("redirect_uri", _redirectUri).build();
		return url.toString();
	}

	public OAuthToken getOAuthToken(String code) throws OAuthException {
		OkHttpClient client = new OkHttpClient();
		HttpUrl url = new HttpUrl.Builder().scheme("https").host(HOST).addPathSegments(TOKEN).build();
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("client_id", _clientId).addFormDataPart("client_secret", _clientSecret)
				.addFormDataPart("grant_type", "authorization_code").addFormDataPart("code", code)
				.addFormDataPart("redirect_uri", _redirectUri).build();

		Request req = new Request.Builder().url(url).addHeader("Content-Type", "application/x-www-form-urlencoded")
				.post(body).build();

		try {
			Response resp = client.newCall(req).execute();
			String respBody = resp.body().string();
			System.out.println(respBody);
			hasErrors(respBody);
			return gson.fromJson(respBody, OAuthToken.class);
		} catch (IOException e) {
			return null;
		}
	}

	private void hasErrors(String body) throws OAuthException {
		DiscordError err = gson.fromJson(body, DiscordError.class);
		if (err.getError() != null || err.getErrorDescription() != null) {
			throw new OAuthException(err.getError() + ":" + err.getErrorDescription());
		}
	}

	public OAuthToken refreshToken(String refresh) throws OAuthException {
		OkHttpClient client = new OkHttpClient();
		HttpUrl url = new HttpUrl.Builder().scheme("https").host(HOST).addPathSegments(TOKEN).build();
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("client_id", _clientId).addFormDataPart("client_secret", _clientSecret)
				.addFormDataPart("grant_type", "refresh_token").addFormDataPart("refresh_token", refresh)
				.addFormDataPart("redirect_uri", _redirectUri).addFormDataPart("scope", String.join(" ", _scopes))
				.build();

		Request req = new Request.Builder().url(url).addHeader("Content-Type", "application/x-www-form-urlencoded")
				.post(body).build();

		try {
			Response resp = client.newCall(req).execute();
			String respBody = resp.body().string();
			System.out.println(respBody);
			hasErrors(respBody);
			return gson.fromJson(respBody, OAuthToken.class);
		} catch (IOException e) {
			return null;
		}
	}

	public OAuthUser getOAuthUser(OAuthToken token) throws OAuthException {
		List<String> scps = Arrays.asList(token.getScope().split(" "));
		if (!(scps.contains(Scopes.IDENTIFY.toString()) || scps.contains(Scopes.EMAIL.toString())))
			return null;
		OkHttpClient client = new OkHttpClient();
		HttpUrl url = new HttpUrl.Builder().scheme("https").host(HOST).addPathSegments(USERS).build();

		Request req = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + token.getAccessToken())
				.get().build();

		try {
			Response resp = client.newCall(req).execute();
			String respBody = resp.body().string();
			hasErrors(respBody);
			return gson.fromJson(respBody, OAuthUser.class);
		} catch (IOException e) {
			return null;
		}
	}
}
