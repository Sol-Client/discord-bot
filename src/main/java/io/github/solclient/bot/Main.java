package io.github.solclient.bot;

import java.io.File;
import java.nio.file.Files;

import javax.security.auth.login.LoginException;

import org.tinylog.Logger;

import io.github.solclient.bot.listeners.ScamListener;
import io.github.solclient.bot.listeners.SuggestionListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main {

	public static void main(String[] args) throws LoginException {
		if(args.length != 1) {
			System.err.println("usage: sol-bot <token>");
			System.exit(1);
		}

		Logger.info("Starting up...");

		JDA jda = JDABuilder.createLight(args[0]).build();

		jda.getPresence().setStatus(OnlineStatus.IDLE);
		jda.getPresence().setActivity(Activity.watching("the telly"));

		jda.addEventListener(new SuggestionListener());
		jda.addEventListener(new ScamListener());
	}

}
