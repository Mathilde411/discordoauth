package fr.bastoup.DiscordOAuth.oauth;

public enum Scopes {
	BOT("bot"),
	CONNECTIONS("connections"),
	EMAIL("email"),
	IDENTIFY("identify"),
	GUILDS("guilds"),
	GUILDS_JOIN("guilds.join"),
	GDM_JOIN("gdm.join"),
	MESSAGES_READ("messages.read"),
	RPC("rpc"),
	RPC_API("rpc.api"),
	RPC_NOTIF_READ("rpc.notifications.read"),
	WEBHOOK_INC("webhook.incoming");
	
	private String name;

	private Scopes(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
