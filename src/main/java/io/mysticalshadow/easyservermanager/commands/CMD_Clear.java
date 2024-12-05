package io.siedlermc.easysiedlermanager.commands;

import io.siedlermc.easysiedlermanager.EasySiedlerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Clear implements CommandExecutor {

    private EasySiedlerManager plugin;
    public CMD_Clear(EasySiedlerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("clear").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("siedlermanager.clear") || p.hasPermission("siedlermanager.*")) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.getInventory().clear();
                        p.sendMessage(plugin.Prefix + "§3You cleared the Inventory from: §2" + target.getName());
                        target.sendMessage(plugin.Prefix + "§3Your inventory has been cleared.");
                        return true;
                    } else {
                        p.sendMessage(plugin.Prefix + "§cPlayer doas not exist");
                    }
                } else {
                    p.sendMessage(plugin.Prefix + "§bUse command §8/clear <player>");
                }
            } else {
                p.sendMessage(plugin.Prefix + "§cDo you not have permission to use this command");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}