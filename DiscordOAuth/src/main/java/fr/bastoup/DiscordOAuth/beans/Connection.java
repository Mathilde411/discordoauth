package fr.bastoup.DiscordOAuth.beans;

public interface Connection {
	String getId();
	String getName();
	String getType();
	boolean isRevoked();
	boolean isFriendSync();
	boolean isShowActivity();
	int getVisibility();
}
