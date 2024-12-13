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
        if (args.length == 0) {
            Player p = (Player) sender;
            if (p.hasPermission(plugin.PermsWarn) || p.hasPermission(plugin.PermsRemoveWarn) || p.hasPermission(plugin.PermsGetWarn) || p.hasPermission(plugin.PermSternchen)) {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "warn add <player> <reason>");
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "warn get <player>");
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "warn remove <player> <reason>");
                return true;
            } else {
                p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                return true;
            }
        }
        if (args.length == 2) {
            Player p = (Player) sender;
            if (args[0].equalsIgnoreCase("get")) {
                if (p.hasPermission(plugin.PermsGetWarn) || p.hasPermission(plugin.PermSternchen)) {
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
                                    String getWarns = plugin.AnnotherPlayerWarnsMSG;
                                    getWarns = getWarns.replace("%warns%", warns);
                                    p.sendMessage(plugin.Prefix + getWarns);
                                }
                                return true;
                            } else {
                                String playerHaveNoWarns = plugin.PlayerHaveNoWarnsMSG;
                                playerHaveNoWarns = playerHaveNoWarns.replace("%player%", target.getDisplayName());
                                p.sendMessage(plugin.Prefix + playerHaveNoWarns);
                                return true;
                            }
                        } else {
                            String playerHaveNoWarns = plugin.PlayerHaveNoWarnsMSG;
                            playerHaveNoWarns = playerHaveNoWarns.replace("%player%", target.getDisplayName());
                            p.sendMessage(plugin.Prefix + playerHaveNoWarns);
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
        } else if (args[0].equalsIgnoreCase("get")) {
            sender.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "warn get <player>");
            return true;
        }
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("add")) {
                    Player target = Bukkit.getPlayer(args[1]);
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
                            String path = target.getName() + ".Warn." + args[2];
                            List<String> listWarns = config.getStringList(target.getName() + ".WarnList");
                            if (!listWarns.contains(args[2])) {
                                listWarns.add(args[2]);
                                config.set(target.getName() + ".WarnList", listWarns);
                                config.set(path + ".Reason", args[2]);
                                config.set(path + ".Creator", p.getName());
                                config.set(path + ".Date", orginal);
                                try {
                                    config.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    if (all.hasPermission(plugin.PermsNotifyWarn) || all.hasPermission(plugin.PermSternchen)) {
                                        all.sendMessage("");
                                        all.sendMessage(plugin.Prefix + "§2The player §3" + sender.getName());
                                        all.sendMessage(plugin.Prefix + "§2Warned the player §3" + target.getName());
                                        all.sendMessage(plugin.Prefix + "§2Warn Reason §3" + args[2]);
                                        all.sendMessage(plugin.Prefix + "§2Total warns §3" + listWarns.size());
                                        all.sendMessage(" ");
                                    }
                                }
                                if (listWarns.size() == plugin.MaxWarnsBeforeKick) {
                                    List<String> historyWarns = config.getStringList(target.getName() + ".History.Warn." + args[2] + ".Reason");
                                    historyWarns.add(String.valueOf(listWarns));
                                    target.kickPlayer(plugin.PlayerWarnKickMSG);
                                    config.set(target.getName() + ".History.Warns." + args[2] +".Reason." + args[2], historyWarns);
                                    config.set(target.getName() + ".History.Warns." + args[2] + ".Date", orginal);
                                    listWarns.clear();
                                    config.set(target.getName() + ".WarnList", listWarns);
                                    config.set(target.getName() + ".Warn", null);
                                    try {
                                        config.save(file);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    String playerKickedWarnExceeding = plugin.PlayerKickedWarnMSG;
                                    playerKickedWarnExceeding = playerKickedWarnExceeding.replace("%player%", target.getDisplayName());
                                    p.sendMessage(plugin.Prefix + playerKickedWarnExceeding);
                                    return true;
                                }
                                String warnedPlayerWithReason = plugin.YourWarnedThePlayerMSG;
                                warnedPlayerWithReason = warnedPlayerWithReason.replace("%player%", target.getDisplayName());
                                warnedPlayerWithReason = warnedPlayerWithReason.replace("%reason%", args[2]);
                                sender.sendMessage(plugin.Prefix + warnedPlayerWithReason);
                                return true;
                            } else {
                                String wanrReasonAlreadyListed = plugin.WarnReasonAlreadyListedMSG;
                                wanrReasonAlreadyListed = wanrReasonAlreadyListed.replace("%reason%", args[2]);
                                p.sendMessage(plugin.Prefix + wanrReasonAlreadyListed);
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
            } else if (args[0].equalsIgnoreCase("add")) {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "warn add <player> <reason>");
                return true;
            }
            if (p.hasPermission(plugin.PermsRemoveWarn) || p.hasPermission(plugin.PermSternchen)) {
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
                                String warnRemoved = plugin.SuccessfullyRemoveWarnMSG;
                                warnRemoved = warnRemoved.replace("%reason%", args[2]);
                                warnRemoved = warnRemoved.replace("%player%", target.getDisplayName());
                                p.sendMessage(plugin.Prefix + warnRemoved);
                                return true;
                            } else {
                                String playerIsNotWarnedWithThisReason = plugin.PlayerHasNoWarnWithThisReasonMSG;
                                playerIsNotWarnedWithThisReason = playerIsNotWarnedWithThisReason.replace("%reason%", args[2]);
                                playerIsNotWarnedWithThisReason = playerIsNotWarnedWithThisReason.replace("%player%", target.getDisplayName());
                                p.sendMessage(plugin.Prefix + playerIsNotWarnedWithThisReason);
                                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "warn get <player>");
                                return true;
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                            return true;
                        }
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "warn remove <player> <reason>");
                    return true;
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