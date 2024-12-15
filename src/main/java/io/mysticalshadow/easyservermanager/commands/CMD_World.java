package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class CMD_World implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_World (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("world").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
                if (args.length == 0) {
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "world create <world> <type>");
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "world tp <world>");
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "world remove <world>");
                    return true;
                }
                if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("create")) {
                        if (p.hasPermission(plugin.PermsWorldCreate) || p.hasPermission(plugin.PermSternchen)) {
                            String worldName = args[1];
                            World w = Bukkit.getWorld(worldName);
                            if (w == null) {
                                String worldCreated = plugin.WorldCreatedMSG;
                                worldCreated = worldCreated.replace("%world%", worldName);
                                if (args[2].equalsIgnoreCase("normal")) {
                                    Bukkit.createWorld(WorldCreator.name(worldName).type(WorldType.NORMAL));
                                    p.sendMessage(plugin.Prefix + worldCreated);
                                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "world tp " + worldName);
                                    return true;
                                } else if (args[2].equalsIgnoreCase("flat")) {
                                    Bukkit.createWorld(WorldCreator.name(worldName).type(WorldType.FLAT));
                                    p.sendMessage(plugin.Prefix + worldCreated);
                                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "world tp " + worldName);
                                    return true;
                                } else if (args[2].equalsIgnoreCase("large")) {
                                    Bukkit.createWorld(WorldCreator.name(worldName).type(WorldType.LARGE_BIOMES));
                                    p.sendMessage(plugin.Prefix + worldCreated);
                                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "world tp " + worldName);
                                    return true;
                                }
                            } else {
                                String worldAlreadyExist = plugin.WorldAlreadyExistMSG;
                                worldAlreadyExist = worldAlreadyExist.replace("%world%", worldName);
                                p.sendMessage(plugin.Prefix + worldAlreadyExist);
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                            return true;
                        }
                    }
                } else if (args[0].equalsIgnoreCase("create")) {
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "world create <world> <normal, flat, large>");
                    return true;
                }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("tp")) {
                        if (p.hasPermission(plugin.PermsWorldTP) || p.hasPermission(plugin.PermSternchen)) {
                            String worldName = args[1];
                            World w = Bukkit.getWorld(worldName);
                            if (w != null) {
                                p.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
                                String worldTeleported = plugin.YourTeleportToTheWorldMSG;
                                worldTeleported = worldTeleported.replace("%world%", worldName);
                                p.sendMessage(plugin.Prefix + worldTeleported);
                                return true;
                            } else {
                                String worldNotExist = plugin.WorldDoesNotExistMSG;
                                worldNotExist = worldNotExist.replace("%world%", worldName);
                                p.sendMessage(plugin.Prefix + worldNotExist);
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                            return true;
                        }
                    }
                } else if (args[0].equalsIgnoreCase("tp")) {
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "world tp <world>");
                    return true;
                }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("remove")) {
                        if (p.hasPermission(plugin.PermsWorldRemove) || p.hasPermission(plugin.PermSternchen)) {
                            String worldName = args[1];
                            World w = Bukkit.getWorld(worldName);
                            if (w != null) {
                                Bukkit.unloadWorld(worldName, true);
                                deleteFolder(w.getWorldFolder().toPath());
                                String worldRemoved = plugin.YourRemovedTheWorldMSG;
                                worldRemoved = worldRemoved.replace("%world%", worldName);
                                p.sendMessage(plugin.Prefix + worldRemoved);
                                return true;
                            } else {
                                String worldNotExist = plugin.WorldDoesNotExistMSG;
                                worldNotExist = worldNotExist.replace("%world%", worldName);
                                p.sendMessage(plugin.Prefix + worldNotExist);
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                            return true;
                        }
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "world remove <world>");
                    return true;
                }
        }
        return false;
    }
    private void deleteFolder (Path path) {
        try {
            Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
