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

public class CMD_ListWarps implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_ListWarps (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("listwarps").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(plugin.PermListWarp) || p.hasPermission(plugin.PermSternchen)) {
                if (args.length == 0) {
                    try {
                        WarpManager.config.load(WarpManager.file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                    if (WarpManager.config.isSet(plugin.ServerName + ".WarpManager")) {
                        for (String warps : WarpManager.config.getConfigurationSection(plugin.ServerName + ".WarpManager").getKeys(false)) {
                            String listWarps = warps;
                            listWarps = listWarps.replaceAll(".world", "");
                            listWarps = listWarps.replaceAll(".x", "");
                            listWarps = listWarps.replaceAll(".z", "");
                            listWarps = listWarps.replaceAll(".y", "");
                            listWarps = listWarps.replaceAll(".yaw", "");
                            listWarps = listWarps.replaceAll(".pitch", "");
                            p.sendMessage(plugin.Prefix + plugin.WarpListMSG + listWarps);
                        }
                        return true;
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.WarpsNotExistOnServerMSG);
                        return true;
                    }
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                return true;
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
            return true;
        }
        return false;
    }
}