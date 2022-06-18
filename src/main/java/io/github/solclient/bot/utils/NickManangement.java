package io.github.solclient.bot.utils;

import java.util.Optional;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class NickManangement {

	private static final String NICK_SEPERATOR = " | ";

	public static void updateNick(Member member) {
		String baseNick = member.getNickname();

		if(baseNick.contains(NICK_SEPERATOR)) {
			baseNick = baseNick.substring(baseNick.indexOf(NICK_SEPERATOR) + NICK_SEPERATOR.length());
		}

		Optional<Role> primaryRole = Utils.getPrimaryRole(member);

		if(primaryRole.isPresent()) {
			member.modifyNickname(primaryRole.get().getName() + " | " + baseNick).queue();
		}
	}

}
