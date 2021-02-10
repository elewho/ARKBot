import net.dv8tion.jda.api.entities.*;

public class Moderation {

    public void muteUser(Guild guild, String [] rawMsg, Role mutedRole) {
        String warningMsg = "", badUser = rawMsg[1];
        guild.addRoleToMember(badUser, mutedRole).queue();
    }

    public void kickUser(Guild guild, String [] rawMsg){
        guild.kick(rawMsg[1]).queue();
    }



        /*
        create role "MUTED"
        assign role to person you wanna mute
        delete messages from that user for the past hour
         */




    /*
    mute, temp mute, kick, ban, temp ban
    then the opposite of those

     */
}
