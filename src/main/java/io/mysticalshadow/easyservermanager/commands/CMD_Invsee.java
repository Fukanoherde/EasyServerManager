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
            if (p.hasPermission(plugin.PermInvsee) || p.hasPermission(plugin.PermSternchen)) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (target.getName() != p.getName()) {
                            p.openInventory(target.getInventory());
                            p.sendMessage(plugin.Prefix + plugin.InvseeMSG);
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.InvseeYourselfMSG);
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "invsee <Player>");
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
        }
        return false;
    }
}