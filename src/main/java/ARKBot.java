import listeners.*;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class ARKBot {

    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA jda = JDABuilder.create("Nzc1NTYzMzExOTM1NzgyOTQy.X6oJng.5e-MEwDXFVjz4BWfy60QL7RRByA", GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new MessageListener())
                .addEventListeners(new ReactionListener())
                .setActivity(Activity.playing("Counting dinos..."))
                .build();
        jda.awaitReady();
    }
}


