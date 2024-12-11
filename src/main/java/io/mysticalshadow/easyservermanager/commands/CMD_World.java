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
            if (p.hasPermission("")) {
                if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("create")) {
                        String worldName = args[1];
                        World w = Bukkit.getWorld(worldName);
                        if (w == null) {
                            if (args[2].equalsIgnoreCase("normal")) {
                                Bukkit.createWorld(WorldCreator.name(worldName).type(WorldType.NORMAL));
                                p.sendMessage(plugin.Prefix + "§2The world generation was successfully!");
                                p.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
                                return true;
                            } else if (args[2].equalsIgnoreCase("flat")) {
                                Bukkit.createWorld(WorldCreator.name(worldName).type(WorldType.FLAT));
                                p.sendMessage(plugin.Prefix + "§2The world generation was successfully!");
                                p.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
                                return true;
                            } else if (args[2].equalsIgnoreCase("large")) {
                                Bukkit.createWorld(WorldCreator.name(worldName).type(WorldType.LARGE_BIOMES));
                                p.sendMessage(plugin.Prefix + "§2The world generation was successfully!");
                                p.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
                                return true;
                            }
                        } else {
                            p.sendMessage(plugin.Prefix + "§4This world already exist!");
                        }
                    }
                }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("tp")) {
                        String worldName = args[1];
                        World w = Bukkit.getWorld(worldName);
                        if (w != null) {
                            p.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
                            p.sendMessage(plugin.Prefix + "§2You successfully teleported to the world §3" + worldName + "§2!");
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + "§4This world does not exist!");
                        }
                    }
                }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("remove")) {
                        String worldName = args[1];
                        World w = Bukkit.getWorld(worldName);
                        if (w != null) {
                            Bukkit.unloadWorld(worldName, true);
                            deleteFolder(w.getWorldFolder().toPath());
                            p.sendMessage(plugin.Prefix + "§2You successfully deleted the World §3" + worldName + "§2!");
                            return true;
                        } else {
                            p.sendMessage(plugin.Prefix + "§4The world §c" + worldName + " §4does not exist!");
                        }
                    }
                }
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
