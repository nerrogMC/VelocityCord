package net.nerrog.velocitycord.velocitycord.Listener;

import emoji4j.EmojiUtils;
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
            VelocityCord.logger.info("Discord Login Successful!");
        }else if (event instanceof MessageReceivedEvent){
            //メッセージを受信したとき
            if (((MessageReceivedEvent) event).getMessage().getChannel().getId().equals(VelocityCord.config.ChannelId)){
                if (!((MessageReceivedEvent) event).getAuthor().isBot()){
                    //添付ファイルのurl
                    String Image = "";
                    for (Message.Attachment a : ((MessageReceivedEvent) event).getMessage().getAttachments()){
                        if(a != null){
                            Image = " Files[ "+ a.getUrl() + " ]";
                        }
                    }
                    //絵文字が豆腐化するのを防ぐ
                    String MessageContent = EmojiUtils.shortCodify(((MessageReceivedEvent) event).getMessage().getContentRaw());
                    //ゲーム内にブロードキャスト
                    VelocityCord.inGameBroadcast(
                            "<"+((MessageReceivedEvent) event).getAuthor().getName()+"(§3Discord§r)"+"> "+
                                    MessageContent+ Image
                    );
                }
            }
        }
    }
}
