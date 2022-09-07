package net.nerrog.velocitycord.velocitycord;

import co.aikar.commands.VelocityCommandManager;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.nerrog.velocitycord.velocitycord.Listener.DiscordListener;
import net.nerrog.velocitycord.velocitycord.Listener.VelocityListener;
import net.nerrog.velocitycord.velocitycord.command.vcord;
import net.nerrog.velocitycord.velocitycord.data.ConfigLoader;
import net.nerrog.velocitycord.velocitycord.data.config;
import org.slf4j.Logger;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

@Plugin(
        id = "velocitycord",
        name = "VelocityCord",
        version = "1.3-SNAPSHOT",
        authors = {"nerrog"}
)
public class VelocityCord {

    public static Logger logger;
    public static ProxyServer proxyServer;
    public static JDA jda;
    public static config config;
    public static VelocityCommandManager manager;
    public static List<Player> mutePlayers = new ArrayList<Player>();


    //Main
    @Inject
    public VelocityCord(ProxyServer proxyServer, Logger logger){
        VelocityCord.proxyServer = proxyServer;
        VelocityCord.logger = logger;
    }

    //初期化
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

        //Register Commands
        manager = new VelocityCommandManager(proxyServer, this);
        manager.registerCommand(new vcord());

        logger.info("Loaded VelocityCord!");
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event){
        if (jda != null){
            jda.shutdown();
        }
    }


    //ブロードキャスト用メソッド
    public static void inGameBroadcast(String message){
        TextComponent textComponent = Component.text(message);
        proxyServer.sendMessage(textComponent);
    }

    //JDA初期化用メソッド
    private JDA InitializeJDA(String token, String Status) throws LoginException {
        return JDABuilder
                .createDefault(token)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.playing(Status))
                .addEventListeners(new DiscordListener())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();

    }
}
