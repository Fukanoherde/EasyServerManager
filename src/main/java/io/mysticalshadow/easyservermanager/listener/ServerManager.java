package io.mysticalshadow.easyservermanager.listener;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import io.mysticalshadow.easyservermanager.manager.MaintenanceManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerManager implements Listener {

    private EasyServerManager plugin;
    public ServerManager(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onMOTD (ServerListPingEvent e) {
        String path = plugin.ServerName + ".";
        if (MaintenanceManager.config.getBoolean(path + "Maintenance", true)) {
            e.setMotd(plugin.MOTDHeader + "\n" + plugin.MOTDMaintenance);
        } else {
            e.setMotd(plugin.MOTDHeader + "\n" + plugin.MOTDFooter);
        }
        e.setMaxPlayers(plugin.MaxPlayer);
    }
}