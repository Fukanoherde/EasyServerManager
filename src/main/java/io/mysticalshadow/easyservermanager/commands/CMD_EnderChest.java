package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_EnderChest implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_EnderChest (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("enderchest").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(plugin.PermEnderchest) || p.hasPermission(plugin.PermSternchen)) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (target != p) {
                            p.openInventory(target.getEnderChest());
                            p.sendMessage(plugin.Prefix + plugin.OpenEnderchestMSG);
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.OpenYourselfEnderchestMSG);
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
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