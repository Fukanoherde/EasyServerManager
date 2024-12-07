package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Heal implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Heal(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("heal").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(plugin.PermHeal) || p.hasPermission(plugin.PermSternchen)) {
                if (args.length == 0) {
                    p.setHealth(20);
                    p.setFoodLevel(20);
                    p.sendMessage(plugin.Prefix + plugin.YourHealedMSG);
                    return true;
                } else {
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "heal");
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (p.hasPermission(plugin.PermHealAnnotherPlayer) || p.hasPermission(plugin.PermSternchen)) {
                if (target != null) {
                    if (target.getName() != p.getName()) {
                        if (args.length == 1) {
                            target.setHealth(20);
                            target.setFoodLevel(20);
                            String healAnnotherPlayer = plugin.YourHealedAnnotherPlayer;
                            healAnnotherPlayer = healAnnotherPlayer.replace("%player%", target.getDisplayName());
                            String healFromPlayer = plugin.YourHealedFromPlayer;
                            healFromPlayer = healFromPlayer.replace("%player%", p.getDisplayName());
                            target.sendMessage(plugin.Prefix + healFromPlayer);
                            p.sendMessage(plugin.Prefix + healAnnotherPlayer);
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "heal <player>");
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "heal");
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