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
        //チャットからEmbedを作成してDiscordに転送
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor(event.getPlayer().getUsername()+"("+event.getPlayer().getCurrentServer().get().getServerInfo().getName()+")"
                , null, "https://api.tydiumcraft.net/skin?uuid="+event.getPlayer().getUniqueId()+"&type=avatar");
        embed.setTitle(event.getMessage());
        embed.setFooter("Minecraft Ingame Chat(VelocityCord)");

        for (Guild g : VelocityCord.jda.getGuilds()){
            TextChannel ch = g.getTextChannelById(VelocityCord.config.ChannelId);
            if(ch != null){
                ch.sendMessageEmbeds(embed.build()).queue();
            }
        }
    }
}
