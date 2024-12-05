package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import io.mysticalshadow.easyservermanager.manager.JailManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
            if (sender.hasPermission("siedlermanager.jail") || sender.hasPermission("siedlermanager.*")) {
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
                        if (jailConfig.isSet("SiedlerManager" + "." + jailName + ".")) {
                            JailManager.teleportToJail(target, jailName);
                            try {
                                configTarget.load(fileTarget);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (InvalidConfigurationException e) {
                                throw new RuntimeException(e);
                            }
                            if (configTarget.getBoolean(target.getName() + ".Jail.Status") != true) {
                                configTarget.set(target.getName() + ".Jail.Status", true);
                                String world = JailManager.config.getString("SiedlerManager" + "." + jailName + ".Welt");
                                double x = JailManager.config.getDouble("SiedlerManager" + "." + jailName + ".X");
                                double y = JailManager.config.getDouble("SiedlerManager" + "." + jailName + ".Y");
                                double z = JailManager.config.getDouble("SiedlerManager" + "." + jailName + ".Z");
                                float yaw = (float) JailManager.config.getDouble("SiedlerManager" + "." + jailName + ".Yaw");
                                float pitch = (float) JailManager.config.getDouble("SiedlerManager" + "." + jailName + ".Pitch");
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
                                sender.sendMessage(plugin.Prefix + "§3You successfully jailed the player §2" + target.getName());
                                target.sendMessage(plugin.Prefix + "§3You was jailed!");
                                JailManager.teleportToJail(target, jailName);
                                return true;
                            } else {
                                sender.sendMessage(plugin.Prefix + "§4The player §c" + target.getName() + " §4already jailed!");
                            }
                        } else {
                            sender.sendMessage(plugin.Prefix + "§4The jail §c" + jailName + " §4does not exist!");
                        }
                    } else {
                        sender.sendMessage(plugin.Prefix + "§4The player §c" + args[1] + " §4does not exist!");
                    }
                }
            }
        }
        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if (args[0].equalsIgnoreCase("remove")) {
                if (target != null) {
                    if (target != sender) {
                        File fileTarget = new File("plugins//EasySiedlerManager//Players", target.getUniqueId() + ".yml");
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
                                sender.sendMessage(plugin.Prefix + "§3You successfully unjailed the player §2" + target.getName());
                                target.sendMessage(plugin.Prefix + "§3You was unjailed!");
                                target.teleport(Bukkit.getWorld("world").getSpawnLocation());
                                return true;
                            } else {
                                sender.sendMessage(plugin.Prefix + "§4The player §c" + args[0] + " §4is not Jailed!");
                            }
                        } else {
                            sender.sendMessage(plugin.Prefix + "§4The path does not exist!");
                            configTarget.set(target.getName() + ".Jail.Satus", false);
                        }
                    } else {
                        sender.sendMessage(plugin.Prefix + "§4You cannot unjailed yourself!");
                    }
                } else {
                    sender.sendMessage(plugin.Prefix + "§4The player §c" + args[1] + " §4does not exist!");
                }
            }
        }
        return false;
    }
}