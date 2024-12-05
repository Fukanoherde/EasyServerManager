package io.siedlermc.easysiedlermanager.commands;

import io.siedlermc.easysiedlermanager.EasySiedlerManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Alert implements CommandExecutor {

    private EasySiedlerManager plugin;
    public CMD_Alert(EasySiedlerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("alert").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("siedlermanager.alert") || p.hasPermission("siedlermanager.*")) {
                if (args.length >= 2) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (p.getName() != target.getName()) {
                            String msg = "";
                        for (int i = 1; i < args.length; i++) {
                            msg += args[i] + " ";
                        }
                        target.sendTitle("§3" + msg, "§csend by: §4" + p.getName(), 45, 45, 45);
                        target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_BURP, 5, 4);
                        p.sendMessage(plugin.Prefix + "§3You send the player: §2" + target.getName() + " §3a title message!");
                        return true;
                        } else {
                            p.sendMessage(plugin.Prefix + "§cYou cannot send a alert to youself");
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + "§cPlayer does not exist");
                    }
                } else {
                    p.sendMessage(plugin.Prefix + "§bUse Command §8/alert <player> <message>");
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