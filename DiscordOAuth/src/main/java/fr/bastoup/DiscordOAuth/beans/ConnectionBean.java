package fr.bastoup.DiscordOAuth.beans;

public class ConnectionBean implements Connection {
	private String id;
	private String name;
	private String type;
	private boolean revoked;
	private boolean verified;
	private boolean friend_sync;
	private boolean show_activity;
	private int visibility;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public boolean isFriendSync() {
		return friend_sync;
	}

	public void setFriendSync(boolean friend_sync) {
		this.friend_sync = friend_sync;
	}

	public boolean isShowActivity() {
		return show_activity;
	}

	public void setShowActivity(boolean show_activity) {
		this.show_activity = show_activity;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
}
