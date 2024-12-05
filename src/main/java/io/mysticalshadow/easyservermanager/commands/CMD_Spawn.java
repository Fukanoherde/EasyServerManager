package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Spawn implements CommandExecutor {
    private EasyServerManager plugin;
    public CMD_Spawn(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("spawn").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                p.teleport(Bukkit.getWorld("world").getSpawnLocation());
                p.sendMessage(plugin.Prefix + "§3You successfully teleport to world");
                return true;
            } else {
                p.sendMessage(plugin.Prefix + "§bUse command §8/spawn");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}