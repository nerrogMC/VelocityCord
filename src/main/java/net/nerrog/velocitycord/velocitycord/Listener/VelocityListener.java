package net.nerrog.velocitycord.velocitycord.Listener;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.nerrog.velocitycord.velocitycord.VelocityCord;

public class VelocityListener {

    @Subscribe(order = PostOrder.NORMAL)
    public void onPlayerChat(PlayerChatEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor(event.getPlayer().getUsername()+"("+event.getPlayer().getCurrentServer().get().getServerInfo().getName()+")"
                , null, "https://cravatar.eu/avatar/"+event.getPlayer().getUsername());
        embed.setTitle(event.getMessage());
        embed.setFooter("Minecraft Ingame Chat(VelocityCord)");

        for (Guild g : VelocityCord.getJDA().getGuilds()){
            TextChannel ch = g.getTextChannelById(VelocityCord.getChannelId());
            if(ch != null){
                ch.sendMessageEmbeds(embed.build()).queue();
            }
        }
    }
}
