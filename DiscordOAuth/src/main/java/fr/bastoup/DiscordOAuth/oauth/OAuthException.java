package fr.bastoup.DiscordOAuth.oauth;

public class OAuthException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public OAuthException( String message ) {
        super( message );
    }

    public OAuthException( String message, Throwable cause ) {
        super( message, cause );
    }

    public OAuthException( Throwable cause ) {
        super( cause );
    }
}
