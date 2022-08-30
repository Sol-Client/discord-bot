package io.github.solclient.bot.listeners;

import io.github.solclient.bot.utils.Utils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Thanks, kuschelnder#8968.
 * I shouldn't need to do this!
 *
 * Class to stop GitHub users from trying to get recognition.
 * Or just annoy me.
 */
public class GitHubSpamListener extends ListenerAdapter {

	private final String channelName;

	public GitHubSpamListener(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(!channelName.equals(event.getChannel().getName())) {
			return;
		}

		String starrer = getStarrer(event.getMessage());

		if(starrer == null) {
			return;
		}

		Utils.getMostRecentMessage(event.getTextChannel(),
				(message) -> message.getIdLong() != event.getMessageIdLong() && starrer.equals(getStarrer(message)),
				(ignored) -> event.getMessage().delete().queue(), 10);
	}

	private String getStarrer(Message message) {
		if(message.getEmbeds().size() != 1) {
			return null;
		}

		MessageEmbed embed = message.getEmbeds().get(0);

		if(!embed.getTitle().matches("\\[.*\\/.*\\] New star added")) {
			return null;
		}

		return embed.getAuthor() == null ? null : embed.getAuthor().getUrl();
	}

}
