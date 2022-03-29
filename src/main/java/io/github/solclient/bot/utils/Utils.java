package io.github.solclient.bot.utils;

import java.util.function.Consumer;
import java.util.function.Predicate;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;

public class Utils {

	public static void getMostRecentMessage(TextChannel channel, Predicate<Message> criteria, Consumer<Message> callback, int limit) {
		MessageHistory history = channel.getHistory();
		history.retrievePast(limit).queue((result) -> {
			for(Message message : result) {
				if(criteria.test(message)) {
					callback.accept(message);
					return;
				}
			}
		});
	}

}
