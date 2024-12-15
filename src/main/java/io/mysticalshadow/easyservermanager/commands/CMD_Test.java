package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;

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
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
            return true;
        }
        return false;
    }
}