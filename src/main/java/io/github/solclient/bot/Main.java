package io.github.solclient.bot;

import javax.security.auth.login.LoginException;

import io.github.solclient.bot.listeners.ClearListener;
import io.github.solclient.bot.listeners.ComplimentListener;
import io.github.solclient.bot.listeners.FaqListener;
import io.github.solclient.bot.listeners.GitHubSpamListener;
import io.github.solclient.bot.listeners.JoinLeaveListener;
import io.github.solclient.bot.listeners.MorseListener;
import io.github.solclient.bot.listeners.SuggestionListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {

	public static void main(String[] args) throws LoginException, InterruptedException {
		if (args.length != 1) {
			System.err.println("usage: sol-bot <token>");
			System.exit(1);
		}

		JDA jda = JDABuilder.createDefault(args[0]).enableIntents(GatewayIntent.GUILD_MEMBERS).build().awaitReady();

		jda.getPresence().setStatus(OnlineStatus.IDLE);
		jda.getPresence().setActivity(Activity.listening("jazz"));

		Guild solClientDiscord = jda.getGuildById(886561982872977408L);

		jda.addEventListener(new SuggestionListener("suggestions"));
		jda.addEventListener(new GitHubSpamListener("github"));
		jda.addEventListener(new MorseListener());
		jda.addEventListener(new ClearListener());
		jda.addEventListener(new JoinLeaveListener(solClientDiscord.getTextChannelById(945981496399917056L)));
		jda.addEventListener(new FaqListener());
		jda.addEventListener(new ComplimentListener());

		solClientDiscord.updateCommands()
				.addCommands(
						Commands.slash("atom", "Convert text to morse.").addOption(OptionType.STRING, "data",
								"The text.", true),
						Commands.slash("mtoa", "Convert morse to text.").addOption(OptionType.STRING, "data",
								"The text.", true),
						Commands.slash("clear", "Clear n messages")
								.addOption(OptionType.INTEGER, "n", "The number of messages to clear.", true)
								.setDefaultEnabled(false),
						Commands.slash("faq", "Display FAQ message"),
						Commands.slash("compliment", "Receive a compliment."))
				.queue();
	}

}
