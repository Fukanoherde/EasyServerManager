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
                                 p.sendMessage(plugin.Prefix + plugin.YourHomesMSG + listHomes);
                             }
                             return true;
                         } else {
                             p.sendMessage(plugin.Prefix + plugin.YourHaveNoHomesMSG);
                             return true;
                         }
                     } else {
                         p.sendMessage(plugin.Prefix + plugin.YourHaveNoHomesMSG);
                         return true;
                     }
                }
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                File fileTarget = new File("plugins//EasyServerManager//Players", target.getUniqueId() + ".yml");
                YamlConfiguration configTarget = YamlConfiguration.loadConfiguration(fileTarget);
                if (p.hasPermission(plugin.PermGetOtherHomes) || p.hasPermission(plugin.PermSternchen)) {
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
                                    String playerHomes = plugin.AnnotherPlayerHomes;
                                    playerHomes = playerHomes.replace("%player%", target.getDisplayName());
                                    p.sendMessage(plugin.Prefix + playerHomes + listHomes);
                                }
                                return true;
                            } else {
                                String playerHaveNoHomes = plugin.AnnotherPlayerHaveNoHomesMSG;
                                playerHaveNoHomes = playerHaveNoHomes.replace("%player%", target.getDisplayName());
                                p.sendMessage(plugin.Prefix + playerHaveNoHomes);
                                return true;
                            }
                        } else {
                            String playerDoesExistFile = plugin.PlayerExistNotAFileMSG;
                            playerDoesExistFile = playerDoesExistFile.replace("%player%", target.getDisplayName());
                            p.sendMessage(plugin.Prefix + playerDoesExistFile);
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                }
            }
        } else {
                sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
        }
        return false;
    }
}