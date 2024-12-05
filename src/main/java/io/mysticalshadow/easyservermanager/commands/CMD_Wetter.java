package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Wetter implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Wetter(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("weather").setExecutor(this);
    }

        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

            // Prüft, ob der Sender ein Spieler ist
            if (sender instanceof Player) {
                Player p = (Player) sender;

                // Prüft, ob der Spieler die Berechtigung zum Ändern des Wetters hat
                if (p.hasPermission("siedlermanager.weather") || p.hasPermission("siedlermanager.*")) {

                    // Prüft, ob ein Wettertyp als Argument angegeben wurde
                    if (args.length == 1) {
                        String wetterTyp = args[0].toLowerCase();

                        switch (wetterTyp) {
                            case "sun":
                                // Setzt das Wetter auf klar
                                Bukkit.getWorld(p.getLocation().getWorld().getName()).setStorm(false);
                                p.sendMessage(plugin.Prefix + "§3The weather change to sun");
                                break;

                            case "rain":
                                // Setzt das Wetter auf Regen
                                Bukkit.getWorld(p.getLocation().getWorld().getName()).setStorm(true);
                                p.sendMessage(plugin.Prefix + "§3The weather change to rain!");
                                break;

                            case "thunder":
                                // Setzt das Wetter auf Gewitter
                                Bukkit.getWorld(p.getLocation().getWorld().getName()).setStorm(true);
                                Bukkit.getWorld(p.getLocation().getWorld().getName()).setThundering(true);
                                p.sendMessage(plugin.Prefix + "§3The weather change to thunder!");
                                break;

                            default:
                                p.sendMessage(plugin.Prefix + "§bUse command §8/weather <sun, rain, thunder>");
                                return false;
                        }
                        return true;
                    } else {
                        p.sendMessage(plugin.Prefix + "§c§bUse command §8/weather <sun, rain, thunder>");
                    }
                } else {
                    p.sendMessage(plugin.Prefix + "§cYou don't have permission to use this command!");
                }
            } else {
                sender.sendMessage(plugin.Prefix + "§4Error: §cThis command cannot be used");
            }

            return false;
        }
}