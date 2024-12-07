package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Clear implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Clear(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("clear").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(plugin.PermClear) || p.hasPermission(plugin.PermSternchen)) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.getInventory().clear();
                        String clearSender = plugin.PlayerClearMSG;
                        clearSender = clearSender.replace("%player%", target.getDisplayName());
                        p.sendMessage(plugin.Prefix + clearSender);
                        String targetPlayer = plugin.AnotherPlayerClearMSG;
                        targetPlayer = targetPlayer.replace("%player%", p.getDisplayName());
                        target.sendMessage(plugin.Prefix + targetPlayer);
                        return true;
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "clear <player>");
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