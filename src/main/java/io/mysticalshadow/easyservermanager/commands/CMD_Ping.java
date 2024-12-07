package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Ping implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Ping(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("ping").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (sender instanceof Player) {
            if (args.length == 0) {
                p.sendMessage(plugin.Prefix + plugin.PingYourselfMSG + p.getPing() + plugin.PingInMSMSG);
                return true;
            }
        }
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (p.hasPermission(plugin.PermAnnotherPing) || p.hasPermission(plugin.PermSternchen)) {
                p.sendMessage(plugin.Prefix + plugin.AnnotherPlayerPingMSG + target.getPing() + plugin.PingInMSMSG);
                return true;
            } else {
                p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                return true;
            }
        }
        return false;
    }
}