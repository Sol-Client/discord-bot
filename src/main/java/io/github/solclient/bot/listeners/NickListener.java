package io.github.solclient.bot.listeners;

import java.util.Optional;

import io.github.solclient.bot.utils.NickManangement;
import io.github.solclient.bot.utils.Utils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GenericGuildMemberEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberUpdateEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateHoistedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class NickListener extends ListenerAdapter {

	@Override
	public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
		super.onGuildMemberRoleAdd(event);
		update(event);
	}

	@Override
	public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
		super.onGuildMemberRoleRemove(event);
		update(event);
	}

	@Override
	public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent event) {
		super.onGuildMemberUpdateNickname(event);
		update(event);
	}

	@Override
	public void onRoleUpdateHoisted(RoleUpdateHoistedEvent event) {
		super.onRoleUpdateHoisted(event);
		event.getGuild().getMembersWithRoles(event.getRole()).forEach(NickManangement::updateNick);
	}

	private static void update(GenericGuildMemberEvent event) {
		NickManangement.updateNick(event.getMember());
	}

}
