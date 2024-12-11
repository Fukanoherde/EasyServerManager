package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CMD_Burn implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Burn (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("burn").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("")) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {

                    } else {
                        p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                    }
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
            }
        }
        return false;
    }
}