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
            if (p.hasPermission(plugin.PermTPWarp) || p.hasPermission(plugin.PermSternchen)) {
                if (args.length == 1) {
                    String warpname = args[0];
                    try {
                        WarpManager.config.load(WarpManager.file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                    String path = plugin.ServerName + "." + "WarpManager" + "." + warpname;
                    if (WarpManager.config.isSet(path)) {
                        String world = WarpManager.config.getString(path + ".World");
                        double x = WarpManager.config.getDouble(path + ".X");
                        double y = WarpManager.config.getDouble(path + ".Y");
                        double z = WarpManager.config.getDouble(path + ".Z");
                        float yaw = (float) WarpManager.config.getDouble(path + ".Yaw");
                        float pitch = (float) WarpManager.config.getDouble(path + ".Pitch");
                        Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                        if (plugin.AllowPlayTeleportSound == true) {
                            p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                        }
                        p.teleport(location);
                        String teleportWarp = plugin.TeleportToWarpMSG;
                        teleportWarp = teleportWarp.replace("%warp%", warpname);
                        p.sendMessage(plugin.Prefix + teleportWarp);
                        return true;
                    } else {
                        String warpNotExist = plugin.WarpDoesNotExistMSG;
                        warpNotExist = warpNotExist.replace("%warp%", warpname);
                        p.sendMessage(plugin.Prefix + warpNotExist);
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "warp <warpname>");
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
        }
        return false;
    }
}