package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Level implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Level (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("level").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("siedlermanager.level") || sender.hasPermission("siedlermanager.*")) {
            if (args.length == 3) {
                Player t = Bukkit.getPlayer(args[1]);
                int level = Integer.parseInt(args[2]);
                if (args[0].equalsIgnoreCase("add")) {
                    if (t != null) {
                        t.setLevel(t.getLevel() + level);
                        sender.sendMessage(plugin.Prefix + "§3You have the player §2" + t.getName() + " §3added §2" + level + " §3Level!");
                        t.sendMessage(plugin.Prefix + "§3You become §2" + level + " §3Level from the §2" + sender.getName());
                        return true;
                    } else {
                        sender.sendMessage(plugin.Prefix + "§4The player §c" + t.getName() + " §4does not exist!");
                    }
                }
                if (args[0].equalsIgnoreCase("remove")) {
                    if (t != null) {
                        if (t.getLevel() >= level) {
                            t.setLevel(t.getLevel() - level);
                            sender.sendMessage(plugin.Prefix + "§4You have the player §c" + t.getName() + " §4Removed §c" + level + " §4Level!");
                            t.sendMessage(plugin.Prefix + "§3You removed §2" + level + " §3Level from the §2" + sender.getName());
                            return true;
                        } else {
                            sender.sendMessage(plugin.Prefix + "§4The player §c" + t.getName() + " §4has not enough level!");
                        }
                    } else {
                        sender.sendMessage(plugin.Prefix + "§4The player §c" + t.getName() + " §4does not exist!");
                    }
                }
            } else {
                sender.sendMessage(plugin.Prefix + "§bUse command §8/level <add, remove> <player> <level>");
            }
        }
        return false;
    }
}