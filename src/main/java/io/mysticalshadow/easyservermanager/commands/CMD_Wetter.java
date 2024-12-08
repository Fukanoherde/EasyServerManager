package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Wetter implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Wetter(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("weather").setExecutor(this);
    }

        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission(plugin.PermWeather) || p.hasPermission(plugin.PermSternchen)) {
                    if (args.length == 1) {
                        String wetterTyp = args[0].toLowerCase();
                        switch (wetterTyp) {
                            case "sun":
                                Bukkit.getWorld(p.getLocation().getWorld().getName()).setStorm(false);
                                p.sendMessage(plugin.Prefix + plugin.WeatherChangedSunMSG);
                                break;
                            case "rain":
                                Bukkit.getWorld(p.getLocation().getWorld().getName()).setStorm(true);
                                p.sendMessage(plugin.Prefix + plugin.WeatherChangedRainMSG);
                                break;
                            case "thunder":
                                Bukkit.getWorld(p.getLocation().getWorld().getName()).setStorm(true);
                                Bukkit.getWorld(p.getLocation().getWorld().getName()).setThundering(true);
                                p.sendMessage(plugin.Prefix + plugin.WeatherChangedThunderMSG);
                                break;
                            default:
                                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "weather <sun, rain, thunder>");
                                return false;
                        }
                        return true;
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "weather <sun, rain, thunder>");
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                }
            } else {
                sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
            }
            return false;
        }
}