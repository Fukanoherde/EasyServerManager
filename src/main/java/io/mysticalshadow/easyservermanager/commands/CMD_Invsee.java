package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Invsee implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Invsee(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("invsee").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("siedlermanager.invsee") || p.hasPermission("siedlermanager.*")) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (target.getName() != p.getName()) {
                            p.openInventory(target.getInventory());
                            p.sendMessage(plugin.Prefix + "§3You can n ow see the player's inventory");
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + "§cWhy do see yourself inventory");
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + "§cPlayer does not exist.");
                    }
                } else {
                    p.sendMessage(plugin.Prefix + "§bUse command §8/invsee <Player>");
                }
            } else {
                p.sendMessage(plugin.Prefix + "§cYou don't have permission to use this command");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}