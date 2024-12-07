package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CMD_Reward implements CommandExecutor {

    private EasyServerManager plugin;
    public CMD_Reward (EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("reward").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            File file = new File("plugins//EasyServerManager//Players", p.getUniqueId() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            if (args.length == 0) {
                SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
                String orginal = date.format(new Date());
                    if (!config.getBoolean(p.getName() + ".Rewards.Pickup.Date." + orginal)) {
                        config.set((p.getName()) + ".Rewards.Pickup.Date." + orginal, true);
                        try {
                            config.save(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        ItemStack random = null;
                        Random r = new Random();
                        int zufall = r.nextInt(15);
                        switch (zufall) {
                            case 0:
                                random = new ItemStack(Material.ANVIL, 1);
                                break;
                            case 1:
                                random = new ItemStack(Material.NETHERITE_BLOCK, 4);
                                break;
                            case 2:
                                random = new ItemStack(Material.DIAMOND, 16);
                                break;
                            case 3:
                                random = new ItemStack(Material.COOKED_BEEF, 16);
                                break;
                            case 4:
                                random = new ItemStack(Material.ROTTEN_FLESH, 32);
                                break;
                            case 5:
                                random = new ItemStack(Material.RAW_GOLD_BLOCK, 8);
                                break;
                            case 6:
                                random = new ItemStack(Material.EXPERIENCE_BOTTLE, 32);
                                break;
                            case 7:
                                random = new ItemStack(Material.ENDER_PEARL, 8);
                                break;
                            case 8:
                                random = new ItemStack(Material.BEACON, 1);
                                break;
                            case 9:
                                random = new ItemStack(Material.DIRT, 64);
                                break;
                            case 10:
                                random = new ItemStack(Material.VILLAGER_SPAWN_EGG, 2);
                                break;
                            case 11:
                                random = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 8);
                                break;
                            case 12:
                                random = new ItemStack(Material.ENDER_CHEST, 1);
                                break;
                            case 13:
                                random = new ItemStack(Material.LEATHER, 64);
                                break;
                            case 14:
                                random = new ItemStack(Material.RAW_IRON_BLOCK, 8);
                                break;
                        }
                        p.getInventory().addItem(random);
                        p.sendMessage(plugin.Prefix + plugin.YouGotRewardMSG);
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (all.hasPermission(plugin.PermRewardNotify) || all.hasPermission(plugin.PermSternchen)) {
                                String gotRewardNotify = plugin.GotPlayerRewardNotify;
                                gotRewardNotify = gotRewardNotify.replace("%player%", p.getDisplayName());
                                all.sendMessage(plugin.Prefix + gotRewardNotify);
                                return true;
                            }
                        }
                        return true;
                    } else if (file.exists()) {
                        try {
                            config.load(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        p.sendMessage(plugin.Prefix + plugin.AlreadyPickupRewardMSG);
                    } else {
                        config.set((p.getName()) + ".Rewards.Pickup.Date.", "dd/mm/yyyy");
                        try {
                            config.save(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        p.sendMessage(plugin.Prefix + plugin.ThePathNotFoundMSG);
                    }
                } else {
                    p.sendMessage(plugin.Prefix + plugin.UseCommandMSG + "reward");
                }
            } else {
            sender.sendMessage(plugin.Prefix + plugin.OnlyRealPlayer);
            }
        return false;
    }
}