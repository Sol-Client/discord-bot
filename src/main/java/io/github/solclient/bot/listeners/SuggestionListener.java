package io.github.solclient.bot.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SuggestionListener extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		super.onMessageReceived(event);

		if(event.getChannel().getName().equals("suggestions")) {
			event.getMessage().addReaction("U+2B06").queue();
			event.getMessage().addReaction("U+2B07").queue();

			event.getMessage().createThreadChannel("Suggestion Discussion (" + event.getAuthor().getName() + ")").queue();;
		}
	}

}
