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
            if (p.hasPermission("siedlermanager.fly") || p.hasPermission("siedlermanager.*")) {
                if (args.length == 0) {
                    if (plugin.fly.contains(p)) {
                        plugin.fly.remove(p);
                        p.setAllowFlight(false);
                        p.setFlying(false);
                        p.sendMessage(plugin.Prefix + "§cYou cannot flying!");
                        return true;
                    } else {
                        plugin.fly.add(p);
                        p.setAllowFlight(true);
                        p.setFlying(true);
                        p.sendMessage(plugin.Prefix + "§3You can now flying");
                        return true;
                    }
                } else {
                    p.sendMessage(plugin.Prefix + "§bUse command §8/fly");
                }
            } else {
                p.sendMessage(plugin.Prefix + "§cYou don't have permission to use this command");
            }
            if (p.hasPermission("siedlermanager.fly.other") || p.hasPermission("siedlermanager.*")) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (plugin.fly.contains(target)) {
                            plugin.fly.remove(target);
                            target.setAllowFlight(false);
                            target.setFlying(false);
                            p.sendMessage(plugin.Prefix + "§cThe player §4" + target.getName() + " §ccanot flying");
                            target.sendMessage(plugin.Prefix + "§cYou cannot flying");
                            return true;
                        } else {
                            plugin.fly.add(target);
                            target.setAllowFlight(true);
                            target.setFlying(true);
                            p.sendMessage(plugin.Prefix + "§3The player §2" + target.getName() + " §3now flying");
                            target.sendMessage(plugin.Prefix + "§3You can now flying");
                            return true;
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + "§cPlayer does not exist!");
                    }
                }
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}