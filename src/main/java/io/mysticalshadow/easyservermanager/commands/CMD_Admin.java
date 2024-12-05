package io.siedlermc.easysiedlermanager.commands;

import io.siedlermc.easysiedlermanager.api.ItemAPI;
import io.siedlermc.easysiedlermanager.EasySiedlerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CMD_Admin implements CommandExecutor {

    private EasySiedlerManager plugin;
    public CMD_Admin(EasySiedlerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("admin").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("siedlermanager.command.admin") || p.hasPermission("siedlermanager.*")) {
                if (args.length == 0) {
                    Inventory admin = Bukkit.createInventory(null, 54, "§6✪ §3Server Settings §6✪");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                
                            }
                            admin.setItem(15, ItemAPI.createItemNoEnch(Material.ACACIA_SIGN, 1, 1, "§6✪ §3Gamerule §6✪"));
                            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 4);
                        }
                    }, 2L);
                    p.openInventory(admin);
                } else {
                    p.sendMessage(plugin.Prefix + "§bUse command §8/admin");
                }
            } else {
                p.sendMessage(plugin.Prefix + "§cYou don't have permission to use this command");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}