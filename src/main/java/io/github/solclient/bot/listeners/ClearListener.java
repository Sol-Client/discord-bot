package io.github.solclient.bot.listeners;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections4.map.HashedMap;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class ClearListener extends ListenerAdapter {

	private Map<String, ClearAction> clearActions = new HashedMap<>();

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		if (!event.getName().equals("clear")) {
			return;
		}

		int n = event.getOption("n").getAsInt();

		if (n > 100) {
			event.reply("Limited to 100").setEphemeral(true).queue();
			return;
		}

		MessageHistory history = event.getChannel().getHistory();
		history.retrievePast(n).queue((messages) -> {
			String uuid = UUID.randomUUID().toString();

			Message downFrom = messages.get(messages.size() - 1);

			Message message = new MessageBuilder()
					.setContent(messages.size() + " messages will be deleted, down from this one:")
					.setEmbeds(new EmbedBuilder()
							.setAuthor(downFrom.getAuthor().getName(), null, downFrom.getAuthor().getAvatarUrl())
							.setTitle("Jump to Message", downFrom.getJumpUrl()).setColor(Color.decode("#f74747"))
							.setDescription(downFrom.getContentDisplay()).setTimestamp(downFrom.getTimeCreated())
							.build())
					.setActionRows(ActionRow.of(Button.secondary(uuid + "-cancel", "Cancel"),
							Button.danger(uuid + "-confirm", "Confirm")))
					.build();

			event.reply(message).queue((processedMessage) -> {
				clearActions.put(uuid, new ClearAction(messages, event.getUser().getIdLong()));
			});
		});
	}

	@Override
	public void onButtonInteraction(ButtonInteractionEvent event) {
		if (!event.getComponentId().contains("-"))
			return;

		String id = event.getComponentId().substring(0, event.getComponentId().lastIndexOf("-"));

		if (!clearActions.containsKey(id))
			return;

		ClearAction action = clearActions.get(id);

		if (action.actor != event.getUser().getIdLong()) {
			event.reply("You didn't initiate the clear command").setEphemeral(true).queue();
			return;
		}

		event.getMessage().delete().queue();

		if (event.getComponentId().endsWith("confirm"))
			event.getChannel().purgeMessages(action.toDelete);
	}

	private class ClearAction {

		private List<Message> toDelete;
		private long actor;

		public ClearAction(List<Message> toDelete, long author) {
			this.toDelete = toDelete;
			this.actor = author;
		}

	}

}
