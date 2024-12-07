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
            if (p.hasPermission(plugin.PermGameMode) || p.hasPermission(plugin.PermSternchen)) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("0")) {
                        if (!(p.getGameMode() == GameMode.SURVIVAL)) {
                            p.setGameMode(GameMode.SURVIVAL);
                            p.sendMessage(plugin.Prefix + plugin.GameModeSurvivalSelfMSG);
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.GameModeAlreadyInSurvivalSelfMSG);
                        }
                    } else if (args[0].equalsIgnoreCase("1")) {
                        if (!(p.getGameMode() == GameMode.CREATIVE)) {
                            p.setGameMode(GameMode.CREATIVE);
                            p.sendMessage(plugin.Prefix + plugin.GameModeCreativeSelfMSG);
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.GameModeAlreadyInCreativeSelfMSG);
                        }
                    } else if (args[0].equalsIgnoreCase("2")) {
                        if (!(p.getGameMode() == GameMode.ADVENTURE)) {
                            p.setGameMode(GameMode.ADVENTURE);
                            p.sendMessage(plugin.Prefix + plugin.GameModeAdventureSelfMSG);
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.GameModeAlreadyInAdventureSelfMSG);
                        }
                    } else if (args[0].equalsIgnoreCase("3")) {
                        if (!(p.getGameMode() == GameMode.SPECTATOR)) {
                            p.setGameMode(GameMode.SPECTATOR);
                            p.sendMessage(plugin.Prefix + plugin.GameModeSpectatorSelfMSG);
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.GameModeAlreadyInSpectatorSelfMSG);
                            return true;
                        }
                    }
                } else {
                    //p.sendMessage(plugin.Prefix + "§bUse Command: §8/gm <0, 1, 2, 3>");
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
            }
            if (p.hasPermission(plugin.PermGameModeAnnotherPlayer) || p.hasPermission(plugin.PermSternchen)) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    if (target.getName() != p.getName()) {
                        if (args.length == 2) {
                            if (args[0].equalsIgnoreCase("0")) {
                                if (target.getGameMode() != GameMode.SURVIVAL) {
                                    target.setGameMode(GameMode.SURVIVAL);
                                    String changedPlayerGameModeToSurvival = plugin.GameModeSurvivalAnnotherPlayerMSG;
                                    changedPlayerGameModeToSurvival = changedPlayerGameModeToSurvival.replace("%player%", p.getDisplayName());
                                    target.sendMessage(plugin.Prefix + changedPlayerGameModeToSurvival);
                                    String changedFromPlayerGameModeToSurvival = plugin.GameModeSetInSurvivalAnnotherPlayerMSG;
                                    changedFromPlayerGameModeToSurvival = changedFromPlayerGameModeToSurvival.replace("%player%", target.getDisplayName());
                                    p.sendMessage(plugin.Prefix + changedFromPlayerGameModeToSurvival);
                                    return true;
                                } else {
                                    String playerIsAlreadyInSurvival = plugin.GameModeAlreadyPlayerInSurvivalMSG;
                                    playerIsAlreadyInSurvival = playerIsAlreadyInSurvival.replace("%player%", target.getDisplayName());
                                    p.sendMessage(plugin.Prefix + playerIsAlreadyInSurvival);
                                }
                            } else if (args[0].equalsIgnoreCase("1")) {
                                if (target.getGameMode() != GameMode.CREATIVE) {
                                    target.setGameMode(GameMode.CREATIVE);
                                    String changedPlayerGameModeToCreative = plugin.GameModeCreativeAnnotherPlayerMSG;
                                    changedPlayerGameModeToCreative = changedPlayerGameModeToCreative.replace("%player%", p.getDisplayName());
                                    target.sendMessage(plugin.Prefix + changedPlayerGameModeToCreative);
                                    String changedFromPlayerGameModeToCreative = plugin.GameModeSetInCreativeAnnotherPlayerMSG;
                                    changedFromPlayerGameModeToCreative = changedFromPlayerGameModeToCreative.replace("%player%", target.getDisplayName());
                                    p.sendMessage(plugin.Prefix + changedFromPlayerGameModeToCreative);
                                    return true;
                                } else {
                                    String playerIsAlreadyInCreative = plugin.GameModeAlreadyPlayerInCreativeMSG;
                                    playerIsAlreadyInCreative = playerIsAlreadyInCreative.replace("%player%", target.getDisplayName());
                                    p.sendMessage(plugin.Prefix + playerIsAlreadyInCreative);
                                }
                            } else if (args[0].equalsIgnoreCase("2")) {
                                if (target.getGameMode() != GameMode.ADVENTURE) {
                                    target.setGameMode(GameMode.ADVENTURE);
                                    String changedPlayerGameModeToAdventure = plugin.GameModeAdventureAnnotherPlayerMSG;
                                    changedPlayerGameModeToAdventure = changedPlayerGameModeToAdventure.replace("%player%", p.getDisplayName());
                                    target.sendMessage(plugin.Prefix + changedPlayerGameModeToAdventure);
                                    String changedFromPlayerGameModeToAdventure = plugin.GameModeSetInAdventureAnnotherPlayerMSG;
                                    changedFromPlayerGameModeToAdventure = changedFromPlayerGameModeToAdventure.replace("%player%", target.getDisplayName());
                                    p.sendMessage(plugin.Prefix + changedFromPlayerGameModeToAdventure);
                                    return true;
                                } else {
                                    String playerIsAlreadyInAdventure = plugin.GameModeAlreadyPlayerInAdventureMSG;
                                    playerIsAlreadyInAdventure = playerIsAlreadyInAdventure.replace("%player%", target.getDisplayName());
                                    p.sendMessage(plugin.Prefix + playerIsAlreadyInAdventure);
                                }
                            } else if (args[0].equalsIgnoreCase("3")) {
                                if (target.getGameMode() != GameMode.SPECTATOR) {
                                    target.setGameMode(GameMode.SPECTATOR);
                                    String changedPlayerGameModeToSpectator = plugin.GameModeSpectatorAnntotherPlayerMSG;
                                    changedPlayerGameModeToSpectator = changedPlayerGameModeToSpectator.replace("%player%", p.getDisplayName());
                                    target.sendMessage(plugin.Prefix + changedPlayerGameModeToSpectator);
                                    String changedFromPlayerGameModeToSpectator = plugin.GameModeSetInSpectatorAnnotherPlayerMSG;
                                    changedFromPlayerGameModeToSpectator = changedFromPlayerGameModeToSpectator.replace("%player%", target.getDisplayName());
                                    p.sendMessage(plugin.Prefix + changedFromPlayerGameModeToSpectator);
                                    return true;
                                } else {
                                    String playerIsAlreadyInSpectator = plugin.GameModeAlreadyPlayerInSpectatorMSG;
                                    playerIsAlreadyInSpectator = playerIsAlreadyInSpectator.replace("%player%", target.getDisplayName());
                                    p.sendMessage(plugin.Prefix + playerIsAlreadyInSpectator);
                                }
                            }
                        }
                    } else {
                        p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "gm <0, 1, 2, 3>");
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.PlayerNotExist);
                }
            } else {
                p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
            }
        } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
        }
        return false;
    }
}
