package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import io.mysticalshadow.easyservermanager.manager.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CMD_PvP implements CommandExecutor {
    private EasyServerManager plugin;
    public CMD_PvP (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("pvp").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            File file = new File("plugins//EasyServerManager//Players", p.getUniqueId() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("off")) {
                    try {
                        config.load(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                    if (config.isSet(p.getName() + ".PvP.Activated")) {
                        if (config.getBoolean(p.getName() + ".PvP.Activated", Boolean.valueOf(true))) {
                            config.set(p.getName() + ".PvP.Activated", false);
                            try {
                                config.save(file);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                ScoreboardManager.setBoard(all);
                                ScoreboardManager.updateBoard(all);
                            }
                            p.sendMessage(plugin.Prefix + plugin.DeactivatedYourselfPvPMSG);
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.AlreadyDeactivatedYourselfPvPMSG);
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.ThePathNotFoundMSG);
                        config.set(p.getName() + ".PvP.Activated", false);
                        try {
                            config.save(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("on")) {
                    try {
                        config.load(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                    if (config.isSet(p.getName() + ".PvP.Activated")) {
                        if (config.getBoolean(p.getName() + ".PvP.Activated") != true) {
                            config.set(p.getName() + ".PvP.Activated", true);
                            try {
                                config.save(file);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                ScoreboardManager.setBoard(all);
                                ScoreboardManager.updateBoard(all);
                            }
                            p.sendMessage(plugin.Prefix + plugin.ActivatedYourselfPvPMSG);
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.AlreadyActivatedYourselfPvPMSG);
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.ThePathNotFoundMSG);
                        config.set(p.getName() + ".PvP.Activated", false);
                        try {
                            config.save(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "pvp <on, off>");
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
        }
    return false;
    }
}