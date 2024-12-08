package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_TPHere implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_TPHere(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("tphere").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);
            if (p.hasPermission(plugin.PermTPHERE) || p.hasPermission(plugin.PermSternchen)) {
                if (target != null) {
                    if (target.getName() != p.getName()) {
                        target.teleport(p);
                        String tphereMessage = plugin.TpherePlayerMSG;
                        tphereMessage = tphereMessage.replace("%player%", target.getDisplayName());
                        p.sendMessage(plugin.Prefix + tphereMessage);
                        return true;
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.TeleportYourselfMSG);
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
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