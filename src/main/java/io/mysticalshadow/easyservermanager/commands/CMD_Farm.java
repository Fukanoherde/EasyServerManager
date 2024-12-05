package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Farm implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Farm(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("farm").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (plugin.Farminworld != null) {
                p.teleport(Bukkit.getWorld(plugin.Farminworld).getSpawnLocation());
                p.sendMessage(plugin.Prefix + "§3You successfully teleport to the Farmworld");
                return true;
            } else {
                p.sendMessage(plugin.Prefix + "§cCannot find the farmworld. Please contact the server Administration");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}