package io.siedlermc.easysiedlermanager.commands;

import io.siedlermc.easysiedlermanager.EasySiedlerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Heal implements CommandExecutor {

    private EasySiedlerManager plugin;
    public CMD_Heal(EasySiedlerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("heal").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("siedlermanager.heal") || p.hasPermission("siedlermanager.*")) {
                if (args.length == 0) {
                    p.setHealth(20);
                    p.setFoodLevel(20);
                    p.sendMessage(plugin.Prefix + "§3You successfully healed!");
                    return true;
                } else {
                    p.sendMessage(plugin.Prefix + "§bUse command §8/heal");
                }
            } else {
                p.sendMessage(plugin.Prefix + "§cYou don't have permission to use this command!");
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (p.hasPermission("siedlermanager.heal.other") || p.hasPermission("siedlermanager.*")) {
                if (target != null) {
                    if (target.getName() != p.getName()) {
                        if (args.length == 1) {
                            target.setHealth(20);
                            target.setFoodLevel(20);
                            target.sendMessage(plugin.Prefix + "§3You healed from §2" + p.getDisplayName());
                            p.sendMessage(plugin.Prefix + "§3You healed the Player §2" + target.getDisplayName());
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + "§bUse comamnd §8/heal <player>");
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + "§bUse command §8/heal");
                    }
                } else {
                    p.sendMessage(plugin.Prefix + "§cPlayer does not exist!");
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