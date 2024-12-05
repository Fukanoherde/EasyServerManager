package io.mysticalshadow.easyservermanager.manager;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class JailManager {

    public static File file = new File(EasyServerManager.getInstance().getDataFolder(), "jail.yml");
    public static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

    public static void setJailPoint(Player p, String jailname) throws IOException {
        String path = "SiedlerManager" + "." + jailname + ".";

        String welt = p.getWorld().getName();
        double x = p.getLocation().getX();
        double y = p.getLocation().getY();
        double z = p.getLocation().getZ();
        float yaw = p.getLocation().getYaw();
        float pitch = p.getLocation().getPitch();

        config.set(path + "Welt", welt);
        config.set(path + "X", Double.valueOf(x));
        config.set(path + "Y", Double.valueOf(y));
        config.set(path + "Z", Double.valueOf(z));
        config.set(path + "Yaw", Float.valueOf(yaw));
        config.set(path + "Pitch", Float.valueOf(pitch));
        config.set(path + "SetFrom", p.getUniqueId() + " : " + p.getName());

        config.options().header("SiedlerManager" + " JailManager");
        config.options().copyHeader(true);

        savecfg();
    }
    public static Location teleportToJail(Player player, String jailname) {
        String path = "SiedlerManager" + "." + jailname + ".";
        String world = config.getString(path + "Welt");
        double x = config.getDouble(path + "X");
        double y = config.getDouble(path + "Y");
        double z = config.getDouble(path + "Z");
        float yaw = (float)config.getDouble(path + "Yaw");
        float pitch = (float)config.getDouble(path + "Pitch");
        Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        player.teleport(location);
        return location;
    }
    public static void savecfg() throws IOException {
        config.save(file);
    }
}