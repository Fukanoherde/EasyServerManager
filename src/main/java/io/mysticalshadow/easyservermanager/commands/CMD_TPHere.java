package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_TPHere implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_TPHere(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("tphere").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);
            if (p.hasPermission("siedlermanager.tphere") || p.hasPermission("siedlermanager.*")) {
                if (target != null) {
                    if (target.getName() != p.getName()) {
                        target.teleport(p);
                        p.sendMessage(plugin.Prefix + "§3You have successfully teleported the player §2" + target.getDisplayName() + " §3to you");
                        return true;
                    } else {
                        p.sendMessage(plugin.Prefix + "§cYou cannot teleport to yourself!");
                    }
                } else {
                    p.sendMessage(plugin.Prefix + "§cPlayer does not exist");
                }
            } else {
                p.sendMessage(plugin.Prefix + "§cYou do not have permission to use this command");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}