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
                if (p.hasPermission("siedlermanager.god") || p.hasPermission("siedlermanager*")) {
                    if (godMode.contains(p)) {
                        godMode.remove(p);
                        p.sendMessage(plugin.Prefix + "§3You are no longer in God mode!");
                        return true;
                    } else {
                        godMode.add(p);
                        p.sendMessage(plugin.Prefix + "§3You are now in God mode!");
                        return true;
                    }
                } else {
                    p.sendMessage(plugin.Prefix + "§cYou do not have permission to use this command.");
                }
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (p.hasPermission("siedlermanager.god.other") || p.hasPermission("siedlermanager.*")) {
                    if (target != null) {
                        if (godMode.contains(target)) {
                            godMode.remove(target);
                            p.sendMessage(plugin.Prefix + "§3You removed the player god mode!");
                            target.sendMessage(plugin.Prefix + "§3You are no longer in God mode!");
                            return true;
                        } else {
                            godMode.add(target);
                            p.sendMessage(plugin.Prefix + "§3You gived the player god mode!");
                            target.sendMessage(plugin.Prefix + "§3You are now in God mode!");
                            return true;
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + "§cPlayer does not exist!");
                    }
                } else {
                    p.sendMessage(plugin.Prefix + "§cYou do not have permission to use this command.");
                }
            }
        }
        return false;
    }
}