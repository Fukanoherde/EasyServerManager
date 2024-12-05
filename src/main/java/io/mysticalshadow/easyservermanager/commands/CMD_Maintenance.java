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
        if (sender.hasPermission("siedlermanager.maintenance") || sender.hasPermission("siedlermanager.*")) {
            String path = "SiedlerManager" + ".";
            if (args[0].equalsIgnoreCase("on")) {
                MaintenanceManager.config.set(path + "Maintenance", Boolean.valueOf(true));
                try {
                    MaintenanceManager.savecfg();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.hasPermission("siedlermanager.maintenance.activate") || all.hasPermission("siedlermanager.*")) {
                        return true;
                    } else {
                        all.kickPlayer("§3SiedlerMC\n§aDu wurdest gekickt\n§4GRUND: §cWartungsarbeiten\n§3gekickt worden vom: §4System");
                    }
                }
                sender.sendMessage(plugin.Prefix + "§2You successfully activated the maintenance mode!");
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
                sender.sendMessage(plugin.Prefix + "§4You successfully deactivated the maintenance mode!");
                return true;
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§cYou don't have permission to use this command!");
        }
        return false;
    }
}