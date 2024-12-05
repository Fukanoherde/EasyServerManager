package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_GameMode implements CommandExecutor {
    private EasyServerManager plugin;
    public CMD_GameMode(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("gamemode").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("siedlermanager.gamemode") || p.hasPermission("siedlermanager.*")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("0")) {
                        if (!(p.getGameMode() == GameMode.SURVIVAL)) {
                            p.setGameMode(GameMode.SURVIVAL);
                            p.sendMessage(plugin.Prefix + "§2You are now in survival mode");
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + "§cYou are already on the survival mode");
                        }
                    } else if (args[0].equalsIgnoreCase("1")) {
                        if (!(p.getGameMode() == GameMode.CREATIVE)) {
                            p.setGameMode(GameMode.CREATIVE);
                            p.sendMessage(plugin.Prefix + "§2You are now in creative mode");
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + "§cYou are already on the creative mode");
                        }
                    } else if (args[0].equalsIgnoreCase("2")) {
                        if (!(p.getGameMode() == GameMode.ADVENTURE)) {
                            p.setGameMode(GameMode.ADVENTURE);
                            p.sendMessage(plugin.Prefix + "§2You are now in adventure mode");
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + "§cYou are already on an adventure mode");
                        }
                    } else if (args[0].equalsIgnoreCase("3")) {
                        if (!(p.getGameMode() == GameMode.SPECTATOR)) {
                            p.setGameMode(GameMode.SPECTATOR);
                            p.sendMessage(plugin.Prefix + "§2You are now in spectator mode");
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + "§cYou are already on an spectator mode");
                            return true;
                        }
                    }
                } else {
                    //p.sendMessage(plugin.Prefix + "§bUse Command: §8/gm <0, 1, 2, 3>");
                }
            } else {
                p.sendMessage(plugin.Prefix + "§cYou do not have permission to use this command");
            }
            if (p.hasPermission("siedlermanager.gamemode.other") || p.hasPermission("siedlermanager.*")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    if (target.getName() != p.getName()) {
                        if (args.length == 2) {
                            if (args[0].equalsIgnoreCase("0")) {
                                if (target.getGameMode() != GameMode.SURVIVAL) {
                                    target.setGameMode(GameMode.SURVIVAL);
                                    target.sendMessage(plugin.Prefix + "§3You are now in survival mode");
                                    p.sendMessage(plugin.Prefix + "§2You set the Player in survival mode");
                                    return true;
                                } else {
                                    p.sendMessage(plugin.Prefix + "§cThe player is already on the survival mode");
                                }
                            } else if (args[0].equalsIgnoreCase("1")) {
                                if (target.getGameMode() != GameMode.CREATIVE) {
                                    target.setGameMode(GameMode.CREATIVE);
                                    target.sendMessage(plugin.Prefix + "§3You are now in creative mode");
                                    p.sendMessage(plugin.Prefix + "§2You set the Player in creative mode");
                                    return true;
                                } else {
                                    p.sendMessage(plugin.Prefix + "§cThe player is already on the creative mode");
                                }
                            } else if (args[0].equalsIgnoreCase("2")) {
                                if (target.getGameMode() != GameMode.ADVENTURE) {
                                    target.setGameMode(GameMode.ADVENTURE);
                                    target.sendMessage(plugin.Prefix + "§3You are now in adventure mode");
                                    p.sendMessage(plugin.Prefix + "§2You set the Player in adventure mode");
                                    return true;
                                } else {
                                    p.sendMessage(plugin.Prefix + "§cThe player is already on an adventure mode");
                                }
                            } else if (args[0].equalsIgnoreCase("3")) {
                                if (target.getGameMode() != GameMode.SPECTATOR) {
                                    target.setGameMode(GameMode.SPECTATOR);
                                    target.sendMessage(plugin.Prefix + "§3You are now in spectator mode");
                                    p.sendMessage(plugin.Prefix + "§2You set the Player in spectator mode");
                                    return true;
                                } else {
                                    p.sendMessage(plugin.Prefix + "§cThe player is already on an spectator mode");
                                }
                            }
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + "§cPlease use /gm 0");
                    }
                } else {
                    p.sendMessage(plugin.Prefix + "§cThe player does not exist");
                }
            } else {
                p.sendMessage(plugin.Prefix + "§cYou do not have permission to use this command");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}
