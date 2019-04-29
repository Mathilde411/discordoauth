package fr.bastoup.DiscordOAuth.beans;

import java.util.List;

public interface User {
	String getId();
	String getUsername();
	String getDiscriminator();
	String getAvatarURL();
	String getEmail();
	boolean isBot();
	boolean isMFAEnabled();
	boolean isVerified();
	List<Guild> getGuilds();
	String getLocale();
	Integer getFlags();
	Integer getPremiumType();
	List<Connection> getConnections();
}
