package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Warn implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Warn (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("warn").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 2) {
            Player p = (Player) sender;
            if (sender.hasPermission("")) {
                
            } else {
                sender.sendMessage(plugin.Prefix + plugin.NoPermMessage);
            }
        }
        return false;
    }
}