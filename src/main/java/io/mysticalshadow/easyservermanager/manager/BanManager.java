package io.mysticalshadow.easyservermanager.manager;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class BanManager {

    public static File file = new File(EasyServerManager.instance.getDataFolder(), "ban.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);


}