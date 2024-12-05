package io.siedlermc.easysiedlermanager.listener;

import io.siedlermc.easysiedlermanager.EasySiedlerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

public class InventoryManager implements Listener {

    private EasySiedlerManager plugin;
    public InventoryManager(EasySiedlerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (plugin.vanish.contains(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }
    @EventHandler
    public void onInventoryPickup(InventoryPickupItemEvent e) {
        Player p = (Player) e.getHandlers();
        if (plugin.vanish.contains(p)) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }
    @EventHandler
    public void onMoveItem (InventoryMoveItemEvent e) {
        Player p = (Player) e.getHandlers();
        if (p.getInventory().equals("§6✪ §3Server Settings §6✪")) {
            if (!e.isCancelled()) {
                e.setCancelled(true);
            } else {
                return;
            }
        } else {
            return;
        }
    }
}