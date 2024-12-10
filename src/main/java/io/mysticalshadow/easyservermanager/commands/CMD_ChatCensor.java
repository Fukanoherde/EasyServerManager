package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMD_ChatCensor implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_ChatCensor (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("censor").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("")) {

        } else {
            sender.sendMessage(plugin.Prefix + plugin.NoPermMessage);
        }
        return false;
    }
}