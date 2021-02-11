package gruntwork;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class Reactions extends ListenerAdapter {


    public void joinTribe(Member member, List<Role> mentionedRoles, Guild guild) {
        guild.addRoleToMember(member, mentionedRoles.get(0)).queue();
    }

    private void leaveTribe(Member member, List<Role> mentionedRoles, Guild guild) {
        guild.removeRoleFromMember(member, mentionedRoles.get(0)).queue();
    }
}


