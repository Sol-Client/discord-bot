package io.github.solclient.bot.listeners;

import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MorseListener extends ListenerAdapter {

	// morse codes, according to Wikipedia
	// the definitive source for any and all information
	private static List<String> morseCodes = Arrays.asList("•—", "—•••", "—•—•", "—••", "•", "••—•", "——•", "••••", "••",
			"•———", "—•—", "•—••", "——", "—•", "———", "•——•", "——•—", "•—•", "•••", "—", "••—", "•••—", "•——", "—••—",
			"—•——", "——••", "•————", "••———", "•••——", "••••—", "•••••", "—••••", "——•••", "———••", "————•", "—————");

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		if(event.getName().equals("atom")) {
			event.reply(atom(event.getOption("data").getAsString())).queue();
		}
		else if(event.getName().equals("mtoa")) {
			event.reply(mtoa(event.getOption("data").getAsString())).queue();
		}
	}

	private static String atom(String data) {
		StringBuilder builder = new StringBuilder();

		for(int i = 0; i < data.length(); i++) {
			char character = data.charAt(i);

			int morseIndex;

			if(character >= 'A' && character <= 'Z') {
				morseIndex = character - 65;
			}
			else if(character >= 'a' && character <= 'z') {
				morseIndex = character - 97;
			}
			else if(character >= '0' && character <= '9') {
				morseIndex = character - 23;

				if(character == '0') {
					morseIndex += 10;
				}
			}

			else {
				if(character == ' ' && i != data.length() - 1) {
					builder.append(" /");
				}
				continue;
			}

			if(i != 0) {
				builder.append(" ");
			}

			builder.append(morseCodes.get(morseIndex));
		}

		String result = builder.toString();

		if(result.isEmpty()) {
			return "Could not find any alphanumeric characters";
		}

		return result;
	}

	private static String mtoa(String data) {
		StringBuilder builder = new StringBuilder();

		// assume double-space is full space
		data = data.replace("  ", "/");
		data = data.replace("|", "/");
		data = data.replace(" / ", "/");
		data = data.replace(".", "•");
		data = data.replace("·", "•");
		data = data.replace("-", "—");
		data = data.replace("_", "—");

		while(data.startsWith(" ")) {
			data = data.substring(1);
		}

		while(data.endsWith(" ")) {
			data = data.substring(0, data.length() - 1);
		}

		System.out.println(data);

		StringBuilder currentMorse = new StringBuilder();

		for(int i = 0; i < data.length(); i++) {
			char character = data.charAt(i);

			if(character == '•' || character == '—') {
				currentMorse.append(character);
			}

			if(character == '/' || character == ' ' || i == data.length() - 1) {
				int morseIndex = morseCodes.indexOf(currentMorse.toString());

				if(morseIndex > 25) {
					int offset = morseIndex - 25;

					if(offset == 0) {
						offset = 9;
					}
					else if(offset == 10) {
						offset = 0;
					}

					builder.append((char) (48 + offset));
				}
				else if(morseIndex < 0) {
					continue;
				}
				else {
					builder.append((char) (65 + morseIndex));
				}

				if(character == '/') {
					builder.append(" ");
				}

				currentMorse = new StringBuilder();
			}
		}

		String result = builder.toString();

		if(result.isEmpty()) {
			return "Could not find any valid morse codes";
		}

		return result;
	}

}
