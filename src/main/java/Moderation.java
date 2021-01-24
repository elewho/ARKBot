import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.*;

public class Moderation {

    public void muteUser(Guild guild, String user, MessageChannel channel){
        List<Role> roles = guild.getRoles();
        Role mutedRole= null;



        if(!(guild.getRolesByName("MUTED", false).isEmpty())){ //if MUTED exists
            for(Role role : roles){
                if(role.getName().equals("MUTED")){
                    mutedRole = role;
                    break;
                }
            }

            guild.addRoleToMember(user, mutedRole).queue();
            System.out.println("reached 1");
        } else {
            mutedRole = guild.createRole()
                    .setName("MUTED")
                    .setMentionable(false)
                    .setPermissions()
                    .complete();

            System.out.println("Muted role created");
            /*
            NOT WORKING, ADDS ROLE TO MEMBS BUT PERMS ARE NOT SET
             */

            if(mutedRole!= null){
                guild.addRoleToMember(user, mutedRole).queue();
                channel.sendMessage("User " + user + " has been muted.");
            } else {
                System.out.println("unable to add user to mutedrole");
            }

        }

        /*
        create role "MUTED"
        assign role to person you wanna mute
        delete messages from that user for the past hour
         */

    }
    /*
    mute, temp mute, kick, ban, temp ban
    then the opposite of those

     */
}
