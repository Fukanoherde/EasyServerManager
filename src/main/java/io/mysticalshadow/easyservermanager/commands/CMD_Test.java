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
import java.util.List;

public class CMD_Test implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Test (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("test").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            File file = new File(plugin.getDataFolder(), "reports.yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
            return true;
        }
        return false;
    }
}