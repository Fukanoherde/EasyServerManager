package io.mysticalshadow.easyservermanager.manager;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MaintenanceManager {

    public static File file = new File(EasyServerManager.getInstance().getDataFolder(), "maintenance.yml");
    public static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

    public static void loadFile () throws IOException {
        String path = "SiedlerManager" + ".";

        Boolean maintenance = Boolean.valueOf(false);

        config.set(path + "Maintenance", maintenance);

        config.options().header("WaveLobby" + " WarpManager");
        config.options().copyHeader(true);

        savecfg();
    }
    public static void savecfg() throws IOException {
        config.save(file);
    }
}