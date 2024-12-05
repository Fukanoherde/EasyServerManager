package io.siedlermc.easysiedlermanager.commands;

import io.siedlermc.easysiedlermanager.EasySiedlerManager;
import io.siedlermc.easysiedlermanager.manager.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CMD_Payout implements CommandExecutor {

    private EasySiedlerManager plugin;
    public CMD_Payout (EasySiedlerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("payout").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            File file = new File("plugins//EasySiedlerManager//Players", p.getUniqueId() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            if (args.length == 1) {
                int level = Integer.parseInt(args[0]);
                if (config.isSet(p.getName() + "." + "Level")) {
                    try {
                        config.load(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                    if (config.getInt(p.getName() + "." + "Level") >= level) {
                        config.set(p.getName() + "." + "Level", config.getInt(p.getName() + "." + "Level") - level);
                        try {
                            config.save(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        p.setLevel(p.getLevel() + level);
                        ScoreboardManager.setBoard(p);
                        ScoreboardManager.updateBoard(p);
                        p.sendMessage(plugin.Prefix + "§3The payout was successfully");
                        return true;
                    } else {
                        p.sendMessage(plugin.Prefix + "§cYou don't have enough level");
                    }
                }
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}