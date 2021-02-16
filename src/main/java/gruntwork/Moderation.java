package gruntwork;


import net.dv8tion.jda.api.entities.*;

public class Moderation {

    public void muteUser(Guild guild, Message message, Role mutedRole) {
        User badUser = message.getMentionedUsers().get(0);
        /*
        check if user is already muted

         */

        guild.addRoleToMember();
    }

    /**
     * Kicks person out of guild.
     *
     * @param guild what guild the action should be taken in
     * @param rawMsg takes first element in String array as user's name
     */
    public void kickUser(Guild guild, String [] rawMsg){
        guild.kick(rawMsg[1]).queue();
    }




}
