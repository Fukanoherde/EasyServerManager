package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Fly implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Fly (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("fly").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(plugin.PermFly) || p.hasPermission(plugin.PermSternchen)) {
                if (args.length == 0) {
                    if (plugin.fly.contains(p)) {
                        plugin.fly.remove(p);
                        p.setAllowFlight(false);
                        p.setFlying(false);
                        p.sendMessage(plugin.Prefix + plugin.DeactivatedFlyMSG);
                        return true;
                    } else {
                        plugin.fly.add(p);
                        p.setAllowFlight(true);
                        p.setFlying(true);
                        p.sendMessage(plugin.Prefix + plugin.ActivatedFlyMSG);
                        return true;
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "fly");
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
            }
            if (p.hasPermission(plugin.PermAnnotherFly) || p.hasPermission(plugin.PermSternchen)) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (plugin.fly.contains(target)) {
                            plugin.fly.remove(target);
                            target.setAllowFlight(false);
                            target.setFlying(false);
                            String deactivatedFlyByPlayer = plugin.DeactivatedFlyByPlayerMSG;
                            deactivatedFlyByPlayer = deactivatedFlyByPlayer.replace("%player%", target.getDisplayName());
                            p.sendMessage(plugin.Prefix + deactivatedFlyByPlayer);
                            String deactivatedFlyFromPlayer = plugin.DeactivatedFlyFromMSG;
                            deactivatedFlyFromPlayer = deactivatedFlyFromPlayer.replace("%player%", p.getDisplayName());
                            target.sendMessage(plugin.Prefix + deactivatedFlyFromPlayer);
                            p.sendMessage(plugin.Prefix + deactivatedFlyByPlayer);
                            target.sendMessage(plugin.Prefix + deactivatedFlyFromPlayer);
                            return true;
                        } else {
                            plugin.fly.add(target);
                            target.setAllowFlight(true);
                            target.setFlying(true);
                            String activatedFlyByPlayer = plugin.ActivatedFlyByPlayerMSG;
                            activatedFlyByPlayer = activatedFlyByPlayer.replace("%player%", target.getDisplayName());
                            p.sendMessage(plugin.Prefix + activatedFlyByPlayer);
                            String activatedFlyFromPlayer = plugin.ActivatedFlyFromMSG;
                            activatedFlyFromPlayer = activatedFlyFromPlayer.replace("%player%", p.getDisplayName());
                            target.sendMessage(plugin.Prefix + activatedFlyFromPlayer);
                            return true;
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