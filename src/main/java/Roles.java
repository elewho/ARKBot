import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Roles{

    public void createTribe(MessageChannel channel, Guild g, String rawMsg, long user){
        boolean colorSpecified = false;
        String color = "", roleName = "";
        Role role;

        // checks if color is specified
        if(rawMsg.contains("#")){
            colorSpecified = true;
            int i = rawMsg.indexOf("#");
            color += rawMsg.substring(i);
            roleName += rawMsg.substring(10,i); // 8 is !newtribe
            System.out.println(roleName);
        } else{
            roleName += rawMsg.substring(10);
            System.out.println(roleName);
        }

        // checks if role already exists
        if(!(g.getRolesByName(roleName,true).isEmpty())){
            channel.sendMessage("This tribe name already exists! Please specify another name for your tribe.").queue();
            return;
        }

        if(rawMsg.length() > 10){ //could probably change this. put rawmsg into string array, and check if array size is more than 0
            if(roleName.length() > 0 && roleName.length() < 33) {
                try{
                    if(colorSpecified){
                        role = g.createRole()
                                .setName(roleName)
                                .setMentionable(true)
                                .setColor(Color.decode(color))
                                .complete(); //this is what you were missing when creating role
                    } else{
                        role = g.createRole()
                                .setName(roleName)
                                .setMentionable(true)
                                .complete(); //this is what you were missing when creating role
                    }

                    if(role != null){
                        g.addRoleToMember(user, role).queue(); // needs .complete() to..complete
                        channel.sendMessage(roleName + " created! React to this message to join the tribe.").queue(message -> message.addReaction("U+2705").queue()); //queue important to send msg, it is a RestAction
                        return;
                    } else{
                        System.out.println("Role is null.");
                        return ;
                    }

                }  catch (IllegalArgumentException iae){
                    System.out.println("There was an invalid input: " + iae);
                    channel.sendMessage("Invalid input!").queue();
                    System.exit(0);
                }
            }
        } else{
            channel.sendMessage("Invalid input! Please specify a tribe name.").queue();

        }

    }

}
