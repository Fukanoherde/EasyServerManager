package io.mysticalshadow.easyservermanager.manager;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {

    public static File file = new File("plugins//EasyServerManager", "config.yml");
    public static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

    public static void setValue (String path, String value) {
        config.set("EasyServerManager.Settings.AllowJoinMessage", true);
        config.set("EasyServerManager.Settings.AllowQuitMessage", true);
    }
    public static void getValue (String path, String value) {

    }
    public static boolean valueIsSet (String path, String value) {
        return true;
    }
}