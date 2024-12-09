package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import io.mysticalshadow.easyservermanager.manager.JailManager;
import io.mysticalshadow.easyservermanager.manager.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.io.File;
import java.io.IOException;

public class CMD_Jail implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Jail (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("jail").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);
            String jailName = args[2];
            if (sender.hasPermission(plugin.PermJail) || sender.hasPermission(plugin.PermSternchen)) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (target != null) {
                        File fileTarget = new File("plugins//EasyServerManager//Players", target.getUniqueId() + ".yml");
                        YamlConfiguration configTarget = YamlConfiguration.loadConfiguration(fileTarget);
                        File jailFile = new File(plugin.getDataFolder().getPath(), "jail.yml");
                        YamlConfiguration jailConfig = YamlConfiguration.loadConfiguration(jailFile);
                        try {
                            jailConfig.load(jailFile);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        if (jailConfig.isSet(plugin.ServerName + "." + jailName + ".")) {
                            String world = JailManager.config.getString(plugin.ServerName + "." + jailName + ".Welt");
                            double x = JailManager.config.getDouble(plugin.ServerName + "." + jailName + ".X");
                            double y = JailManager.config.getDouble(plugin.ServerName + "." + jailName + ".Y");
                            double z = JailManager.config.getDouble(plugin.ServerName + "." + jailName + ".Z");
                            float yaw = (float) JailManager.config.getDouble(plugin.ServerName + "." + jailName + ".Yaw");
                            float pitch = (float) JailManager.config.getDouble(plugin.ServerName + "." + jailName + ".Pitch");
                            try {
                                configTarget.load(fileTarget);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (InvalidConfigurationException e) {
                                throw new RuntimeException(e);
                            }
                            if (configTarget.getBoolean(target.getName() + ".Jail.Status") != true) {
                                configTarget.set(target.getName() + ".Jail.Status", true);
                                configTarget.set(target.getName() + ".Jail.Location.Name", jailName);
                                configTarget.set(target.getName() + ".Jail.Location.Welt", world);
                                configTarget.set(target.getName() + ".Jail.Location.X", x);
                                configTarget.set(target.getName() + ".Jail.Location.Y", y);
                                configTarget.set(target.getName() + ".Jail.Location.Z", z);
                                configTarget.set(target.getName() + ".Jail.Location.Yaw", yaw);
                                configTarget.set(target.getName() + ".Jail.Location.Pitch", pitch);
                                try {
                                    configTarget.save(fileTarget);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                String jailedMessageAPlayer = plugin.YouJailedAPlayerMSG;
                                jailedMessageAPlayer = jailedMessageAPlayer.replace("%player%", target.getDisplayName());
                                sender.sendMessage(plugin.Prefix + jailedMessageAPlayer);
                                String jailedMessageFromPlayer = plugin.YouJailedFromPlayer;
                                jailedMessageFromPlayer = jailedMessageFromPlayer.replace("%player%", sender.getName());
                                target.sendMessage(plugin.Prefix + jailedMessageFromPlayer);
                                Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                                target.teleport(location);
                                return true;
                            } else {
                                String alreadyJailed = plugin.PlayerAlreadyJailedMSG;
                                alreadyJailed = alreadyJailed.replace("%player%", target.getDisplayName());
                                sender.sendMessage(plugin.Prefix + alreadyJailed);
                            }
                        } else {
                            String jailNotExist = plugin.JailNotExistMSG;
                            jailNotExist = jailNotExist.replace("%jail%", jailName);
                            target.sendMessage(plugin.Prefix + jailNotExist);
                        }
                    } else {
                        sender.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                    }
                }
            }
        }
        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if (args[0].equalsIgnoreCase("remove")) {
                if (target != null) {
                    if (target != sender) {
                        File fileTarget = new File("plugins//EasyServerManager//Players", target.getUniqueId() + ".yml");
                        YamlConfiguration configTarget = YamlConfiguration.loadConfiguration(fileTarget);
                        try {
                            configTarget.load(fileTarget);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        if (configTarget.isSet(target.getName() + ".Jail.Status")) {
                            if (configTarget.getBoolean(target.getName() + ".Jail.Status") != false) {
                                configTarget.set(target.getName() + ".Jail.Status", false);
                                configTarget.set(target.getName() + ".Jail.Location", null);
                                try {
                                    configTarget.save(fileTarget);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                String unjailedThePlayer = plugin.PlayerUnjailedMSG;
                                unjailedThePlayer = unjailedThePlayer.replace("%player%", target.getDisplayName());
                                sender.sendMessage(plugin.Prefix + unjailedThePlayer);
                                String unjailedFromPlayer = plugin.PlayerUnjailedFromMSG;
                                unjailedFromPlayer = unjailedFromPlayer.replace("%player%", sender.getName());
                                target.sendMessage(plugin.Prefix + unjailedFromPlayer);
                                try {
                                    WarpManager.config.load(WarpManager.file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                } catch (InvalidConfigurationException e) {
                                    throw new RuntimeException(e);
                                }
                                if (WarpManager.config.isSet(plugin.ServerName + ".WarpManager.Spawn")) {
                                    String world = WarpManager.config.getString(plugin.ServerName + ".WarpManager.Spawn.World");
                                    double x = WarpManager.config.getDouble(plugin.ServerName + ".WarpManager.Spawn.X");
                                    double y = WarpManager.config.getDouble(plugin.ServerName + ".WarpManager.Spawn.Y");
                                    double z = WarpManager.config.getDouble(plugin.ServerName + ".WarpManager.Spawn.Z");
                                    float yaw = (float) WarpManager.config.getDouble(plugin.ServerName + ".WarpManager.Spawn.Yaw");
                                    float pitch = (float) WarpManager.config.getDouble(plugin.ServerName + ".WarpManager.Spawn.Pitch");
                                    Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                                    target.teleport(location);
                                    return true;
                                } else {
                                    target.sendMessage(plugin.Prefix + plugin.WarpSpawnNotFoundMSG);
                                    target.teleport(Bukkit.getWorld("world").getSpawnLocation());
                                    return true;
                                }
                            } else {
                                String playerIsNotJailed = plugin.PlayerIsNotJailedMSG;
                                playerIsNotJailed = playerIsNotJailed.replace("%player%", args[0]);
                                sender.sendMessage(plugin.Prefix + playerIsNotJailed);
                            }
                        } else {
                            sender.sendMessage(plugin.Prefix + plugin.ThePathNotFoundMSG);
                            configTarget.set(target.getName() + ".Jail.Satus", false);
                        }
                    } else {
                        sender.sendMessage(plugin.Prefix + plugin.UnjailedYourselfMSG);
                    }
                } else {
                    sender.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                }
            }
        }
        return false;
    }
}