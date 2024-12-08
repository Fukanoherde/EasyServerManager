package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import io.mysticalshadow.easyservermanager.manager.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CMD_Spawn implements CommandExecutor {
    private EasyServerManager plugin;
    public CMD_Spawn(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("spawn").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                try {
                    WarpManager.config.load(WarpManager.file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InvalidConfigurationException e) {
                    throw new RuntimeException(e);
                }
                if (WarpManager.config.isSet(plugin.ServerName + ".WarpManager.Spawn")) {
                    String world = WarpManager.config.getString(plugin.ServerName + ".WarpManager.Spawn.World");
                    double x = WarpManager.config.getDouble(plugin.ServerName + ".WarpManager.Spawn.X");
                    double y = WarpManager.config.getDouble(plugin.ServerName + ".WarpManager.Spawn.Y");
                    double z = WarpManager.config.getDouble(plugin.ServerName + ".WarpManager.Spawn.Z");
                    float yaw = (float) WarpManager.config.getDouble(plugin.ServerName + ".WarpManager.Spawn.Yaw");
                    float pitch = (float) WarpManager.config.getDouble(plugin.ServerName + ".WarpManager.Spawn.Pitch");
                    Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                    if (plugin.AllowPlayTeleportSound == true) {
                        p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    }
                    p.teleport(location);
                    p.sendMessage(plugin.Prefix + plugin.TeleportToSpawnMSG);
                    return true;
                } else {
                    p.sendMessage(plugin.Prefix + plugin.WarpSpawnNotFoundMSG);
                    return true;
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "spawn");
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
        }
        return false;
    }
}