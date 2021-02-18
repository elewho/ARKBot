package gruntwork;


import net.dv8tion.jda.api.entities.*;

public class Moderation {

    /**
     *
     * @param guild what guild the action should be taken in
     * @param badUser user to be muted
     * @param mutedRole role MUTED
     */
    public void muteUser(Guild guild, User badUser, Role mutedRole) {
        guild.addRoleToMember(badUser.getId(), mutedRole);
    }

    public void msgUser(){

    }

    /**
     * Kicks person out of guild.
     *
     * @param guild what guild the action should be taken in
     * @param user Object user to be kicked
     */
    public void kickUser(Guild guild, User user){
        guild.kick(user.getId()).queue();
    }




}
