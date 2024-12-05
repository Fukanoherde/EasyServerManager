package io.siedlermc.easysiedlermanager.commands;

import io.siedlermc.easysiedlermanager.EasySiedlerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Vanish implements CommandExecutor {

    private EasySiedlerManager plugin;
    public CMD_Vanish(EasySiedlerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("vanish").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("siedlermanager.vanish") || p.hasPermission("siedlermanager.*")) {
                if (args.length == 0) {
                    if (plugin.vanish.contains(p)) {
                        plugin.vanish.remove(p);
                        plugin.fly.remove(p);
                        p.setAllowFlight(false);
                        p.setFlying(false);
                        p.sendMessage("§3All players can now see you");
                        for (Player ps : Bukkit.getOnlinePlayers()) {
                            ps.showPlayer(p);
                        }
                    } else {
                        plugin.vanish.add(p);
                        plugin.fly.add(p);
                        p.setAllowFlight(true);
                        p.setFlying(true);
                        p.sendMessage(plugin.Prefix + "§3All players cannot now see you");
                        for (Player ps : Bukkit.getOnlinePlayers()) {
                            if (ps.hasPermission("siedlermanager.vanish.see") || p.hasPermission("siedlermanager.*")) {
                                ps.showPlayer(p);
                                ps.sendMessage(plugin.Prefix + "§3The player §2" + p.getDisplayName() + " §3is now in Vanish mode!");
                            } else {
                                ps.hidePlayer(p);
                            }
                        }
                    }
                }
                return true;
            } else {
                p.sendMessage(plugin.Prefix + "§cYou don't have permission to use this command!");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}