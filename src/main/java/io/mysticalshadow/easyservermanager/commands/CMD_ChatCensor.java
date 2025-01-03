package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CMD_ChatCensor implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_ChatCensor (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("censor").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission(plugin.PermCensor) || sender.hasPermission(plugin.PermSternchen)) {
            File file = new File(plugin.getDataFolder(), "censor.yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            try {
                config.load(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
            if (args.length == 0) {
                if (config.isSet(plugin.ServerName + ".Censor.Words")) {
                    List<String> blocked = config.getStringList(plugin.ServerName + ".Censor.Words");
                    sender.sendMessage(plugin.Prefix + blocked);
                } else {
                    sender.sendMessage(plugin.Prefix + plugin.NoWordsBlockedMSG);
                }
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("add")) {
                    List<String> words = config.getStringList(plugin.ServerName + ".Censor.Words");
                    if (!words.contains(args[1])) {
                        words.add(args[1]);
                        config.set(plugin.ServerName + ".Censor.Words", words);
                        try {
                            config.save(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        String addedWords = plugin.AddedWordsBlockedMSG;
                        addedWords = addedWords.replace("%word%", args[1]);
                        sender.sendMessage(plugin.Prefix + addedWords);
                        return true;
                    } else {
                        sender.sendMessage(plugin.Prefix + plugin.ThisWordIsAlreadyBlocked);
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    List<String> words = config.getStringList(plugin.ServerName + ".Censor.Words");
                    if (words.contains(args[1])) {
                        words.remove(args[1]);
                        if (words.size() == 0) {
                            config.set(plugin.ServerName + ".Censor", null);
                        } else {
                            config.set(plugin.ServerName + ".Censor.Words", words);
                        }
                        try {
                            config.save(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        String removedWords = plugin.RemovedWordsBlockedMSG;
                        removedWords = removedWords.replace("%word%", args[1]);
                        sender.sendMessage(plugin.Prefix + removedWords);
                        return true;
                    } else {
                        sender.sendMessage(plugin.Prefix + plugin.ThisWordIsNotBlocked);
                    }
                }
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.NoPermMessage);
        }
        return false;
    }
}