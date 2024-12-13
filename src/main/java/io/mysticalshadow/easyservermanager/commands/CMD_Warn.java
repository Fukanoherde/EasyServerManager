package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CMD_Warn implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Warn (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("warn").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 2) {
            Player p = (Player) sender;
            if (args[0].equalsIgnoreCase("get")) {
                if (p.hasPermission("")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        File file = new File("plugins//EasyServerManager//Players", target.getUniqueId() + ".yml");
                        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                        try {
                            config.load(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        if (config.isSet(target.getName() + ".Warn")) {
                            if (!config.getBoolean(p.getName() + ".Warn.")) {
                                for (String warns : config.getConfigurationSection(target.getName() + ".Warn").getKeys(false)) {
                                    p.sendMessage(plugin.Prefix + "§2The player has following warns §3" + warns);
                                }
                                return true;
                            } else {
                                p.sendMessage(plugin.Prefix + "§4The player §c" + target.getDisplayName() + " §4has no warns!");
                                return true;
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + "§4The player §c" + target.getDisplayName() + " §4has no warns!");
                            return true;
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                        return true;
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                    return true;
                }
            }
        }
        if (sender instanceof Player) {
            if (args.length == 2) {
                Player p = (Player) sender;
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    File file = new File("plugins//EasyServerManager//Players", target.getUniqueId() + ".yml");
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                    if (p.hasPermission(plugin.PermsWarn) || p.hasPermission(plugin.PermSternchen)) {
                        try {
                            config.load(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
                        String orginal = date.format(new Date());
                        String path = target.getName() + ".Warn." + args[1];
                        List<String> listWarns = config.getStringList(target.getName() + ".WarnList");
                        if (!listWarns.contains(args[1])) {
                            listWarns.add(args[1]);
                            config.set(target.getName() + ".WarnList", listWarns);
                            config.set(path + ".Reason", args[1]);
                            config.set(path + ".Creator", p.getName());
                            config.set(path + ".Date", orginal);
                            try {
                                config.save(file);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                if (all.hasPermission("")) {
                                    all.sendMessage("");
                                    all.sendMessage(plugin.Prefix + "§2The player §3" + sender.getName());
                                    all.sendMessage(plugin.Prefix + "§2Warned the player §3" + target.getName());
                                    all.sendMessage(plugin.Prefix + "§2Warn Reason §3" + args[1]);
                                    all.sendMessage(plugin.Prefix + "§2Total warns §3" + listWarns.size());
                                    all.sendMessage(" ");
                                }
                            }
                            if (listWarns.size() == plugin.MaxWarnsBeforeKick) {
                                List<String> historyWarns = config.getStringList(target.getName() + ".History.Warn." + args[1] + ".Reason");
                                historyWarns.add(String.valueOf(listWarns));
                                target.kickPlayer("You Kicked!");
                                config.set(target.getName() + ".History.Warns." + args[1] +".Reason." + args[1], historyWarns);
                                config.set(target.getName() + ".History.Warns." + args[1] + ".Date", orginal);
                                listWarns.clear();
                                config.set(target.getName() + ".WarnList", listWarns);
                                config.set(target.getName() + ".Warn", null);
                                try {
                                    config.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                p.sendMessage(plugin.Prefix + "§4The player §c" + target.getDisplayName() + " §4was kicked!");
                                return true;
                            }
                            sender.sendMessage(plugin.Prefix + "§2Your warned the player §3" + target.getName() + " §3with the reason §3" + args[1] + "§2!");
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + "§4The reason §c" + args[1] + " §4is already listed!");
                            return true;
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                        return true;
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                    return true;
                }
            }
            Player p = (Player) sender;
            if (p.hasPermission("")) {
                if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("remove")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            File file = new File("plugins//EasyServerManager//Players", target.getUniqueId() + ".yml");
                            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                            try {
                                config.load(file);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (InvalidConfigurationException e) {
                                throw new RuntimeException(e);
                            }
                            if (config.isSet(target.getName() + ".Warn." + args[2])) {
                                List<String> removeWarn = config.getStringList(target.getName() + ".WarnList");
                                removeWarn.remove(args[2]);
                                config.set(target.getName() + ".WarnList", removeWarn);
                                try {
                                    config.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                if (removeWarn.isEmpty()) {
                                    config.set(target.getName() + ".Warn", null);
                                    try {
                                        config.save(file);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    config.set(target.getName() + ".Warn." + args[2], null);
                                    try {
                                        config.save(file);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                p.sendMessage(plugin.Prefix + "§2You successfully removed the warn §3" + args[2] + " §2from the player §3" + target.getDisplayName());
                                return true;
                            } else {
                                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "warn get <player>");
                                p.sendMessage(plugin.Prefix + "§4The player §c" + target.getDisplayName() + " §4Was not for the following reason §c" + args[2] + " §4warned!");
                                return true;
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                            return true;
                        }
                    }
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                return true;
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
            return true;
        }
        return false;
    }
}