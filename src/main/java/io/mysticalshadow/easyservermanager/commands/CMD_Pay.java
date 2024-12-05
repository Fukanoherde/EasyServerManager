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

public class CMD_Pay implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Pay (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("pay").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            File file = new File("plugins//EasySiedlerManager//Players", p.getUniqueId() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            if (args.length == 2) {
                Player t = Bukkit.getPlayer(args[0]);
                int level = Integer.parseInt(args[1]);
                File fileTarget = new File("plugins//EasySiedlerManager//Players", t.getUniqueId() + ".yml");
                YamlConfiguration configTarget = YamlConfiguration.loadConfiguration(fileTarget);
                if (t != null) {
                if (config.isSet(p.getName() + "." + "Level")) {
                    if (configTarget.isSet(t.getName() + "." + "Level")) {
                        try {
                            config.load(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            configTarget.load(fileTarget);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        if (config.getInt(p.getName() + "." + "Level") >= level) {
                            if (t != p) {
                                    config.set(p.getName() + "." + "Level", config.getInt(p.getName() + "." + "Level") - level);
                                    configTarget.set(t.getName() + "." + "Level", configTarget.getInt(t.getName() + "." + "Level") + level);
                                    try {
                                        config.save(file);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    try {
                                        configTarget.save(fileTarget);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    ScoreboardManager.setBoard(p);
                                    ScoreboardManager.updateBoard(p);
                                    ScoreboardManager.setBoard(t);
                                    ScoreboardManager.updateBoard(t);
                                    p.sendMessage(plugin.Prefix + "§3You have successfully transferred the specified amount to the player!");
                                    t.sendMessage(plugin.Prefix + "§3The player §2" + p.getName() + " §3has you §2" + level + " §3Level send");
                                    return true;
                                } else {
                                    p.sendMessage(plugin.Prefix + "§4You cannot payed level yourself!");
                                }
                            } else {
                                p.sendMessage(plugin.Prefix + "§cYou don't have enough level");
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + "§4The file from §c" + t.getName() + " §4does not exist!");
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + "§4Your level file does not exist!");
                    }
                } else {
                    p.sendMessage(plugin.Prefix + "§4Player does not exist!");
                }
            } else {
                p.sendMessage(plugin.Prefix + "§bUse command §8/pay <player> <level>");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}