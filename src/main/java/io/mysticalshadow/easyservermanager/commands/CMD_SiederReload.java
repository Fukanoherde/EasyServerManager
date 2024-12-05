package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMD_SiederReload implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_SiederReload (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("siedler").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}