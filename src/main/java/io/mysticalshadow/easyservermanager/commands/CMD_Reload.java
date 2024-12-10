package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import io.mysticalshadow.easyservermanager.manager.JailManager;
import io.mysticalshadow.easyservermanager.manager.MaintenanceManager;
import io.mysticalshadow.easyservermanager.manager.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CMD_Reload implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Reload (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("easyservermanager").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("") || sender.hasPermission(plugin.PermSternchen)) {
            if (args.length == 0) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all != null) {
                        File filePlayer = new File("plugins//EasyServerManager//Players" + all.getUniqueId());
                        YamlConfiguration configPlayer = YamlConfiguration.loadConfiguration(filePlayer);
                        if (!filePlayer.exists()) {
                            sender.sendMessage(plugin.Prefix + "§4The player file does not exist!");
                        } else {
                            try {
                                configPlayer.load(filePlayer);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (InvalidConfigurationException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                File fileCensor = new File(plugin.getDataFolder(), "censor.yml");
                YamlConfiguration configCensor = YamlConfiguration.loadConfiguration(fileCensor);
                if (!fileCensor.exists()) {
                    try {
                        configCensor.save(fileCensor);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        configCensor.load(fileCensor);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (!new File(plugin.getDataFolder(), "config.yml").exists()) {
                    plugin.saveResource("config.yml", true);
                } else {
                    try {
                        plugin.getConfig().load("config.yml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (!MaintenanceManager.file.exists()) {
                    try {
                        MaintenanceManager.file.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        MaintenanceManager.loadFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (!JailManager.file.exists()) {
                    try {
                        JailManager.file.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        JailManager.config.load(JailManager.file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (!WarpManager.file.exists()) {
                    try {
                        WarpManager.file.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        WarpManager.config.load(WarpManager.file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                }
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