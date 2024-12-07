package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_ChatClear implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_ChatClear(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("chatclear").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (p.hasPermission(plugin.PermChatClear) || p.hasPermission(plugin.PermSternchen)) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        for (int i = 0; i <= 1000; i++) {
                            all.sendMessage(" ");
                        }
                        String clearer = plugin.AllChatClearMSG;
                        clearer = clearer.replace("%player%", p.getDisplayName());
                        all.sendMessage(plugin.Prefix + clearer);
                    }
                    p.sendMessage(plugin.Prefix + plugin.SuccessfullyChatClearedMSG);
                    return true;
                } else {
                    p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                }
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
        }
        return false;
    }
}
