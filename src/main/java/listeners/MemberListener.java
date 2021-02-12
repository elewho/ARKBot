package listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MemberListener extends ListenerAdapter {

    public void onGuildMemberJoin (GuildMemberJoinEvent event){
        Guild guild = event.getGuild();
        long userID = event.getMember().getIdLong();

        List<Role> roles = guild.getRolesByName("VERIFIED", false);
        Role verifiedRole = null;
        for(Role r : roles){
            if(r.getName().equals("VERIFIED")){
                verifiedRole = r;
                break;
            }
        }

        if(verifiedRole!= null){
            guild.addRoleToMember(userID, verifiedRole).queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
