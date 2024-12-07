package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Kick implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Kick(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("kick").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission(plugin.PermKick) || sender.hasPermission(plugin.PermSternchen)) {
            if (args.length >= 2) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != sender) {
                    if (target != null) {
                        String msg = "";
                        for (int i = 1; i < args.length; i++) {
                            msg += args[i] + " ";
                        }
                        String successKick = plugin.SuccessfullyKickPlayerMSG;
                        successKick = successKick.replace("%player%", target.getDisplayName());
                        sender.sendMessage(plugin.Prefix + successKick);
                        String kickReason = plugin.KickMSG;
                        kickReason = kickReason.replace("%reason%", msg);
                        String kickSender = plugin.KickPlayer;
                        kickSender = kickSender.replace("%player%", sender.getName());
                        target.kickPlayer(plugin.ServerName + kickReason + kickSender);
                        return true;
                    } else {
                        sender.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                    }
                } else {
                    sender.sendMessage(plugin.Prefix + plugin.KickYourself);
                }
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.NoPermMessage);
        }
        return false;
    }
}