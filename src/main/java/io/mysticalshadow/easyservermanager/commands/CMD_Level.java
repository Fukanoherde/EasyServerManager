package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Level implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Level (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("level").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission(plugin.PermLevel) || sender.hasPermission(plugin.PermSternchen)) {
            if (args.length == 3) {
                Player t = Bukkit.getPlayer(args[1]);
                int level = Integer.parseInt(args[2]);
                if (args[0].equalsIgnoreCase("add")) {
                    if (t != null) {
                        t.setLevel(t.getLevel() + level);
                        String givedPlayerLevel = plugin.AddedLevelMSG;
                        givedPlayerLevel = givedPlayerLevel.replace("%player%", t.getDisplayName());
                        sender.sendMessage(plugin.Prefix + givedPlayerLevel);
                        String givedFromPlayerLevel = plugin.AddedLevelFromPlayerMSG;
                        givedFromPlayerLevel = givedFromPlayerLevel.replace("%player%", sender.getName());
                        t.sendMessage(plugin.Prefix + givedFromPlayerLevel);
                        return true;
                    } else {
                        sender.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                    }
                }
                if (args[0].equalsIgnoreCase("remove")) {
                    if (t != null) {
                        if (t.getLevel() >= level) {
                            t.setLevel(t.getLevel() - level);
                            String removedLevelByPlayer = plugin.RemovedPlayerLevelMSG;
                            removedLevelByPlayer = removedLevelByPlayer.replace("%player%", t.getDisplayName());
                            sender.sendMessage(plugin.Prefix + removedLevelByPlayer);
                            String removedLevelFromPlayer = plugin.RemovedLevelFromPlayerMSG;
                            removedLevelFromPlayer = removedLevelFromPlayer.replace("%player%", sender.getName());
                            t.sendMessage(plugin.Prefix + removedLevelFromPlayer);
                            return true;
                        } else {
                            String enoughPlayerLevel = plugin.EnoughPlayerLevel;
                            enoughPlayerLevel = enoughPlayerLevel.replace("%player%", sender.getName());
                            sender.sendMessage(plugin.Prefix + enoughPlayerLevel);
                        }
                    } else {
                        sender.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                    }
                }
            } else {
                sender.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "level <add, remove> <player> <level>");
            }
        }
        return false;
    }
}