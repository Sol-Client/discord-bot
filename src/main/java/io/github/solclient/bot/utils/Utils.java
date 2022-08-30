package io.github.solclient.bot.utils;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.utils.MarkdownSanitizer;

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

	/**
	 * Gets the top hoisted role of a member.
	 * @param member The member.
	 * @return The role (if any were found).
	 */
	public static Optional<Role> getPrimaryRole(Member member) {
		return member.getRoles().stream().filter(Role::isHoisted).findFirst();
	}

	public static String sanitise(String text) {
		return MarkdownSanitizer.sanitize(text);
	}

	public static String addNumberSuffix(int i) {
		int lastDigit = i % 10;

		switch(lastDigit) {
			case 1:
				return i + "st";
			case 2:
				return i + "nd";
			case 3:
				return i + "rd";
		}

		return i + "th";
	}

	public static String formatTime(OffsetDateTime timeCreated) {
		return "<t:" + timeCreated.toEpochSecond() + ":R>";
	}

}
