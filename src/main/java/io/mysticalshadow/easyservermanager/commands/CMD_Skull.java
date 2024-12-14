package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import io.mysticalshadow.easyservermanager.api.ItemAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CMD_Skull implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Skull (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("skull").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("")) {
                if (args.length == 3) {
                    String target = args[1];
                    int amount = Integer.parseInt(args[2]);
                    ItemStack playerHead = ItemAPI.createPlayerHead(target, target, amount, 0, "§2" + target);
                    p.getInventory().addItem(playerHead);
                    String givePlayerHead = plugin.YouGiveThePlayerHeadMSG;
                    givePlayerHead = givePlayerHead.replace("%player%", target);
                    p.sendMessage(plugin.Prefix + givePlayerHead);
                    return true;
                } else {
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "skull <player> <amount>");
                    return true;
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                return true;
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
            return true;
        }
    }
}