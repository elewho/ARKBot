package gruntwork;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

import java.awt.*;

public class Roles{

    /**
    Create a role with given name, color specified.
    @param channel what channel the message was sent in
    @param guild what guild the message was sent in
    @param color specified HEX
    @param roleName name of role
    @param user userID of user that called the command

    @return The Object role that was created
     */
    public Role createTribe(MessageChannel channel, Guild guild, String color, String roleName, long user) {
        Role role;

        try {
            role = guild.createRole()
                    .setName(roleName)
                    .setColor(Color.decode(color))
                    .setMentionable(true)
                    .setHoisted(true)
                    .complete();
            return role;
        } catch(InsufficientPermissionException ipe){
            channel.sendMessage("You don't have the permission to use this command").queue();
            return null;
        } catch(IllegalArgumentException iae){
            channel.sendMessage("Unable to create role.").queue();
            System.out.println(iae);
            return null;
        }
    }

    /**
     Create a role with given name.
     @param channel what channel the message was sent in
     @param guild what guild the message was sent in
     @param roleName name of role
     @param user userID of user that called the command

     @return The Object role that was created
     */

    public Role createTribe(MessageChannel channel, Guild guild, String roleName, long user) {
        Role role;

        try {
            role = guild.createRole()
                    .setName(roleName)
                    .setMentionable(true)
                    .setHoisted(true)
                    .complete();
            return role;
        } catch(InsufficientPermissionException ipe){
            channel.sendMessage("You don't have the permission to use this command").queue();
            return null;
        } catch(IllegalArgumentException iae){
            channel.sendMessage("Unable to create role.").queue();
            System.out.println(iae);
            return null;
        }
    }

    /**
     * Create MUTED role, if doesn't already exist.
     *
     * @param channel what channel the message was sent in
     * @param guild what guild the message was sent in
     *
     * @return Object role that was created
     */

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
