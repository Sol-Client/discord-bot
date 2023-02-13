package io.github.solclient.bot.listeners;

import io.github.solclient.bot.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinLeaveListener extends ListenerAdapter {

	private TextChannel channel;

	public JoinLeaveListener(TextChannel channel) {
		this.channel = channel;
	}

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		send(event.getUser(), Type.JOIN);
	}

	@Override
	public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
		send(event.getUser(), Type.LEAVE);
	}

	private void send(User user, Type type) {
		channel.sendMessageEmbeds(buildEmbed(user, type)).queue();
	}

	private MessageEmbed buildEmbed(User user, Type type) {
		return new EmbedBuilder()
				.setAuthor(user.getName() + '#' + user.getDiscriminator(), "https://discord.com/users/" + user.getId(),
						user.getAvatarUrl())
				.setTitle("Member " + type.past).setDescription(buildDescription(user, type)).setColor(type.colour)
				.build();
	}

	private String buildDescription(User user, Type type) {
		switch (type) {
		case JOIN:
			return user.getAsMention() + " - " + Utils.addNumberSuffix(channel.getGuild().getMemberCount())
					+ " to join.\nCreated " + Utils.formatTime(user.getTimeCreated()) + ".";
		case LEAVE:
			return user.getAsMention() + " - The guild now has " + channel.getGuild().getMemberCount()
					+ " members remaining.";
		default:
			throw new IllegalArgumentException(String.valueOf(type));
		}
	}

	enum Type {
		JOIN("join", "joined", 0xFF62FF56), LEAVE("leave", "left", 0xFFFF4B4B);

		final String verb;
		final String past;
		final int colour;

		Type(String verb, String past, int colour) {
			this.verb = verb;
			this.past = past;
			this.colour = colour;
		}

	}

}
