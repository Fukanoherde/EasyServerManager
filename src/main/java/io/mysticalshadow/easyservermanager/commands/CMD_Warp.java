package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import io.mysticalshadow.easyservermanager.manager.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CMD_Warp implements CommandExecutor {

    private EasyServerManager plugin;

    public CMD_Warp(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("warp").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("remove")) {
                        if (p.hasPermission(plugin.PermRemoveWarp) || p.hasPermission(plugin.PermSternchen)) {
                            String warpname = args[1];
                            String path = plugin.ServerName + "." + "WarpManager" + "." + warpname;
                            try {
                                WarpManager.config.load(WarpManager.file);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (InvalidConfigurationException e) {
                                throw new RuntimeException(e);
                            }
                            if (WarpManager.config.isSet(path)) {
                                WarpManager.config.set(path, null);
                                try {
                                    WarpManager.config.save(WarpManager.file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                p.sendMessage(plugin.Prefix + "§3You successfully removed the warp §c" + warpname + "§3!");
                                return true;
                            } else {
                            p.sendMessage(plugin.Prefix + "§4The warp §c" + warpname + " §4does not exist!");
                            }
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                }
            } else {
                p.sendMessage(plugin.Prefix + "§bUse command §8/warp <remove> <waprname>");
            }
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("get")) {
                        if (p.hasPermission(plugin.PermListWarp) || p.hasPermission(plugin.PermSternchen)) {
                        try {
                            WarpManager.config.load(WarpManager.file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        String path = plugin.ServerName + "." + "WarpManager";
                        if (WarpManager.config.isSet(path)) {
                            for (String warps : WarpManager.config.getConfigurationSection(path).getKeys(false)) {
                                String listWarps = warps;
                                listWarps = listWarps.replaceAll(".World", "");
                                listWarps = listWarps.replaceAll(".X", "");
                                listWarps = listWarps.replaceAll(".Z", "");
                                listWarps = listWarps.replaceAll(".Y", "");
                                listWarps = listWarps.replaceAll(".Yaw", "");
                                listWarps = listWarps.replaceAll(".Pitch", "");
                                listWarps = listWarps.replaceAll(".SetFrom", "");
                                p.sendMessage(plugin.Prefix + "§3The Warps on the Server §2" + listWarps);
                            }
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + "§4There are no warps!");
                        }
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                }
            } else {
                p.sendMessage(plugin.Prefix + "§bUse command §8/warp get");
            }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("tp")) {
                        if (p.hasPermission(plugin.PermTPWarp)) {
                        try {
                            WarpManager.config.load(WarpManager.file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        String warpname = args[1];
                        String path = plugin.ServerName + "." + "WarpManager" + "." + warpname;
                        if (WarpManager.config.isSet(path)) {
                            String world = WarpManager.config.getString(path + ".World");
                            double x = WarpManager.config.getDouble(path + ".X");
                            double y = WarpManager.config.getDouble(path + ".Y");
                            double z = WarpManager.config.getDouble(path + ".Z");
                            float yaw = (float) WarpManager.config.getDouble(path + ".Yaw");
                            float pitch = (float) WarpManager.config.getDouble(path + ".Pitch");
                            Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                            p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                            p.teleport(location);
                            p.sendMessage(plugin.Prefix + "§3You successfully teleported to the warp §2" + warpname + "§3!");
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + "§4The warp §c" + warpname + " §4does not exist!");
                        }
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                }
            } else {
                p.sendMessage(plugin.Prefix + "§bUse command §8/warp get");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}