package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CMD_ChatCensor implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_ChatCensor (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("censor").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission(plugin.PermCensor) || sender.hasPermission(plugin.PermSternchen)) {
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("add")) {
                    List<String> words = plugin.getConfig().getStringList("deniedWords");
                    if (!words.contains(args[1])) {
                        words.add(args[1]);
                        plugin.getConfig().set("deniedWords", words);
                        plugin.saveConfig();
                        sender.sendMessage(plugin.Prefix + "§2You added the word §3" + args[1] + " §2to the blocked list!");
                        return true;
                    } else {
                        sender.sendMessage(plugin.Prefix + "§4This words is already blocked!");
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    List<String> words = plugin.getConfig().getStringList("deniedWords");
                    if (words.contains(args[1])) {
                        words.remove(args[1]);
                        plugin.getConfig().set("deniedWords", words);
                        plugin.saveConfig();
                        sender.sendMessage(plugin.Prefix + "§2You removed the word §3" + args[1] + " §2to the blocked list!");
                        return true;
                    } else {
                        sender.sendMessage(plugin.Prefix + "§4This words is not blocked!");
                    }
                }
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.NoPermMessage);
        }
        return false;
    }
}