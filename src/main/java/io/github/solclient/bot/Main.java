package io.github.solclient.bot;

import java.io.File;
import java.nio.file.Files;

import javax.security.auth.login.LoginException;

import org.tinylog.Logger;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main {

	public static void main(String[] args) throws LoginException {
		if(args.length != 1) {
			System.err.println("usage: sol-bot <token>");
			System.exit(1);
		}

		Logger.info("Starting up...");

		JDABuilder.createLight(args[0]).addEventListeners(new PingTest()).build();
	}

}
