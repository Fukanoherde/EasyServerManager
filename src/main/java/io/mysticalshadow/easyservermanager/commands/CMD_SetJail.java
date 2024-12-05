package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import io.mysticalshadow.easyservermanager.manager.JailManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CMD_SetJail implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_SetJail (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("setjail").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("siedlermanager.setjail") || p.hasPermission("siedlermanager.*")) {
                if (args.length == 1) {
                    String jailname = args[0];
                    String path = "SiedlerManager" + "." + jailname + ".";

                    String welt = p.getWorld().getName();
                    double x = p.getLocation().getX();
                    double y = p.getLocation().getY();
                    double z = p.getLocation().getZ();
                    float yaw = p.getLocation().getYaw();
                    float pitch = p.getLocation().getPitch();

                    JailManager.config.set(path + "Welt", welt);
                    JailManager.config.set(path + "X", Double.valueOf(x));
                    JailManager.config.set(path + "Y", Double.valueOf(y));
                    JailManager.config.set(path + "Z", Double.valueOf(z));
                    JailManager.config.set(path + "Yaw", Float.valueOf(yaw));
                    JailManager.config.set(path + "Pitch", Float.valueOf(pitch));
                    JailManager.config.set(path + "SetFrom", p.getUniqueId() + " : " + p.getName());

                    try {
                        JailManager.savecfg();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    p.sendMessage(plugin.Prefix + "§3You successfully created the Jail!");
                    return true;
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