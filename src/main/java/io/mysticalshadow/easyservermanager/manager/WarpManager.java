package io.mysticalshadow.easyservermanager.manager;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.swing.plaf.basic.BasicButtonUI;
import java.io.File;
import java.io.IOException;

public class WarpManager {

    private static EasyServerManager plugin;
    public WarpManager (EasyServerManager plugin) {
        this.plugin = plugin;
    }
    public static File file = new File(plugin.getDataFolder().getPath(), "location.yml");
    public static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

    public static void setWarp (Player p, String warpname) {
        String path = plugin.ServerName + "." + "WarpManager" + "." + warpname + ".";
        String world = p.getWorld().getName();
        double x = p.getLocation().getX();
        double y = p.getLocation().getY();
        double z = p.getLocation().getZ();
        float yaw = p.getLocation().getYaw();
        float pitch = p.getLocation().getPitch();
        config.set(path + "World", world);
        config.set(path + "X", x);
        config.set(path + "Y", y);
        config.set(path + "Z", z);
        config.set(path + "Yaw", yaw);
        config.set(path + "Pitch", pitch);
        config.set(path + "SetFrom", p.getUniqueId() + " : " + p.getName());
        config.options().header(plugin.ServerName + "WarpManager");
        config.options().copyHeader(true);
        savecfg();
    }
    public static void telportToWarp (Player p, String warpname) {
        try {
            config.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        String path = plugin.ServerName + "." + "WarpManager" + "." + warpname + ".";
        String world = config.getString(path + "World");
        double x = config.getDouble(path + "X");
        double y = config.getDouble(path + "Y");
        double z = config.getDouble(path + "Z");
        float yaw = (float) config.getDouble(path + "Yaw");
        float pitch = (float) config.getDouble(path + "Pitch");
        Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        p.teleport(location);
    }
    public static void removeWarp (Player p, String warpname) {
        String path = plugin.ServerName + "." + "WarpManager" + "." + warpname + ".";
        try {
            config.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        if (config.isSet(path)) {
            config.set(path + warpname, null);
            config.set(path + warpname, null);
            config.set(path + warpname, null);
            config.set(path + warpname, null);
            config.set(path + warpname, null);
            config.set(path + warpname, null);
            config.set(path + warpname, null);
            config.set(path + warpname, null);
            if (p == null) {
                Bukkit.getConsoleSender().sendMessage(plugin.Prefix + "§4The warp §c" + warpname + " §4has been deleted by §c" + p.getDisplayName() + "§4!");
            } else {
                Bukkit.getConsoleSender().sendMessage(plugin.Prefix + "§4The warp §c" + warpname + " §4has been deleted!");
            }
            savecfg();
        } else {
            p.sendMessage(plugin.Prefix + "§4The warp §c" + warpname + " §4does not exist!");
        }
    }
    public static Boolean isWarpExist(String warpname) {
        try {
            config.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        String path = plugin.ServerName + "." + "WarpManager" + "." + warpname + ".";
        if (config.isSet(path)) {
            return false;
        }
        return true;
    }
    public static void savecfg () {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}