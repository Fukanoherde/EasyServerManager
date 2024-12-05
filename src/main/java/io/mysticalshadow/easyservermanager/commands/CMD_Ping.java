package io.siedlermc.easysiedlermanager.commands;

import io.siedlermc.easysiedlermanager.EasySiedlerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Ping implements CommandExecutor {

    private EasySiedlerManager plugin;

    public CMD_Ping(EasySiedlerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("ping").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (sender instanceof Player) {
            if (args.length == 0) {
                p.sendMessage(plugin.Prefix + "§3You have a ping from §2" + p.getPing() + "§3ms");
                return true;
            }
        }
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (p.hasPermission("siedlermanager.ping") || p.hasPermission("siedlermanager.*")) {
                p.sendMessage(plugin.Prefix + "§3The specified player has a ping of §2" + target.getPing() + "§3ms");
                return true;
            } else {
                p.sendMessage(plugin.Prefix + "§cYou don't have permission to use this command!");
                return true;
            }
        }
        return false;
    }
}