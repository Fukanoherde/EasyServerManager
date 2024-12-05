package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import io.mysticalshadow.easyservermanager.manager.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CMD_Deposit implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Deposit (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("deposit").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            File file = new File("plugins//EasySiedlerManager//Players", p.getUniqueId() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            if (args.length == 1) {
                int level = Integer.parseInt(args[0]);
                if (p.getLevel() >= level) {
                    config.set(p.getName() + "." + "Level", config.getInt(p.getName() + "." + "Level") + level);
                    try {
                        config.save(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    p.setLevel(p.getLevel() - level);
                    ScoreboardManager.setBoard(p);
                    ScoreboardManager.updateBoard(p);
                    p.sendMessage(plugin.Prefix + "§3The deposit was successfully");
                    return true;
                } else {
                    p.sendMessage(plugin.Prefix + "§cYou dont have enough level");
                }
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}