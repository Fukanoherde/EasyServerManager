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

public class CMD_RemoveWarp implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_RemoveWarp (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("warpremove").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(plugin.PermRemoveWarp) || p.hasPermission(plugin.PermSternchen)) {
                if (args.length == 1) {
                    try {
                        WarpManager.config.load(WarpManager.file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                    String path = plugin.ServerName + "." + "WarpManager" + "." + args[0];
                    if (WarpManager.config.isSet(path)) {
                        List<String> warps = WarpManager.config.getStringList(plugin.ServerName + ".ListWarps");
                        warps.remove(args[0]);
                        WarpManager.config.set(plugin.ServerName + ".ListWarps", warps);
                        if (warps.isEmpty()) {
                            WarpManager.config.set(plugin.ServerName + ".WarpManager", null);
                        }
                        WarpManager.config.set(path, null);
                        try {
                            WarpManager.config.save(WarpManager.file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        String warpRemove = plugin.RemoveWarpMSG;
                        warpRemove = warpRemove.replace("%warp%", args[0]);
                        p.sendMessage(plugin.Prefix + warpRemove);
                        return true;
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.WarpDoesNotExistMSG);
                    }
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