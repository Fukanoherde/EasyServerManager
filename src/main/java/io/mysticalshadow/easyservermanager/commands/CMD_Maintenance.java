package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import io.mysticalshadow.easyservermanager.manager.MaintenanceManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CMD_Maintenance implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Maintenance (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("maintenance").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission(plugin.PermMaintenanceActivate) || sender.hasPermission(plugin.PermSternchen)) {
            String path = plugin.ServerName + ".";
            if (args[0].equalsIgnoreCase("on")) {
                MaintenanceManager.config.set(path + "Maintenance", Boolean.valueOf(true));
                sender.sendMessage(plugin.Prefix + plugin.ActivateMaintenanceMode);
                try {
                    MaintenanceManager.savecfg();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.hasPermission(plugin.PermMaintenanceJoin) || all.hasPermission(plugin.PermSternchen)) {
                        return true;
                    } else {
                        all.kickPlayer(plugin.KickPlayerWhenActivateMaintenance);
                    }
                }
                return true;
            } else if (args[0].equalsIgnoreCase("off")) {
                try {
                    MaintenanceManager.loadFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                MaintenanceManager.config.set(path + "Maintenance", Boolean.valueOf(false));
                try {
                    MaintenanceManager.savecfg();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sender.sendMessage(plugin.Prefix + plugin.DeactivateMaintenanceModeMSG);
                return true;
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.NoPermMessage);
        }
        return false;
    }
}