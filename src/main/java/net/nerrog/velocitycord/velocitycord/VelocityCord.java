package net.nerrog.velocitycord.velocitycord;

import com.google.inject.Inject;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import jdk.nashorn.internal.scripts.JD;
import net.dv8tion.jda.api.AccountType;
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
import java.nio.file.Paths;

@Plugin(
        id = "velocitycord",
        name = "VelocityCord",
        version = "1.1-SNAPSHOT",
        authors = {"nerrog"}
)
public class VelocityCord {

    private static Logger logger;
    private static ProxyServer proxyServer;
    private static JDA jda;
    private static config config;

    @Inject
    public VelocityCord(ProxyServer proxyServer, Logger logger){
        VelocityCord.proxyServer = proxyServer;
        VelocityCord.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) throws Exception {
        //Initialize Discord
        config = ConfigLoader.LoadConfig();
        if (config == null){
            logger.error("ConfigFile Load Error!");
            throw new Exception();
        }

        jda = InitializeJDA(config.BotToken, "Minecraft");

        //add Proxy Listener
        proxyServer.getEventManager().register(this, new VelocityListener());
    }


    public static Logger getLogger() {
        return logger;
    }
    public static String getChannelId(){
        return config.ChannelId;
    }
    public static JDA getJDA(){
        return jda;
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
