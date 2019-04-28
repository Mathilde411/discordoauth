package fr.bastoup.DiscordOAuth.beans;

import java.util.List;

public class UserBean implements User{
	private String id, username, discriminator, avatar, email;
	private boolean bot, mfa_enabled, verified;
	private List<Guild> guilds;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}

	public String getAvatar() {
		return avatar;
	}
	
	public String getAvatarURL() {
		return "https://cdn.discordapp.com/avatars/" + id + "/" + avatar + ".png";
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isBot() {
		return bot;
	}

	public void setBot(boolean bot) {
		this.bot = bot;
	}

	public boolean isMFAEnabled() {
		return mfa_enabled;
	}

	public void setMFAEnabled(boolean mfa_enabled) {
		this.mfa_enabled = mfa_enabled;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public List<Guild> getGuilds() {
		return guilds;
	}

	public void setGuilds(List<Guild> guilds) {
		this.guilds = guilds;
	}
}
