package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_SetWarp implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_SetWarp (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("setwarp").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(plugin.PermRemoveWarp) || p.hasPermission(plugin.PermSternchen)) {
                
            }
        }
        return false;
    }
}