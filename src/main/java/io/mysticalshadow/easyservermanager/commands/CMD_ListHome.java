package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CMD_ListHome implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_ListHome(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("listhomes").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            File filePerPlayer = new File("plugins//EasyServerManager//Players", p.getUniqueId() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(filePerPlayer);
            if (args.length == 0) {
                    try {
                        config.load(filePerPlayer);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                     if (config.isSet(p.getName() + ".Homes.")) {
                         if (!config.getBoolean(p.getName() + ".Homes.")) {
                             for (String homes : config.getConfigurationSection(p.getName() + ".Homes").getKeys(false)) {
                                 String listHomes = homes;
                                 listHomes = listHomes.replaceAll(".world", "");
                                 listHomes = listHomes.replaceAll(".x", "");
                                 listHomes = listHomes.replaceAll(".z", "");
                                 listHomes = listHomes.replaceAll(".y", "");
                                 listHomes = listHomes.replaceAll(".yaw", "");
                                 listHomes = listHomes.replaceAll(".pitch", "");
                                 p.sendMessage(plugin.Prefix + "§3Your Homes: §2" + listHomes);
                             }
                             return true;
                         } else {
                             p.sendMessage(plugin.Prefix + "§4You don't have any homes!");
                             return true;
                         }
                     } else {
                         p.sendMessage(plugin.Prefix + "§4You don't have any homes!");
                         return true;
                     }
                }
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                File fileTarget = new File("plugins//EasyServerManager//Players", target.getUniqueId() + ".yml");
                YamlConfiguration configTarget = YamlConfiguration.loadConfiguration(fileTarget);
                if (p.hasPermission("siedlermanager.gethomes.other") || p.hasPermission("siedlermanager.*")) {
                        if (fileTarget.exists()) {
                            try {
                                config.load(filePerPlayer);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (InvalidConfigurationException e) {
                                throw new RuntimeException(e);
                            }
                            if (configTarget.getString(p.getName() + ".Homes") != null) {
                                for (String homes : configTarget.getConfigurationSection(target.getName() + ".Homes").getKeys(false)) {
                                    String listHomes = homes;
                                    listHomes = listHomes.replaceAll(".world", "");
                                    listHomes = listHomes.replaceAll(".x", "");
                                    listHomes = listHomes.replaceAll(".z", "");
                                    listHomes = listHomes.replaceAll(".y", "");
                                    listHomes = listHomes.replaceAll(".yaw", "");
                                    listHomes = listHomes.replaceAll(".pitch", "");
                                    p.sendMessage(plugin.Prefix + "§3The Homes from the Player §2" + target.getName() + " §3Homes: §2" + listHomes);
                                }
                                return true;
                            } else {
                                p.sendMessage(plugin.Prefix + "§4The player §c" + target.getName() + " Has not any Homes");
                                return true;
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + "§4The Player has not exist a File!");
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + "§cYou don't have permission to see annother player homes!");
                    }
                } else {
                    p.sendMessage(plugin.Prefix + "§4The player §c" + args[0] + " §4does not exist!");
                }
            }
        } else {
                sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}