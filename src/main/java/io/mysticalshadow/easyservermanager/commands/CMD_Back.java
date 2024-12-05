package io.siedlermc.easysiedlermanager.commands;

import io.siedlermc.easysiedlermanager.EasySiedlerManager;
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

    private EasySiedlerManager plugin;
    public CMD_Back (EasySiedlerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("back").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            File file = new File("plugins//EasySiedlerManager//Players", p.getUniqueId() + ".yml");
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
                Double x = config.getDouble(p.getName() + ".Death.X");
                Double y = config.getDouble(p.getName() + ".Death.Y");
                Double z = config.getDouble(p.getName() + ".Death.Z");
                Location location = new Location(Bukkit.getWorld(world), x, y, z);
                p.teleport(location);
                config.set(p.getName() + ".Death", null);
                try {
                    config.save(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                p.sendMessage(plugin.Prefix + "§3You successfully teleport to your death point!");
                return true;
            } else {
                p.sendMessage(plugin.Prefix + "§4does not found your last Death point!");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}