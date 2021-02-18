package listeners;

import gruntwork.*;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.*;

public class MessageListener extends ListenerAdapter {

    private Roles roles = new Roles();
    private Moderation mod = new Moderation();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        try {
            Guild guild = event.getGuild();
            Member member = event.getMember();
            long userID = member.getIdLong();
            MessageChannel channel = event.getChannel();
            Message msg = event.getMessage();
            String rawMsg = event.getMessage().getContentRaw();
            String[] rawMsgArray = event.getMessage().getContentRaw().split(" ");
            boolean isBot = member.getUser().isBot();
            String roleName = "";

            /*
            // Prefix Mapping
            HashMap<Long, Character> moderationCommands = new HashMap<Long, Character>();
            moderationCommands.put(event.getGuild().getIdLong(), event.getMessage().getContentRaw().charAt(0));

            // Command Mapping
            HashMap<String, Roles> roleCommands = new HashMap<String, Roles>();
            HashMap<String, Moderation> modCommands = new HashMap<String, Moderation>();
            */


            if(!isBot){ //Can probably be changed to detect first char
                switch(rawMsgArray[0]){
                    case "!newtribe":

                        boolean colorSpecified;
                        String color = "";
                        Role role = null;

                        if(rawMsgArray.length > 4){
                            channel.sendMessage("The given tribe is either too long or contains a #. Please try again").queue();
                            return;
                        }

                        if(rawMsg.contains("#")){
                            int i = rawMsg.indexOf("#");
                            color += rawMsg.substring(i);
                            roleName += rawMsg.substring(10, i);
                            colorSpecified = true;
                        } else{
                            colorSpecified = false;
                            roleName += rawMsg.substring(10);
                        }

                        if(guild.getRolesByName(roleName, true).isEmpty()) {
                            if (colorSpecified) {
                                role = roles.createTribe(channel, guild, color, roleName, userID);
                            } else {
                                role = roles.createTribe(channel, guild, roleName, userID);
                            }
                        }

                        if (role != null) {
                            channel.sendMessage(role.getAsMention() + " created! Click the :white_check_mark: to enter.").queue((message -> message.addReaction("U+2705").queue()));
                        } else {
                            channel.sendMessage("This role name is already in use. Please try again.").queue();
                        }

                        break;

                    case "!mute":
                        Role mutedRole = null;
                        User badUser = msg.getMentionedUsers().get(0);
                        //Checks if MUTED Role exists.
                        if(guild.getRolesByName("MUTED", false).isEmpty()){
                            mutedRole = roles.createMuted(channel, guild);
                        }else{
                            List<Role> roles = guild.getRolesByName("MUTED", false);
                            for(Role r : roles)
                                if(r.getName().equals("MUTED")){
                                    mutedRole = r;
                                    break;
                                }
                        }

                        // This checks if user has modPerms and if mutedRole exists.

                        if(hasModPerms(member) && mutedRole != null){
                            mod.muteUser(guild, badUser, mutedRole);
                            channel.sendMessage("User " + rawMsgArray[1] + " has been muted.").queue();
                        } else
                            channel.sendMessage("You do not have the permissions for this command.").queue();
                        break;

                    case "!kick":
                        if(hasModPerms(member)){
                            User user = msg.getMentionedUsers().get(0);
                            if(user != null){
                                // It needed to be .complete() rather than .queue()
                                user.openPrivateChannel()
                                        .flatMap(privchannel -> privchannel.sendMessage("You have been kicked from the " + guild.getName())).complete();
                                mod.kickUser(guild, user);
                                channel.sendMessage("User " + user.getName() + " has been kicked.").queue();

                            } else{
                                System.out.println("User is null.");
                            }

                        }
                        break;

                    case "!pm":
                        User user = msg.getMentionedUsers().get(0);
                        user.openPrivateChannel()
                            .flatMap(privchannel -> privchannel.sendMessage("hi")).queue();
                        break;

                    case "!purge":
                        MessageChannel purgedChannel = msg.getMentionedChannels().get(0);
                        String msgID = rawMsgArray[2];
                        MessageHistory.MessageRetrieveAction msgHistory = MessageHistory.getHistoryAfter(purgedChannel, msgID);

                        purgedChannel.purgeMessages();
                        /*
                        getmessagechannel to purge
                        maybe take from date to date
                         */


                    default:
                        break;
                }
            }


        } catch (InsufficientPermissionException ipe){
            System.out.println("Insufficient permissions.");
        }
    }



    private boolean hasModPerms(Member member){
        List<Role> memRoles = member.getRoles();
        Role modRole = memRoles.stream()
                .filter(role -> role.getName().equals("Moderator"))
                .findFirst()
                .orElse(null);

        return member.getRoles().contains(modRole);

    }

}


