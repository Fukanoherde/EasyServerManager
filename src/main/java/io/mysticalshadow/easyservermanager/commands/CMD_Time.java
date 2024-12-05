package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Time implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Time(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("time").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("easysiedler.time") || p.hasPermission("siedlermanager.*")) {
                if (args[0].equalsIgnoreCase("day")) {
                    if (args.length == 1) {
                        p.getWorld().setTime(1000);
                        p.sendMessage(plugin.Prefix + "§3You have successfully set the time to day");
                        return true;
                    } else {
                        p.sendMessage(plugin.Prefix + "§bUse Command §8/time <day, night, midnight>");
                    }
                } else if (args[0].equalsIgnoreCase("night")) {
                    if (args.length == 1) {
                        p.getWorld().setTime(13000);
                        p.sendMessage(plugin.Prefix + "§3You have successfully set the time to night");
                        return true;
                    } else {
                        p.sendMessage(plugin.Prefix + "§bUse Command §8/time <day, night, midnight>");
                    }
                } else if (args[0].equalsIgnoreCase("midnight")) {
                    if (args.length == 1) {
                        p.getWorld().setTime(20000);
                        p.sendMessage(plugin.Prefix + "§3You have successfully set the time to midnight");
                        return true;
                    } else {
                        p.sendMessage(plugin.Prefix + "§bUse Command §8/time <day, night, midnight>");
                    }
                }
            } else {
                p.sendMessage(plugin.Prefix + "§cYou don't have permission to use this command!");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}