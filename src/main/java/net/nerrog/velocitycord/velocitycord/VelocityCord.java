package net.nerrog.velocitycord.velocitycord;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.nerrog.velocitycord.velocitycord.Listener.DiscordListener;
import net.nerrog.velocitycord.velocitycord.Listener.VelocityListener;
import net.nerrog.velocitycord.velocitycord.yaml.ConfigLoader;
import net.nerrog.velocitycord.velocitycord.yaml.config;
import org.slf4j.Logger;

import javax.security.auth.login.LoginException;

@Plugin(
        id = "velocitycord",
        name = "VelocityCord",
        version = "1.2-SNAPSHOT",
        authors = {"nerrog"}
)
public class VelocityCord {

    public static Logger logger;
    public static ProxyServer proxyServer;
    public static JDA jda;
    public static config config;

    @Inject
    public VelocityCord(ProxyServer proxyServer, Logger logger){
        VelocityCord.proxyServer = proxyServer;
        VelocityCord.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) throws Exception {
        //LoadConfig
        config = ConfigLoader.LoadConfig();
        if (config == null){
            logger.error("ConfigFile Load Error!");
            throw new Exception();
        }

        //Initialize Discord
        jda = InitializeJDA(config.BotToken, config.PlayingStatus);

        //add Proxy Listener
        proxyServer.getEventManager().register(this, new VelocityListener());
    }


    public static void inGameBroadcast(String message){
        TextComponent textComponent = Component.text(message);
        proxyServer.sendMessage(textComponent);
    }

    private JDA InitializeJDA(String token, String Status) throws LoginException {
        return JDABuilder
                .createDefault(token)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.playing(Status))
                .addEventListeners(new DiscordListener())
                .build();

    }
}
