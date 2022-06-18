package io.github.solclient.bot;

import java.util.Arrays;

import javax.security.auth.login.LoginException;

import io.github.solclient.bot.listeners.ClearListener;
import io.github.solclient.bot.listeners.GitHubSpamListener;
import io.github.solclient.bot.listeners.MorseListener;
import io.github.solclient.bot.listeners.NickListener;
import io.github.solclient.bot.listeners.ScamListener;
import io.github.solclient.bot.listeners.SuggestionListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege.Type;

public class Main {

	public static void main(String[] args) throws LoginException, InterruptedException {
		if(args.length != 1) {
			System.err.println("usage: sol-bot <token>");
			System.exit(1);
		}

		JDA jda = JDABuilder.createLight(args[0]).build().awaitReady();

		jda.getPresence().setStatus(OnlineStatus.IDLE);
		jda.getPresence().setActivity(Activity.watching("the telly"));

		jda.addEventListener(new SuggestionListener());
		jda.addEventListener(new ScamListener());
		jda.addEventListener(new GitHubSpamListener());
		jda.addEventListener(new MorseListener());
		jda.addEventListener(new ClearListener());
		jda.addEventListener(new NickListener());

		Guild solClientDiscord = jda.getGuildById(886561982872977408L);

		solClientDiscord.updateCommands()
				.addCommands(
						Commands.slash("atom", "Convert text to morse.").addOption(OptionType.STRING, "data",
								"The text.", true),
						Commands.slash("mtoa", "Convert morse to text.").addOption(OptionType.STRING, "data",
								"The text.", true),
						Commands.slash("clear", "Clear n messages").addOption(OptionType.INTEGER,
								"n", "The number of messages to clear.", true).setDefaultEnabled(false))
				.queue((commands) -> {
					for(Command command : commands) {
						if("clear".equals(command.getName())) {
							command.updatePrivileges(solClientDiscord, CommandPrivilege.enableRole(886562656562085909L))
									.queue();
						}
					}
				});
	}

}
