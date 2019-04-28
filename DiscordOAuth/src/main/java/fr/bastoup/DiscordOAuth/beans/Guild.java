package fr.bastoup.DiscordOAuth.beans;

public interface Guild {
	String getId();
	String getName();
	String getIconURL();
	boolean isOwner();
	long getPermissions();
}
