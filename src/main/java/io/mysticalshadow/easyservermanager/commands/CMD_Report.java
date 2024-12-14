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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CMD_Report implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Report (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("report").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        File file = new File(plugin.getDataFolder(), "reports.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (p.hasPermission(plugin.PermsCloseReport) || p.hasPermission(plugin.PermsAcceptReport) || p.hasPermission(plugin.PermsListReports) || p.hasPermission(plugin.PermSternchen)) {
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "report add <player> <reason>");
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "report accept <player> <reason>");
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "report close <player> <reason>");
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "report list <player>");
                    return true;
                } else {
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "report add <player> <reason>");
                    return true;
                }
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("add")) {
                    String player = args[1];
                    String cause = args[2];
                    Player toReport = Bukkit.getPlayer(player);
                    if (toReport != null) {
                        String path = plugin.ServerName + ".ReportSystem." + player + "." + cause + ".";
                        List<String> reportedCause = config.getStringList(plugin.ServerName + ".Player." + player + ".CauseList");
                        if (reportedCause.contains(cause)) {
                            p.sendMessage(plugin.Prefix + "§4The player is already reported with this reason! §c" + cause + "§4!");
                            return true;
                        } else {
                            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
                            String orginal = date.format(new Date());
                            reportedCause.add(cause);
                            config.set(plugin.ServerName + ".Player." + player + ".CauseList", reportedCause);
                            config.set(path + "Reason", cause);
                            config.set(path + "Creator", p.getName());
                            config.set(path + "ReportedPlayer", player);
                            config.set(path + "Date", orginal);
                            config.set(path + "InProgress", false);
                            String world = p.getWorld().getName();
                            double x = p.getLocation().getX();
                            double y = p.getLocation().getY();
                            double z = p.getLocation().getZ();
                            double yaw = p.getLocation().getYaw();
                            double pitch = p.getLocation().getPitch();
                            config.set(path + "Location.World", world);
                            config.set(path + "Location.X", x);
                            config.set(path + "Location.Y", y);
                            config.set(path + "Location.Z", z);
                            config.set(path + "Location.Yaw", yaw);
                            config.set(path + "Location.Pitch", pitch);
                            try {
                                config.save(file);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                if (all.hasPermission(plugin.PermsNotifyReports) || all.hasPermission(plugin.PermSternchen)) {
                                    all.sendMessage("");
                                    all.sendMessage(plugin.Prefix + "§2ReportPlayer: §3" + player);
                                    all.sendMessage(plugin.Prefix + "§2ReportCreator: §3" + p.getName());
                                    all.sendMessage(plugin.Prefix + "§2ReportReason: §3" + cause);
                                    all.sendMessage(plugin.Prefix + "§2Accept Report §3/report accept " + player + " " + cause);
                                    all.sendMessage(" ");
                                }
                                p.sendMessage(plugin.Prefix + "§2You have the player §3" + player + " §2Successfully reported for the following reason §3" + cause + "§2!");
                                return true;
                            }
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                        return true;
                    }
                }
            } else if (args[0].equalsIgnoreCase("add")) {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "report add <player> <reason>");
                return true;
            }
                if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("close")) {
                        if (p.hasPermission(plugin.PermsCloseReport) || p.hasPermission(plugin.PermSternchen)) {
                        try {
                            config.load(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        if (config.isSet(plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2] + ".Editor")) {
                            String editorName = config.getString(plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2] + ".Editor");
                            if (!p.getName().equals(editorName)) {
                                p.sendMessage(plugin.Prefix + "§4You cannot close this report! Please contact the report Editor");
                                return true;
                            } else {
                                List<String> listReports = config.getStringList(plugin.ServerName + ".Player." + args[1] + ".CauseList");
                                listReports.remove(args[2]);
                                config.set(plugin.ServerName + ".Player." + args[1] + ".CauseList", listReports);
                                if (listReports.isEmpty()) {
                                    config.set(plugin.ServerName + ".ReportSystem." + args[1], null);
                                } else {
                                    config.set(plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2], null);
                                }
                                try {
                                    config.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    if (all.hasPermission(plugin.PermsNotifyReports) || all.hasPermission(plugin.PermSternchen)) {
                                        all.sendMessage("§2The player §3" + p.getDisplayName() + " §2close the report §3" + args[2] + "§2!");
                                    }
                                }
                                p.sendMessage(plugin.Prefix + "§2You close successfully the report §3" + args[2] + "§2!");
                                return true;
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + "§4The report is not in progressing!");
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                    return true;
                }
                }
            } else if (args[0].equalsIgnoreCase("close")) {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "report close <player> <reason>");
                return true;
                }
            if (p.hasPermission(plugin.PermsAcceptReport) || p.hasPermission(plugin.PermSternchen)) {
                if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("accept")) {
                        try {
                            config.load(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        if (config.isSet(plugin.ServerName + ".ReportSystem." + args[1])) {
                            if (config.isSet(plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2])) {
                                if (config.getBoolean(plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2] + ".InProgress", Boolean.valueOf(true))) {
                                    p.sendMessage(plugin.Prefix + "§4The report §c" + args[2] + " §4is already in progressing!");
                                    String editorName = config.getString(plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2] + ".Editor");
                                    p.sendMessage(plugin.Prefix + "§2The editor is following player §3" + editorName);
                                    return true;
                                } else {
                                    String world = config.getString(plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2] + ".Location.World");
                                    double x = config.getDouble(plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2] + ".Location.X");
                                    double y = config.getDouble(plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2] + ".Location.Y");
                                    double z = config.getDouble(plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2] + ".Location.Z");
                                    float yaw = (float) config.getDouble(plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2] + ".Location.Yaw");
                                    float pitch = (float) config.getDouble(plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2] + ".Location.Pitch");
                                    if (world != null) {
                                        Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                                        p.teleport(location);
                                        config.set(plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2] + ".Location", null);
                                        config.set(plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2] + ".InProgress", true);
                                        config.set(plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2] + ".Editor", p.getName());
                                        try {
                                            config.save(file);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                        p.sendMessage(plugin.Prefix + "§2You have successfully adopted this report §3" + args[2] + "§2!");
                                        p.sendMessage(plugin.Prefix + "§2When you are done do §3/report close " + args[1] + " " + args[2]);
                                        return true;
                                    } else {
                                        p.sendMessage(plugin.Prefix + "§4This world does not exist!");
                                        return true;
                                    }
                                }
                            } else {
                                p.sendMessage(plugin.Prefix + "§4The player §c" + args[1] + " §4have no report with this reason §c" + args[2] + "§4!");
                                return true;
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + "§4The player §c" + args[1] + " §4have no reports!");
                            return true;
                        }
                    } else if (args[0].equalsIgnoreCase("accept")) {
                        p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "report accept " + args[1] + " " + args[2]);
                        return true;
                    }
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                return true;
            }
            if (args.length == 2) {
                if (p.hasPermission(plugin.PermsListReports) || p.hasPermission(plugin.PermSternchen)) {
                    if (args[0].equalsIgnoreCase("list")) {
                        String player = args[1];
                        try {
                            config.load(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        if (config.isSet(plugin.ServerName + ".ReportSystem." + args[1])) {
                            if (!config.getBoolean(plugin.ServerName + ".ReportSystem." + args[1])) {
                                for (String listReports : config.getConfigurationSection(plugin.ServerName + ".ReportSystem." + args[1]).getKeys(false)) {
                                    p.sendMessage(plugin.Prefix + "§2The player §3" + args[1] + " §2has following reports! §3" + listReports);
                                }
                                return true;
                            } else {
                                p.sendMessage(plugin.Prefix + "§4The player §c" + player + " §4has not reports!");
                                return true;
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + "§4The player §c" + player + " §4have no reports!");
                            return true;
                        }
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "report list <name>");
                return true;
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
            return true;
        }
        return false;
    }
}