package fr.bastoup.DiscordOAuth.beans;

public class GuildBean implements Guild {
	private String id;
	private String name;
	private String icon;
	private boolean owner;
	private long permissions;
	
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

	public String getIconURL() {
		return "https://cdn.discordapp.com/icons/" + id + "/" + icon + ".png";
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	public long getPermissions() {
		return permissions;
	}

	public void setPermissions(long permissions) {
		this.permissions = permissions;
	}
}
