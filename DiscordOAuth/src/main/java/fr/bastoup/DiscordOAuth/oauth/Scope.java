package fr.bastoup.DiscordOAuth.oauth;

import java.util.ArrayList;
import java.util.List;

public enum Scope {
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
	WEBHOOK("webhook.incoming");
	
	private String name;

	private Scope(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public static Scope getScope(String scope) {
		switch(scope.toLowerCase()) {
		case "bot":
			return BOT;
		case "connections":
			return CONNECTIONS;
		case "email":
			return EMAIL;
		case "identify":
			return IDENTIFY;
		case "guilds":
			return GUILDS;
		case "guilds.join":
			return GUILDS_JOIN;
		case "gdm.join":
			return GDM_JOIN;
		case "messages.read":
			return MESSAGES_READ;
		case "rpc":
			return RPC;
		case "rpc.api":
			return RPC_API;
		case "rpc.notifications.read":
			return RPC_NOTIF_READ;
		case "webhook.incoming":
			return WEBHOOK;
		default:
			return null;
		}
	}
	
	public static List<Scope> getScopes(Iterable<String> scopes) {
		List<Scope> scps = new ArrayList<Scope>();
		for (String s : scopes) {
			Scope scp = getScope(s);
			if (scp != null) {
				scps.add(scp);
			}
		}
		return scps;
	}
	
	public static List<Scope> getScopes(String[] scopes) {
		List<Scope> scps = new ArrayList<Scope>();
		for (String s : scopes) {
			Scope scp = getScope(s);
			if (scp != null) {
				scps.add(scp);
			}
		}
		return scps;
	}
}

