package io.mysticalshadow.easyservermanager.commands;

import com.sun.tools.javac.Main;
import io.mysticalshadow.easyservermanager.EasyServerManager;
import io.mysticalshadow.easyservermanager.manager.MaintenanceManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

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
                    List<String> whitelistedPlayer = MaintenanceManager.config.getStringList(plugin.ServerName + ".Join.AllowList");
                    if (whitelistedPlayer.contains(all.getName())) {
                        return true;
                    }
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
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                if (sender.hasPermission("")) {
                    String target = args[1];
                    try {
                        MaintenanceManager.config.load(MaintenanceManager.file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                    List<String> whitelistedPlayer = MaintenanceManager.config.getStringList(plugin.ServerName + ".Join.AllowList");
                    if (!whitelistedPlayer.remove(target)) {
                        whitelistedPlayer.add(target);
                        MaintenanceManager.config.set(plugin.ServerName + ".Join.AllowList", whitelistedPlayer);
                        try {
                            MaintenanceManager.config.save(MaintenanceManager.file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        sender.sendMessage(plugin.Prefix + "§2You successfully added the player §3" + target + " §2to the Maintenance whitelist!");
                        return true;
                    } else {
                        sender.sendMessage(plugin.Prefix + "§4The player §c" + target + " §4is already whitelisted!");
                        return true;
                    }
                } else {
                    sender.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                    return true;
                }
            }
        } else if (args[0].equalsIgnoreCase("add")) {
            sender.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "maintenance add <player>");
            return true;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("remove")) {
                if (sender.hasPermission("")) {
                    try {
                        MaintenanceManager.config.load(MaintenanceManager.file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                    List<String> whitelistedPlayer = MaintenanceManager.config.getStringList(plugin.ServerName + ".Join.AllowList");
                    if (whitelistedPlayer.contains(args[1])) {
                        whitelistedPlayer.remove(args[1]);
                        MaintenanceManager.config.set(plugin.ServerName + ".Join.AllowList", whitelistedPlayer);
                        try {
                            MaintenanceManager.config.save(MaintenanceManager.file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        sender.sendMessage(plugin.Prefix + "§2You successfully removed the player §3" + args[1] + " §2From the join allow list!");
                        return true;
                    } else {
                        sender.sendMessage(plugin.Prefix + "§4The player §c" + args[1] + " §4is not in the join allow list!");
                        return true;
                    }
                } else {
                    sender.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                sender.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "maintenance remove <player>");
                return true;
            }
        }
        return false;
    }
}