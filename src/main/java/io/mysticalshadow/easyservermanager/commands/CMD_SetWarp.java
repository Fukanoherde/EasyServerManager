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
import java.util.List;

public class CMD_SetWarp implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_SetWarp (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("setwarp").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(plugin.PermSetWarp) || p.hasPermission(plugin.PermSternchen)) {
                if (args.length == 1) {
                    try {
                        WarpManager.config.load(WarpManager.file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                    String warpname = args[0];
                    String path = plugin.ServerName + "." + "WarpManager" + "." + warpname;
                    if (!WarpManager.config.isSet(path)) {
                        List<String> warps = WarpManager.config.getStringList(plugin.ServerName + ".ListWarps");
                        warps.add(args[0]);
                        WarpManager.config.set(plugin.ServerName + ".ListWarps", warps);
                        String world = p.getWorld().getName();
                        double x = p.getLocation().getX();
                        double y = p.getLocation().getY();
                        double z = p.getLocation().getZ();
                        float yaw = p.getLocation().getYaw();
                        float pitch = p.getLocation().getPitch();
                        String setfrom = p.getUniqueId() + " : " + p.getName();
                        WarpManager.config.set(path + ".World", world);
                        WarpManager.config.set(path + ".X", x);
                        WarpManager.config.set(path + ".Y", y);
                        WarpManager.config.set(path + ".Z", z);
                        WarpManager.config.set(path + ".Yaw", yaw);
                        WarpManager.config.set(path + ".Pitch", pitch);
                        WarpManager.config.set(path + ".SetFrom", setfrom);
                        try {
                            WarpManager.config.save(WarpManager.file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        String warpMSG = plugin.SetWarpMSG;
                        warpMSG = warpMSG.replace("%warp%", warpname);
                        p.sendMessage(plugin.Prefix + warpMSG);
                        return true;
                    } else {
                        String alreadyWarp = plugin.WarpAlreadyExistMSG;
                        alreadyWarp = alreadyWarp.replace("%warp%", warpname);
                        p.sendMessage(plugin.Prefix + alreadyWarp);
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "setwarp <WarpName>");
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
            }
        }
        return false;
    }
}