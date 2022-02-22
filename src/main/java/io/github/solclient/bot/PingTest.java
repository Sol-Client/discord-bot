package io.github.solclient.bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PingTest extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		super.onMessageReceived(event);

		if(event.getMessage().getContentRaw().equals("ping!")) {
			event.getChannel().sendMessage("pong! :ping_pong:");
		}
	}

}
