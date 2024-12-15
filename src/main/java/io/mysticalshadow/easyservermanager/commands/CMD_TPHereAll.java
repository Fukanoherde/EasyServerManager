package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_TPHereAll implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_TPHereAll (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("tpall").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (p.hasPermission(plugin.PermsTPAll) || p.hasPermission(plugin.PermSternchen)) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.teleport(p);
                        all.sendMessage(plugin.Prefix + "§2The player §3" + p.getDisplayName() + " §2Teleported you to him.");
                    }
                    p.sendMessage(plugin.Prefix + "§2You teleported successfully all player to you!");
                    return true;
                } else {
                    p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                    return true;
                }
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
            return true;
        }
        return false;
    }
}