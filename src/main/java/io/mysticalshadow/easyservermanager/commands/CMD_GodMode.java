package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CMD_GodMode implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_GodMode (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("god").setExecutor(this);
    }
    public static List<Player> godMode = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (p.hasPermission(plugin.PermGodMode) || p.hasPermission(plugin.PermSternchen)) {
                    if (godMode.contains(p)) {
                        godMode.remove(p);
                        p.sendMessage(plugin.Prefix + plugin.DeactivatedYourGodModeMSG);
                        return true;
                    } else {
                        godMode.add(p);
                        p.sendMessage(plugin.Prefix + plugin.ActivatedYourGodModeMSG);
                        return true;
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                }
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (p.hasPermission(plugin.PermGodModeAnnotherPlayer) || p.hasPermission(plugin.PermSternchen)) {
                    if (target != null) {
                        if (godMode.contains(target)) {
                            godMode.remove(target);
                            String removeTheAnnotherPlayerGodMode = plugin.YouDeactivatedGodAnnotherPlayerMSG;
                            removeTheAnnotherPlayerGodMode = removeTheAnnotherPlayerGodMode.replace("%player%", target.getDisplayName());
                            p.sendMessage(plugin.Prefix + removeTheAnnotherPlayerGodMode);
                            String removeTheFromPlayerGodMode = plugin.YouDeactivatedGodFromPlayerMSG;
                            removeTheFromPlayerGodMode = removeTheFromPlayerGodMode.replace("%player%", p.getDisplayName());
                            target.sendMessage(plugin.Prefix + removeTheFromPlayerGodMode);
                            return true;
                        } else {
                            godMode.add(target);
                            String activatedTheAnnotherPlayerGodMode = plugin.YouActivatedGodAnnotherPlayerMSG;
                            activatedTheAnnotherPlayerGodMode = activatedTheAnnotherPlayerGodMode.replace("%player%", target.getDisplayName());
                            p.sendMessage(plugin.Prefix + activatedTheAnnotherPlayerGodMode);
                            String activatedTheFromPlayerGodMode = plugin.YouActivatedGodFromPlayerMSG;
                            activatedTheFromPlayerGodMode = activatedTheFromPlayerGodMode.replace("%player%", p.getDisplayName());
                            target.sendMessage(plugin.Prefix + activatedTheFromPlayerGodMode);
                            target.sendMessage(plugin.Prefix + activatedTheFromPlayerGodMode);
                            return true;
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                }
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
        }
        return false;
    }
}