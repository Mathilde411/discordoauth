package fr.bastoup.DiscordOAuth;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import fr.bastoup.DiscordOAuth.beans.OAuthToken;
import fr.bastoup.DiscordOAuth.beans.OAuthUser;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DiscordOAuth {
	private static String _clientId;
	private static String _clientSecret;
	private static String[] _scopes;
	private static String _redirectUri;

	private static final String HOST = "discordapp.com";
	private static final String AUTHORIZE = "api/oauth2/authorize";
	private static final String TOKEN = "api/oauth2/token";
	private static final String USERS = "api/users/@me";

	public static void start(String clientId, String clientSecret, Scopes[] scopes, String redirectUri) {
		setClientId(clientId);
		setClientSecret(clientSecret);
		setScopes(scopes.length);
		for (int i = 0; i < scopes.length; i++) {
			_scopes[i] = scopes[i].toString();
		}
		setRedirectUri(redirectUri);
	}

	private static void setClientId(String clientId) {
		_clientId = clientId;
	}

	private static void setClientSecret(String clientSecret) {
		_clientSecret = clientSecret;
	}

	private static void setScopes(int nbr) {
		_scopes = new String[nbr];
	}

	private static void setRedirectUri(String redirectUri) {
		_redirectUri = redirectUri;
	}

	public static String buildURL(String state) {
		HttpUrl url = new HttpUrl.Builder().scheme("https").host(HOST).addPathSegments(AUTHORIZE)
				.addQueryParameter("response_type", "code").addQueryParameter("client_id", _clientId)
				.addQueryParameter("scope", String.join(" ", _scopes)).addQueryParameter("state", state)
				.addQueryParameter("redirect_uri", _redirectUri).build();
		return url.toString();
	}

	public static OAuthToken getOAuthToken(String code) {
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
			// TODO Add error response
			return new Gson().fromJson(respBody, OAuthToken.class);
		} catch (IOException e) {
			return null;
		}
	}

	public static OAuthUser getOAuthUser(OAuthToken token) {
		List<String> scps = Arrays.asList(token.getScope().split(" "));
		if (!(scps.contains(Scopes.IDENTIFY.toString()) || scps.contains(Scopes.EMAIL.toString())))
			return null;
		OkHttpClient client = new OkHttpClient();
		HttpUrl url = new HttpUrl.Builder().scheme("https").host(HOST).addPathSegments(USERS).build();

		Request req = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + token.getAccessToken()).get().build();

		try {
			Response resp = client.newCall(req).execute();
			String respBody = resp.body().string();
			System.out.println(respBody);
			return new Gson().fromJson(respBody, OAuthUser.class);
		} catch (IOException e) {
			return null;
		}
	}
	
	// TODO add other structures
}
