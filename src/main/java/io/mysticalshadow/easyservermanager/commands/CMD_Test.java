package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMD_Test implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Test (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("test").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("ERFOLGREICH!!!!");
        }
        return false;
    }
}