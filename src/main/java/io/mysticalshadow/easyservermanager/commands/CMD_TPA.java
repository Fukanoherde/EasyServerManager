package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class CMD_TPA implements CommandExecutor {

    public static HashMap<Player, ArrayList<Player>> request = new HashMap<Player, ArrayList<Player>>();

    private EasyServerManager plugin;
    public CMD_TPA(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("tpa").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                        if (request.containsKey(p)) {
                            request.get(target).add(p);
                        } else {
                            ArrayList<Player> req = new ArrayList<Player>();
                            req.add(p);
                            request.put(target, req);
                        }
                        p.sendMessage(plugin.Prefix + "§3You successfully a tpa request. To the player §2" + target.getName());
                        String pName = p.getName();
                        target.sendMessage(plugin.Prefix + "§3You have a tpa request from " + pName);
                        target.sendMessage(plugin.Prefix + "§3Accept: /tpa accept " + pName);
                        target.sendMessage(plugin.Prefix + "§3Deny: /tpa deny " + pName);
                    } else {
                    p.sendMessage(plugin.Prefix + "§cPlayer does not exist.");
                }
                return true;
            } else {
                p.sendMessage(plugin.Prefix + "§bUse command §8/tpa <player>");
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("accept")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        if (request.containsKey(p)) {
                            if (request.get(p).contains(target)) {
                                request.get(p).remove(target);
                                target.teleport(p.getLocation());
                                p.sendMessage(plugin.Prefix + "§3You successfully accepted a tpa request.");
                                target.sendMessage(plugin.Prefix + "§3The player §2" + p.getName() + " §3successfully teleported to you");
                                return true;
                            } else {
                                p.sendMessage(plugin.Prefix + "§cYou dont have a tpa request");
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + "§cYou dont have a tpa request");
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + "§cPlayer does not exist.");
                    }
                }
                if (args[0].equalsIgnoreCase("deny")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        if (request.containsKey(p)) {
                            if (request.get(p).contains(target)) {
                                request.get(p).remove(target);
                                p.sendMessage(plugin.Prefix + "§3You successfully denied a tpa request.");
                                target.sendMessage(plugin.Prefix + "§cThe player §4" + p.getName() + " §cdenied your tpa request");
                                return true;
                            } else {
                                p.sendMessage(plugin.Prefix + "§cYou dont have a tpa request");
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + "§cYou dont have a tpa request");
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + "§cPlayer does not exist.");
                    }
                }
            } else {
                p.sendMessage(plugin.Prefix + "§bUse command §8/tpa accept <player> or tpa deny <player>");
            }
        } else {
            sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
        }
        return false;
    }
}