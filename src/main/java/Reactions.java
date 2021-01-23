import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class Reactions extends ListenerAdapter {

    public void onMessageReactionRemove (MessageReactionRemoveEvent event){
        Member member = event.getMember();
        Guild guild = event.getGuild();
        String emote = event.getReactionEmote().getAsReactionCode();
        Message msg = event.retrieveMessage().complete();
        List<Role> mentionedRoles = msg.getMentionedRoles();
        boolean roleMentioned = msg.getMentionedRoles().isEmpty();

        switch(emote){
            case "U+2705": //green check mark
                if(!roleMentioned){
                    leaveTribe(member, mentionedRoles, guild);
                }
                break;

            default: break;
        }
    }

    public void joinTribe(Member member, List<Role> mentionedRoles, Guild guild) {
        guild.addRoleToMember(member, mentionedRoles.get(0)).queue();
    }

    private void leaveTribe(Member member, List<Role> mentionedRoles, Guild guild) {
        guild.removeRoleFromMember(member, mentionedRoles.get(0)).queue();
    }
}


