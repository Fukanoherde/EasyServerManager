package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import io.mysticalshadow.easyservermanager.manager.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CMD_Warp implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Warp (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("warp").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("") || p.hasPermission("")) {
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("set")) {
                        String warpname = args[1];
                        String path = plugin.ServerName + "." + "WarpManager" + "." + warpname + ".";
                        try {
                            WarpManager.config.load(WarpManager.file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        if (!WarpManager.config.isSet(path)) {
                            String world = p.getWorld().getName();
                            double x = p.getLocation().getX();
                            double y = p.getLocation().getY();
                            double z = p.getLocation().getZ();
                            float yaw = p.getLocation().getYaw();
                            float pitch = p.getLocation().getYaw();
                            WarpManager.config.set(path + world, "World");
                            WarpManager.config.set(path + x, "X");
                            WarpManager.config.set(path + y, "Y");
                            WarpManager.config.set(path + z, "Z");
                            WarpManager.config.set(path + yaw, "Yaw");
                            WarpManager.config.set(path + pitch, "Pitch");
                            WarpManager.config.set(path + "SetFrom", p.getUniqueId() + " : " + p.getName());
                            WarpManager.savecfg();
                            p.sendMessage(plugin.Prefix + "§3You successfully set the Warp §2" + warpname + "§3!");
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + "§4The warp §c" + warpname + " §4Already exist!");
                        }
                    }
                } else {
                    p.sendMessage(plugin.Prefix + "§bUse command §8/warp <set> <waprname>");
                }
            } else {
                p.sendMessage(plugin.Prefix + "§cYou don't have permission to use this command!");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}