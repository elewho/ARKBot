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
            List<Role> mentionedRole = event.retrieveMessage().complete().getMentionedRoles();
            if(msgChannel.equals("808981438468587540")){ //Gotta find a way to set this
                guild.addRoleToMember(userID, mentionedRole.get(0)).queue();
                channel.sendMessage("added to role").queue();
            }
        }
    }

    public void onMessageReactionRemove (MessageReactionRemoveEvent event){
        if(!event.getUser().isBot()){
            MessageChannel channel = event.getChannel();
            Guild guild = event.getGuild();

            String msgChannel = channel.getId(), userID = event.getUserId();
            List<Role> mentionedRole = event.retrieveMessage().complete().getMentionedRoles();

            if(msgChannel.equals("808981438468587540")){
                guild.removeRoleFromMember(userID, mentionedRole.get(0)).queue();
                channel.sendMessage("removed from role").queue();
            }
        }
    }


}
