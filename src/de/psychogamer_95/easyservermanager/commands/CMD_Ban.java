package de.psychogamer_95.easyservermanager.commands;

import de.psychogamer_95.easyservermanager.Main;
import de.psychogamer_95.easyservermanager.api.BanAPI;
import de.psychogamer_95.easyservermanager.manager.PermManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Ban implements CommandExecutor {

    public Main plugin;
    public CMD_Ban (Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("ban").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("ban")) {
            Player p = (Player) sender;
            if (sender.hasPermission((String) PermManager.getValue("Perms.Ban")) || sender.hasPermission((String)PermManager.getValue("Perms.All"))){
                if(args.length >= 2) {
                    String playername = args[0];
                    if(BanAPI.isBanned(getUUID(playername))) {
                        sender.sendMessage(plugin.Prefix + "§eDieser Spieler ist bereits gebannt!");
                        return true;
                    }
                    String reason = "";
                    for(int i = 1; i < args.length; i++) {
                        reason += args[i] + " ";
                    }
                    BanAPI.ban(getUUID(playername), playername, reason, -1);
                    sender.sendMessage(plugin.Prefix + "§7Du hast §e" + playername + " §4Permanent §7gebannt!");
                    return true;
                }
                sender.sendMessage(plugin.Prefix + "§c/ban <Spieler> <Grund>");
                return true;
            } else {
                sender.sendMessage(plugin.Prefix + plugin.NoPerm);
                return true;
            }
        }
        return true;
    }
    private String getUUID(String playername) {
        return Bukkit.getOfflinePlayer(playername).getUniqueId().toString();
    }
}