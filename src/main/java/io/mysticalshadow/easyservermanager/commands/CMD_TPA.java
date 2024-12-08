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
                        String successfullySendRequest = plugin.SendPlayerTPAMSG;
                        successfullySendRequest = successfullySendRequest.replace("%player%", target.getDisplayName());
                        p.sendMessage(plugin.Prefix + successfullySendRequest);
                        String pName = p.getName();
                        String receivedRequest = plugin.ReceivedRequestMSG;
                        receivedRequest = receivedRequest.replace("%player%", pName);
                        target.sendMessage(plugin.Prefix + receivedRequest);
                        String acceptRequest = plugin.AcceptRequestMSG;
                        acceptRequest = acceptRequest.replace("%player%", pName);
                        String denyRequest = plugin.DeniedRequestMSG;
                        denyRequest = denyRequest.replace("%player%", pName);
                        target.sendMessage(plugin.Prefix + acceptRequest);
                        target.sendMessage(plugin.Prefix + denyRequest);
                    } else {
                    p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                }
                return true;
            } else {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "tpa <player>");
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("accept")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        if (request.containsKey(p)) {
                            if (request.get(p).contains(target)) {
                                request.get(p).remove(target);
                                target.teleport(p.getLocation());
                                p.sendMessage(plugin.Prefix + plugin.AcceptTPAMSG);
                                String acceptedTPA = plugin.PlayerAcceptTPAMSG;
                                acceptedTPA = acceptedTPA.replace("%player%", p.getDisplayName());
                                target.sendMessage(plugin.Prefix + acceptedTPA);
                                return true;
                            } else {
                                p.sendMessage(plugin.Prefix + plugin.YouDontTPARequestMSG);
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.YouDontTPARequestMSG);
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                    }
                }
                if (args[0].equalsIgnoreCase("deny")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        if (request.containsKey(p)) {
                            if (request.get(p).contains(target)) {
                                request.get(p).remove(target);
                                p.sendMessage(plugin.Prefix + plugin.PlayerDeniedTPAMSG);
                                String deniedTPA = plugin.DenyTPAMSG;
                                deniedTPA = deniedTPA.replace("%player%", p.getDisplayName());
                                target.sendMessage(plugin.Prefix + deniedTPA);
                                return true;
                            } else {
                                p.sendMessage(plugin.Prefix + plugin.YouDontTPARequestMSG);
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.YouDontTPARequestMSG);
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                    }
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "tpa accept <player> or tpa deny <player>");
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
        }
        return false;
    }
}