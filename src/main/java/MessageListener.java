import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    private Roles roles = new Roles();
    Moderation moderation = new Moderation();

    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            Guild guild = event.getGuild();
            long user = event.getAuthor().getIdLong();
            MessageChannel channel = event.getChannel();
            String command = event.getMessage().getContentRaw();
            boolean isBot = event.getAuthor().isBot();

            switch(command){
                case "!newtribe": roles.createTribe(channel, guild, command, user);
                break;

                default: channel.sendMessage("ugh").queue(); break;
            }



        } catch (InsufficientPermissionException ipe){
            System.out.println("Insufficient permissions.");
        }
    }

        }


