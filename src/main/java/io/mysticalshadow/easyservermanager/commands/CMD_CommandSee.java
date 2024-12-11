package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_CommandSee implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_CommandSee (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("log").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(plugin.PermsEnableSeeCommands) || p.hasPermission(plugin.PermSternchen)) {
                if (args.length == 0) {
                    if (EasyServerManager.seeCommands.contains(p)) {
                        EasyServerManager.seeCommands.remove(p);
                        p.sendMessage(plugin.Prefix + plugin.YouCannotSeeCommandsMSG);
                        return true;
                    } else {
                        EasyServerManager.seeCommands.add(p);
                        p.sendMessage(plugin.Prefix + plugin.YouCanNowSeeCommandsMSG);
                        return true;
                    }
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