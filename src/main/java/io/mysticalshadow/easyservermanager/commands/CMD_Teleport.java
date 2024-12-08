package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Teleport implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Teleport(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("teleport").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(plugin.PermTeleport) || p.hasPermission(plugin.PermSternchen)) {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    if (p.getName() != target.getName()) {
                            p.teleport(target.getLocation());
                            String teleportToPlayer = plugin.TeleportToThePlayerMSG;
                            teleportToPlayer = teleportToPlayer.replace("%player%", target.getDisplayName());
                            p.sendMessage(plugin.Prefix + teleportToPlayer);
                            return true;
                        }
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