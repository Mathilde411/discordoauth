package fr.bastoup.DiscordOAuth.beans;

public class DiscordError {
	private String error;
	private String error_description;
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}

	public String getErrorDescription() {
		return error_description;
	}

	public void setErrorDescription(String error_description) {
		this.error_description = error_description;
	}
}
