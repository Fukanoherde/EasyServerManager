package io.siedlermc.easysiedlermanager.manager;

import io.siedlermc.easysiedlermanager.EasySiedlerManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private EasySiedlerManager plugin;
    public FileManager (EasySiedlerManager plugin) {
        this.plugin = plugin;
    }
    public static File getFile () {
        return new File("plugins//EasySiedlerManager//Rewards", "config.yml");
    }
    public static void setDefault() {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getFile());
        cfg.options().copyDefaults(true);
        cfg.addDefault("broadcast", true);
        List<String> commands = new ArrayList<String>();
        commands.add("give %player% minecraft:diamond 10");
        commands.add("give %player% minecraft:experience_bottle 32");
        cfg.addDefault("rewardCommands", commands);
        try {
            cfg.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadConfig() {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getFile());
        EasySiedlerManager.getInstance().broadcast = cfg.getBoolean("broadcast");
    }
}