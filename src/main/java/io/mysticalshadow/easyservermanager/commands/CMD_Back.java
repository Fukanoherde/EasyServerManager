package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CMD_Back implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Back (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("back").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            File file = new File("plugins//EasyServerManager//Players", p.getUniqueId() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            try {
                config.load(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
            if (config.isSet(p.getName() + ".Death.")) {
                String world = (String) config.get(p.getName() + ".Death.World");
                double x = config.getDouble(p.getName() + ".Death.X");
                double y = config.getDouble(p.getName() + ".Death.Y");
                double z = config.getDouble(p.getName() + ".Death.Z");
                Location location = new Location(Bukkit.getWorld(world), x, y, z);
                p.teleport(location);
                config.set(p.getName() + ".Death", null);
                try {
                    config.save(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                p.sendMessage(plugin.Prefix + plugin.TeleportToDeathMSG);
                return true;
            } else {
                p.sendMessage(plugin.Prefix + plugin.DeathNotFoundMSG);
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
        }
        return false;
    }
}