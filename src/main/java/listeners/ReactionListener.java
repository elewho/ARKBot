package listeners;
import gruntwork.*;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class ReactionListener extends ListenerAdapter {
    public void onMessageReactionAdd (MessageReactionAddEvent event){
        if(!event.getUser().isBot()){
            MessageChannel channel = event.getChannel();
            Guild guild = event.getGuild();

            String msgChannel = channel.getId(), userID = event.getUserId();
            List<Role> mentionedRoles = event.retrieveMessage().complete().getMentionedRoles();
            if(msgChannel.equals("775566646886531085"))//Gotta find a way to set this
                guild.addRoleToMember(userID, mentionedRoles.get(0)).queue();
        }
    }

    public void onMessageReactionRemove (MessageReactionRemoveEvent event){
        if(!event.getUser().isBot()) {
            MessageChannel channel = event.getChannel();
            Guild guild = event.getGuild();

            String msgChannel = channel.getId(), userID = event.getUserId();
            List<Role> mentionedRole = event.retrieveMessage().complete().getMentionedRoles();

            if (msgChannel.equals("775566646886531085")) //Gotta find a way to set this.
                guild.removeRoleFromMember(userID, mentionedRole.get(0)).queue();
        }
    }
}



