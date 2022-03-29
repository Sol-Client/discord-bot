package io.github.solclient.bot.listeners;

import java.util.Locale;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Quick 'n dirty fallback in case Carl doesn't block this for us!
 */
public class ScamListener extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		scanMessage(event.getMessage());
	}

	@Override
	public void onMessageUpdate(MessageUpdateEvent event) {
		scanMessage(event.getMessage());
	}

	private void scanMessage(Message message) {
		String processed = message.getContentDisplay().replace(" ", "").toLowerCase(Locale.ROOT);

		if(processed.contains(".gift/") || processed.contains(".gifts/")) {
			message.delete().queue();
		}
	}

}
