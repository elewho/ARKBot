import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

import java.awt.*;

public class Roles{

    public Role createTribe(MessageChannel channel, Guild guild, String color, String roleName, long user) {
        boolean roleExists = false;
        Role role;

        try {
            role = guild.createRole()
                    .setName(roleName)
                    .setColor(Color.decode(color))
                    .setMentionable(true)
                    .setHoisted(true)
                    .complete();

            // This adds the user making the role into the role.
            if (role != null) {
                guild.addRoleToMember(user, role).queue();
                return role;
            }
        } catch(InsufficientPermissionException ipe){
            channel.sendMessage("You don't have the permission to use this command").queue();
            return null;
        } catch(IllegalArgumentException iae){
            channel.sendMessage("Unable to create role.").queue();
            System.out.println(iae);
            return null;
        }

        return null;
    }

    public Role createMuted(MessageChannel channel, Guild guild){
       try{
           Role role = null;
           role = guild.createRole()
                   .setName("MUTED")
                   .complete();
           return role;


       } catch (IllegalArgumentException iae){
           channel.sendMessage("Wrong parameters given. Try again.");
        }

        return null;
    }
}
