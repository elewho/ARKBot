package listeners;

import gruntwork.*;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MessageListener extends ListenerAdapter {

    private Roles roles = new Roles();
    private Moderation mod = new Moderation();

    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            Guild guild = event.getGuild();
            Member member = event.getMember();
            long userID = event.getAuthor().getIdLong();
            MessageChannel channel = event.getChannel();
            String rawMsg = event.getMessage().getContentRaw();
            String[] rawMsgArray = event.getMessage().getContentRaw().split(" ");
            boolean isBot = event.getAuthor().isBot();
            String roleName = "";

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
                            mod.muteUser(guild, rawMsgArray, mutedRole);
                            channel.sendMessage("User " + rawMsgArray[1] + " has been muted.").queue();
                        } else
                            channel.sendMessage("You do not have the permissions for this command.").queue();
                        break;

                    case "!kick":
                        if(hasModPerms(member)){
                            mod.kickUser(guild, rawMsgArray);
                            channel.sendMessage("User " + rawMsgArray[1] + " has been kicked.").queue();
                            Member badUser = guild.getMemberById(rawMsgArray[1]);
                            System.out.println(rawMsgArray[1]);

                            JDA jda = event.getJDA();
                            User user = jda.retrieveUserById(rawMsgArray[1]).complete();

                            if(user != null){
                                user.openPrivateChannel()
                                        .flatMap(privateChannel -> privateChannel.sendMessage("hewwo"))
                                        .queue();
                            } else{
                                System.out.println("User is null.");
                            }

                        }
                        break;


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


