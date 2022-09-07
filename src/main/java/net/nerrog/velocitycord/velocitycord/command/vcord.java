package net.nerrog.velocitycord.velocitycord.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.nerrog.velocitycord.velocitycord.VelocityCord;


@CommandAlias("vcord|velocitycord")
@CommandPermission("velocitycord")
public class vcord extends BaseCommand {

    @Default
    @HelpCommand
    public static void onHelp(CommandSource source){
        source.sendMessage(Component.text("Usage: /vcord toggle OF/OFF"));
    }

    //Toggle用Enum
    private enum ToggleType{
        ON,
        OFF
    }

    private static ToggleType checkMute(Player player){
        if(VelocityCord.mutePlayers.contains(player)){
            return ToggleType.ON;
        }else {
            return ToggleType.OFF;
        }
    }


    @Subcommand("toggle")
    public static void toggleOn(CommandSource source, ToggleType type){
        Player p = (Player) source;

        if (type.equals(ToggleType.ON)){
            //ON
            if (checkMute(p) == ToggleType.OFF){
                //ミュートではない場合
                VelocityCord.mutePlayers.add(p);
                source.sendMessage(Component.text("Muted!").color(NamedTextColor.RED));
            }else {
                //既にミュートだった場合
                source.sendMessage(Component.text("Already muted!"));
            }
        }else if (type.equals(ToggleType.OFF)){
            //OFF
            if (checkMute(p) == ToggleType.ON){
                //ミュートされていた場合
                VelocityCord.mutePlayers.remove(p);
                source.sendMessage(Component.text("Unmuted!").color(NamedTextColor.GREEN));
            }else {
                //ミュートされてない場合
                source.sendMessage(Component.text("You are not muted!"));
            }
        }
    }
}
