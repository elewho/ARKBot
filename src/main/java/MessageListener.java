import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    private Roles roles = new Roles();
    private Moderation moderation = new Moderation();

    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            Guild guild = event.getGuild();
            long author  = event.getAuthor().getIdLong();
            MessageChannel channel = event.getChannel();
            String rawMsg = event.getMessage().getContentRaw();
            String [] arr = rawMsg.split(" ", 2);
            boolean isBot = event.getAuthor().isBot();

            if(!isBot){
                switch(arr[0]){
                    case "!newtribe":
                        roles.createTribe(channel, guild, rawMsg, author);
                        break;
                    case "!mute":
                        moderation.muteUser(guild, arr[1], channel);
                        break;

                    default:
                        System.out.println("blah");
                        break;
                }
            }



        } catch (InsufficientPermissionException ipe){
            System.out.println("Insufficient permissions.");
        }
    }
}


