package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_EasyServerManager implements CommandExecutor {
    int countdown;
    private EasyServerManager plugin;
    public CMD_EasyServerManager(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("easyservermanager").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                p.sendMessage("");
                p.sendMessage("§3§lEasy§b§lServer§3§lManager");
                p.sendMessage(" ");
                p.sendMessage("§2Author: §3MysticalShadow95");
                p.sendMessage("§2Version: §3" + plugin.getDescription().getVersion());
                p.sendMessage("§2API-Version: §3" + plugin.getDescription().getAPIVersion());
                p.sendMessage("  ");
                return true;
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("restart")) {
                    if (args[1].equalsIgnoreCase("now")) {
                        if (p.hasPermission(plugin.PermsServerRestart) || p.hasPermission(plugin.PermSternchen)) {
                            final int[] time = {Integer.parseInt(args[2])};
                            p.sendMessage(plugin.Prefix + plugin.StartRestartCountdownMSG);
                            countdown = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    if (time[0] != 0) {
                                        time[0]--;
                                        if (time[0] == 11) {
                                            for (Player all : Bukkit.getOnlinePlayers()) {
                                                if (plugin.getConfig().getBoolean("EasyServerManager.AllowPlayRestartSound", Boolean.TRUE)) {
                                                    all.playSound(all, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                                                }
                                                all.sendMessage(plugin.Prefix + plugin.RestartInTenSecondsMSG);
                                            }
                                        } else if (time[0] == 6) {
                                            for (Player all : Bukkit.getOnlinePlayers()) {
                                                if (plugin.getConfig().getBoolean("EasyServerManager.AllowPlayRestartSound", Boolean.TRUE)) {
                                                    all.playSound(all, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                                                }
                                                all.sendMessage(plugin.Prefix + plugin.RestartInFiveSecondsMSG);
                                            }
                                        } else if (time[0] == 4) {
                                            for (Player all : Bukkit.getOnlinePlayers()) {
                                                if (plugin.getConfig().getBoolean("EasyServerManager.AllowPlayRestartSound", Boolean.TRUE)) {
                                                    all.playSound(all, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                                                }
                                                all.sendMessage(plugin.Prefix + plugin.RestartInThreeSecondsMSG);
                                            }
                                        } else if (time[0] == 3) {
                                            for (Player all : Bukkit.getOnlinePlayers()) {
                                                if (plugin.getConfig().getBoolean("EasyServerManager.AllowPlayRestartSound", Boolean.TRUE)) {
                                                    all.playSound(all, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                                                }
                                                all.sendMessage(plugin.Prefix + plugin.RestartInTwoSecondsMSG);
                                            }
                                        } else if (time[0] == 2) {
                                            for (Player all : Bukkit.getOnlinePlayers()) {
                                                if (plugin.getConfig().getBoolean("EasyServerManager.AllowPlayRestartSound", Boolean.TRUE)) {
                                                    all.playSound(all, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                                                }
                                                all.sendMessage(plugin.Prefix + plugin.RestartInOneSecondsMSG);
                                            }
                                        }
                                    } else {
                                        for (Player all : Bukkit.getOnlinePlayers()) {
                                            all.kickPlayer(plugin.PlayerKickWhenServerRestartMSG);
                                        }
                                        Bukkit.spigot().restart();
                                        Bukkit.getScheduler().cancelTask(countdown);
                                    }
                                }
                            }, 0, 20);
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                            return true;
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("restart")) {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "esm restart now <interval>");
                return true;
            }
        }
        return false;
    }
}