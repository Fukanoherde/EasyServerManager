package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMD_Skull implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Skull (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("skull").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return false;
    }
}