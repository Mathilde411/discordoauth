package fr.bastoup.DiscordOAuth.oauth;

public class OAuthConfigurationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public OAuthConfigurationException( String message ) {
        super( message );
    }

    public OAuthConfigurationException( String message, Throwable cause ) {
        super( message, cause );
    }

    public OAuthConfigurationException( Throwable cause ) {
        super( cause );
    }
}
