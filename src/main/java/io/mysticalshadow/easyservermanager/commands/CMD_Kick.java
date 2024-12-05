package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Kick implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Kick(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("kick").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("siedlermanager.kick") || sender.hasPermission("siedlermanager.*")) {
            if (args.length >= 2) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != sender) {
                    if (target != null) {
                        String msg = "";
                        for (int i = 1; i < args.length; i++) {
                            msg += args[i] + " ";
                        }
                        sender.sendMessage(plugin.Prefix + "§eDu hast den Spieler §a" + target.getDisplayName() + " §eerfolgreich gekickt!");
                        target.kickPlayer("SiedlerMC" + "\n\n"
                                + "§bDu wurdest vom Server gekickt!\n§cGrund: §4" + msg + "\n\n§9gekickt worden von: §7" + sender.getName());
                        return true;
                    } else {
                        sender.sendMessage(plugin.Prefix + "§cPlayer does not exist!");
                    }
                } else {
                    sender.sendMessage(plugin.Prefix + "§cWhy do you kick yourself?!");
                }
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§cYou don't have permission to use this command!");
        }
        return false;
    }
}