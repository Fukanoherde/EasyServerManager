package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMD_CommandSee implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_CommandSee (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("log").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}