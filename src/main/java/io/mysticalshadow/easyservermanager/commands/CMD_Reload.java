package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMD_Reload implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Reload (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("easyservermanager").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission(plugin.PermReload) || sender.hasPermission(plugin.PermSternchen)) {
            if (args.length == 0) {
                Bukkit.getServer().getPluginManager().disablePlugin(plugin);
                Bukkit.getServer().getPluginManager().enablePlugin(plugin);
                sender.sendMessage(plugin.Prefix + "§2All configs successfully reloaded!");
                return true;
            } else {
                sender.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "easyservermanager");
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.NoPermMessage);
        }
        return false;
    }
}