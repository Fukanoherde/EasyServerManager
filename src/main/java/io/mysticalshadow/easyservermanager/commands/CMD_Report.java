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
                    String path = plugin.ServerName + ".ReportSystem." + player + "." + cause + ".";
                    Player tpReport = Bukkit.getPlayer(player);
                    try {
                        config.load(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                    if (tpReport != null) {
                        List<String> reportToCause = config.getStringList(plugin.ServerName + ".Player." + player + ".CauseList");
                        if (reportToCause.contains(cause)) {
                            String alreadyPlayerReportedWithThisReason = plugin.ReportReasonIsAlreadyExistMSG;
                            alreadyPlayerReportedWithThisReason = alreadyPlayerReportedWithThisReason.replace("%player%", player);
                            alreadyPlayerReportedWithThisReason = alreadyPlayerReportedWithThisReason.replace("%reason%", cause);
                            p.sendMessage(plugin.Prefix + alreadyPlayerReportedWithThisReason);
                            return true;
                        } else {
                            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
                            String original = date.format(new Date());
                            reportToCause.add(cause);
                            config.set(plugin.ServerName + ".Player." + player + ".CauseList", reportToCause);
                            config.set(path + "Reason", reportToCause);
                            config.set(path + "Player", player);
                            config.set(path + "Creator", p.getName());
                            config.set(path + "Date", original);
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
                                    all.sendMessage("§4ReportSystem");
                                    all.sendMessage(" ");
                                    all.sendMessage("§2Reported Player §3" + player);
                                    all.sendMessage("§2Report Creator §3" + p.getName());
                                    all.sendMessage("§2Report Reason §3" + cause);
                                    all.sendMessage("§2Report accept §3/report accept " + player + " " + cause);
                                    all.sendMessage("  ");
                                }
                            }
                            String successpReportPlayer = plugin.SuccessReportPlayerMSG;
                            successpReportPlayer = successpReportPlayer.replace("%player%", player);
                            p.sendMessage(plugin.Prefix + successpReportPlayer);
                            return true;
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("add")) {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "report add <player> <reason>");
                return true;
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("accept")) {
                    if (p.hasPermission(plugin.PermsAcceptReport) || p.hasPermission(plugin.PermSternchen)) {
                        try {
                            config.load(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        String path = plugin.ServerName + ".ReportSystem." + args[1];
                        if (config.isSet(path)) {
                            if (config.isSet(path + "." + args[2])) {
                                if (config.getBoolean(path + "." + args[2] + ".InProgress", Boolean.valueOf(true))) {
                                    String editorPlayer = config.getString(path + "." + args[2] + ".Editor");
                                    p.sendMessage(plugin.Prefix + plugin.ReportAlreadyProgressingMSG);
                                    String editorPlayerIs = plugin.ReportAlreadyProgressingByMSG;
                                    if (editorPlayer != null) {
                                        editorPlayerIs = editorPlayerIs.replace("%player%", editorPlayer);
                                        p.sendMessage(plugin.Prefix + editorPlayerIs);
                                        return true;
                                    } else {
                                        p.sendMessage(plugin.Prefix + plugin.ReportAlreadyProgressingMSG);
                                        return true;
                                    }
                                } else {
                                    String world = config.getString(path + "." + args[2] + ".Location.World");
                                    double x = config.getDouble(path + "." + args[2] + ".Location.X");
                                    double y = config.getDouble(path + "." + args[2] + ".Location.Y");
                                    double z = config.getDouble(path + "." + args[2] + ".Location.Z");
                                    float yaw = (float) config.getDouble(path + "." + args[2] + ".Location.Yaw");
                                    float pitch = (float) config.getDouble(path + "." + args[2] + ".Location.Pitch");
                                    if (world != null) {
                                        Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                                        p.teleport(location);
                                        config.set(path + "." + args[2] + ".Location", null);
                                        config.set(path + "." + args[2] + ".InProgress", true);
                                        config.set(path + "." + args[2] + ".Editor", p.getName());
                                        try {
                                            config.save(file);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                        String acceptReport = plugin.YouAcceptReportMSG;
                                        acceptReport = acceptReport.replace("%player%", args[1]);
                                        acceptReport = acceptReport.replace("%reason%", args[2]);
                                        p.sendMessage(plugin.Prefix + acceptReport);
                                        String finishReport = plugin.FinishReportMSG;
                                        finishReport = finishReport.replace("%player%", args[1]);
                                        finishReport = finishReport.replace("%reason%", args[2]);
                                        p.sendMessage(plugin.Prefix + finishReport);
                                        return true;
                                    } else {
                                        p.sendMessage(plugin.Prefix + plugin.WorldNotExistMSG);
                                        return true;
                                    }
                                }
                            } else {
                                String playerHaveNoReportsWithThisReason = plugin.PlayerHaveNoReportsWithThisReasonMSG;
                                playerHaveNoReportsWithThisReason = playerHaveNoReportsWithThisReason.replace("%player%", args[1]);
                                playerHaveNoReportsWithThisReason = playerHaveNoReportsWithThisReason.replace("%reason%", args[2]);
                                p.sendMessage(plugin.Prefix + playerHaveNoReportsWithThisReason);
                                return true;
                            }
                        } else {
                            String playerHaveNoReports = plugin.PlayerHaveNoReportsMSG;
                            playerHaveNoReports = playerHaveNoReports.replace("%player%", args[1]);
                            p.sendMessage(plugin.Prefix + playerHaveNoReports);
                            return true;
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                        return true;
                    }
                }
            } else if (args[0].equalsIgnoreCase("accept")) {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "report accept <player> <reason>");
                return true;
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("close")) {
                    if (p.hasPermission(plugin.PermsCloseReport) || p.hasPermission(plugin.PermSternchen)) {
                        String path = plugin.ServerName + ".ReportSystem." + args[1] + "." + args[2] + ".";
                        try {
                            config.load(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        if (config.isSet(path)) {
                            String editorName = config.getString(path + "Editor");
                            if (p.getName().equals(editorName)) {
                                List<String> reportToCause = config.getStringList(plugin.ServerName + ".Player." + args[1] + ".CauseList");
                                reportToCause.remove(args[2]);
                                config.set(plugin.ServerName + ".Player." + args[1] + ".CauseList", reportToCause);
                                if (reportToCause.isEmpty()) {
                                    config.set(plugin.ServerName + ".ReportSystem." + args[1], null);
                                } else {
                                    config.set(path, null);
                                }
                                try {
                                    config.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                String closeReport = plugin.YouCloseReportMSG;
                                closeReport = closeReport.replace("%player%", args[1]);
                                closeReport = closeReport.replace("%reason%", args[2]);
                                p.sendMessage(plugin.Prefix + closeReport);
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    if (all.hasPermission(plugin.PermsNotifyReports) || all.hasPermission(plugin.PermSternchen)) {
                                        String thePlayerClosedReport = plugin.ThePlayerClosedReportMSG;
                                        thePlayerClosedReport = thePlayerClosedReport.replace("%player%", p.getDisplayName());
                                        thePlayerClosedReport = thePlayerClosedReport.replace("%reason%", args[2]);
                                        all.sendMessage("");
                                        all.sendMessage(thePlayerClosedReport);
                                        all.sendMessage("");
                                    }
                                }
                                return true;
                            } else {
                                p.sendMessage(plugin.Prefix + plugin.YouNotEditorFromThisReportMSG);
                                String editorFromThisReport = plugin.ReportEditorFromThisReportMSG;
                                assert editorName != null;
                                editorFromThisReport = editorFromThisReport.replace("%player%", editorName);
                                p.sendMessage(plugin.Prefix + editorFromThisReport);
                                return true;
                            }
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
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("list")) {
                    if (p.hasPermission(plugin.PermsListReports) || p.hasPermission(plugin.PermSternchen)) {
                        try {
                            config.load(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        String path = plugin.ServerName + ".ReportSystem." + args[1];
                        if (config.isSet(path)) {
                            if (!config.getBoolean(path)) {
                                for (String reports : config.getConfigurationSection(path).getKeys(false)) {
                                    String listReportsPlayer = plugin.ReportsFromThePlayerMSG;
                                    listReportsPlayer = listReportsPlayer.replace("%player%", args[1]);
                                    p.sendMessage(plugin.Prefix + listReportsPlayer + reports);
                                }
                                return true;
                            } else {
                                String playerHaveNoReports = plugin.PlayerHaveNoReportsMSG;
                                playerHaveNoReports = playerHaveNoReports.replace("%player%", args[1]);
                                p.sendMessage(plugin.Prefix + playerHaveNoReports);
                                return true;
                            }
                        } else {
                            String playerHaveNoReports = plugin.PlayerHaveNoReportsMSG;
                            playerHaveNoReports = playerHaveNoReports.replace("%player%", args[1]);
                            p.sendMessage(plugin.Prefix + playerHaveNoReports);
                            return true;
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                        return true;
                    }
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "report list <player>");
                return true;
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
            return true;
        }
        return false;
    }
}