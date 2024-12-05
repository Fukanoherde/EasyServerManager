package io.siedlermc.easysiedlermanager.commands;

import io.siedlermc.easysiedlermanager.EasySiedlerManager;
import io.siedlermc.easysiedlermanager.manager.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CMD_PvP implements CommandExecutor {
    private EasySiedlerManager plugin;
    public CMD_PvP (EasySiedlerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("pvp").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            File file = new File("plugins//EasySiedlerManager//Players", p.getUniqueId() + ".yml");
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
                            p.sendMessage(plugin.Prefix + "§4You deactivated your PVP status");
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + "§4You already deactivated the PvP!");
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + "§4Path does not exist!");
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
                            p.sendMessage(plugin.Prefix + "§2You activated your PVP status");
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + "§4You already activated the PvP!");
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + "§4Path does not exist!");
                        config.set(p.getName() + ".PvP.Activated", false);
                        try {
                            config.save(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } else {
                p.sendMessage(plugin.Prefix + "§bUse command §8/pvp <on, off>");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
    return false;
    }
}