package io.siedlermc.easysiedlermanager.commands;

import io.siedlermc.easysiedlermanager.EasySiedlerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Teleport implements CommandExecutor {

    private EasySiedlerManager plugin;
    public CMD_Teleport(EasySiedlerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("teleport").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("siedlermanager.teleport") || p.hasPermission("siedlermanager.*")) {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    if (p.getName() != target.getName()) {
                            p.teleport(target.getLocation());
                            p.sendMessage(plugin.Prefix + "§3You teleport to the Player: §2" + target.getName());
                            return true;
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + "§cYou cannot to teleport yourself!");
                    }
                } else {
                    p.sendMessage(plugin.Prefix + "§cPlayer does not exist.");
                }
            } else {
                p.sendMessage(plugin.Prefix + "§cYou don't have permission to use this command!");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}