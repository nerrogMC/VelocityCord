package net.nerrog.velocitycord.velocitycord.Listener;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.nerrog.velocitycord.velocitycord.VelocityCord;
import org.jetbrains.annotations.NotNull;

public class DiscordListener implements EventListener {
    //Discord用のイベントリスナー
    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if(event instanceof ReadyEvent){
            //Ready
            VelocityCord.getLogger().info("Discord Login Successful!");
        }else if (event instanceof MessageReceivedEvent){
            if (((MessageReceivedEvent) event).getMessage().getChannel().getId().equals(VelocityCord.getChannelId())){
                if (!((MessageReceivedEvent) event).getAuthor().isBot()){
                    String Image = "";
                    for (Message.Attachment a : ((MessageReceivedEvent) event).getMessage().getAttachments()){
                        if(a != null){
                            Image = "ImageUrl[ "+ a.getUrl() + " ]";
                        }
                    }
                    VelocityCord.inGameBroadcast(
                            "<"+((MessageReceivedEvent) event).getAuthor().getName()+"(§3Discord§r)"+"> "+
                                    ((MessageReceivedEvent) event).getMessage().getContentRaw()+
                                    Image
                    );
                }
            }
        }
    }
}
