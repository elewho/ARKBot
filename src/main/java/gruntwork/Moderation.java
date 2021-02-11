package gruntwork;


import net.dv8tion.jda.api.entities.*;

public class Moderation {

    public void muteUser(Guild guild, String [] rawMsg, Role mutedRole) {
        String warningMsg = "", badUser = rawMsg[1];
        guild.addRoleToMember(badUser, mutedRole).queue();
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
