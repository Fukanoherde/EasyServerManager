package io.mysticalshadow.easyservermanager.manager;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class WarpManager {

    private static EasyServerManager plugin;
    public WarpManager (EasyServerManager plugin) {
        this.plugin = plugin;
    }
    public static File file = new File(plugin.getDataFolder().getPath(), "location.yml");
    public static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
}