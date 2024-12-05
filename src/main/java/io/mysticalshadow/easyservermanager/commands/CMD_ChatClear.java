package io.siedlermc.easysiedlermanager.commands;

import io.siedlermc.easysiedlermanager.EasySiedlerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_ChatClear implements CommandExecutor {

    private EasySiedlerManager plugin;
    public CMD_ChatClear(EasySiedlerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("chatclear").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (p.hasPermission("easysiedler.chatclear") || p.hasPermission("siedlermanager.*")) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        for (int i = 0; i <= 1000; i++) {
                            all.sendMessage(" ");
                        }
                        all.sendMessage(plugin.Prefix + "§3The chat cleared by: §2" + p.getDisplayName());
                    }
                    p.sendMessage(plugin.Prefix + "§3The chat Successfully Cleared");
                    return true;
                } else {
                    p.sendMessage(plugin.Prefix + "§cYou don't have permission to use this command");
                }
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}
