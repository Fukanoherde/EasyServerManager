package io.siedlermc.easysiedlermanager.listener;

import io.siedlermc.easysiedlermanager.EasySiedlerManager;
import io.siedlermc.easysiedlermanager.manager.MaintenanceManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerManager implements Listener {

    private EasySiedlerManager plugin;
    public ServerManager(EasySiedlerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onMOTD (ServerListPingEvent e) {
        String path = "SiedlerManager" + ".";
        if (MaintenanceManager.config.getBoolean(path + "Maintenance", true)) {
            e.setMotd(plugin.MOTDHeader + "\n" + plugin.MOTDMaintenance);
        } else {
            e.setMotd(plugin.MOTDHeader + "\n" + plugin.MOTDFooter);
        }
        e.setMaxPlayers(plugin.MaxPlayer);
    }
}