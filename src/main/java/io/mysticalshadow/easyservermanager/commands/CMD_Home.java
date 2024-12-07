package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CMD_Home implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Home (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("home").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            File file = new File("plugins//EasyServerManager//Players", p.getUniqueId() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            if (args.length == 1) {
                try {
                    config.load(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InvalidConfigurationException e) {
                    throw new RuntimeException(e);
                }
                if (config.isSet(p.getName() + ".Jail.Status")) {
                    if (config.getBoolean(p.getName() + ".Jail.Status", Boolean.valueOf(true))) {
                        String cannotTeleportToHome = plugin.YourJailedHomeMSG;
                        cannotTeleportToHome = cannotTeleportToHome.replace("%home%", args[0]);
                        p.sendMessage(plugin.Prefix + cannotTeleportToHome);
                        return true;
                    } else {
                        if (config.isSet(p.getName() + ".Homes." + args[0])) {
                            String world = config.getString(p.getName() + ".Homes." + args[0] + ".world");
                            double x = config.getDouble(p.getName() + ".Homes." + args[0] + ".x");
                            double y = config.getDouble(p.getName() + ".Homes." + args[0] + ".y");
                            double z = config.getDouble(p.getName() + ".Homes." + args[0] + ".z");
                            float yaw = (float)config.getDouble(p.getName() + ".Homes." + args[0] + ".yaw");
                            float pitch = (float)config.getDouble(p.getName() + ".Homes." + args[0] + ".pitch");
                            Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                            if (plugin.AllowPlayTeleportSound == true) {
                                p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                            }
                            p.teleport(location);
                            String TeleportToHome = plugin.YouTeleportToYourHomeMSG;
                            TeleportToHome = TeleportToHome.replace("%home%", args[0]);
                            p.sendMessage(plugin.Prefix + TeleportToHome);
                            return true;
                        } else {
                            String homeNotExist = plugin.HomeNotExistMSG;
                            homeNotExist = homeNotExist.replace("%home%", args[0]);
                            p.sendMessage(plugin.Prefix + homeNotExist);
                            return true;
                        }
                    }
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "home <homename>");
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (!config.isSet(p.getName() + ".Homes." + args[1])) {
                        String world = p.getWorld().getName();
                        double x = p.getLocation().getX();
                        double y = p.getLocation().getY();
                        double z = p.getLocation().getZ();
                        double yaw = p.getLocation().getYaw();
                        double pitch = p.getLocation().getPitch();
                        config.set(p.getName() + ".Homes." + args[1] + ".world", world);
                        config.set(p.getName() + ".Homes." + args[1] + ".x", x);
                        config.set(p.getName() + ".Homes." + args[1] + ".y", y);
                        config.set(p.getName() + ".Homes." + args[1] + ".z", z);
                        config.set(p.getName() + ".Homes." + args[1] + ".yaw", yaw);
                        config.set(p.getName() + ".Homes." + args[1] + ".pitch", pitch);
                        try {
                            config.save(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        String homeSet = plugin.HomeSetMSG;
                        homeSet = homeSet.replace("%home%", args[1]);
                        p.sendMessage(plugin.Prefix + homeSet);
                        return true;
                    } else {
                        String alreadyHomeExist = plugin.HomeAlreadyExistMSG;
                        alreadyHomeExist = alreadyHomeExist.replace("%home%", args[1]);
                        p.sendMessage(plugin.Prefix + alreadyHomeExist);
                        return true;
                    }
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "home set <homename>");
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("remove")) {
                    if (config.isSet(p.getName() + ".Homes." + args[1])) {
                        try {
                            config.load(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        if (config.getBoolean(p.getName() + ".Homes.")) {
                            config.set(p.getName() + ".Homes", null);
                            try {
                                config.save(file);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            config.set(p.getName() + ".Homes." + args[1], null);
                            config.set(p.getName() + ".Homes." + args[1] + ".world", null);
                            config.set(p.getName() + ".Homes." + args[1] + ".x", null);
                            config.set(p.getName() + ".Homes." + args[1] + ".y", null);
                            config.set(p.getName() + ".Homes." + args[1] + ".z", null);
                            config.set(p.getName() + ".Homes." + args[1] + ".yaw", null);
                            config.set(p.getName() + ".Homes." + args[1] + ".pitch", null);
                            try {
                                config.save(file);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        String homeRemoved = plugin.HomeRemovedMSG;
                        homeRemoved = homeRemoved.replace("%home%", args[1]);
                        p.sendMessage(plugin.Prefix + homeRemoved);
                        return true;
                    } else {
                        String homeNotExist = plugin.HomeNotExistMSG;
                        homeNotExist = homeNotExist.replace("%home%", args[1]);
                        p.sendMessage(plugin.Prefix + homeNotExist);
                        return true;
                    }
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "home remove <homename>");
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
        }
        return false;
    }
}