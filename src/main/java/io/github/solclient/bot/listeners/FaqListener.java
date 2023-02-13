package io.github.solclient.bot.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class FaqListener extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		if (!event.getName().equals("faq"))
			return;

		event.reply("Hello! " + "This is a macro from Sol Bot. "
				+ "We would like to assure you that this is not laziness, but serves only to increase keyboard durability. \n\n"
				+ "The question you have asked has already been answered in <#1016365515121381416>.").queue();
	}

}
