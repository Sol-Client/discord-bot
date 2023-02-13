package io.github.solclient.bot.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SuggestionListener extends ListenerAdapter {

	private final String channelName;

	public SuggestionListener(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (!channelName.equals(event.getChannel().getName()))
			return;

		event.getMessage().addReaction("U+2B06").queue();
		event.getMessage().addReaction("U+2B07").queue();

		event.getMessage().createThreadChannel("Suggestion Discussion (" + event.getAuthor().getName() + ")")
				.queue((thread) -> {
					thread.getManager().setArchived(true).queue();
				});
	}

}
