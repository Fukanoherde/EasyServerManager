package io.mysticalshadow.easyservermanager.commands;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import io.mysticalshadow.easyservermanager.api.ItemAPI;
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
                        int zufall = r.nextInt(20);
                        switch (zufall) {
                            case 0:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.OneItem), plugin.OneAmount, 0, plugin.OneName);
                                break;
                            case 1:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.TwoItem), plugin.TwoAmount, 0, plugin.TwoName);
                                break;
                            case 2:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.ThreeItem), plugin.ThreeAmount, 0, plugin.ThreeName);
                                break;
                            case 3:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.ThreeItem), plugin.ThreeAmount, 0, plugin.ThreeName);
                                break;
                            case 4:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.FourItem), plugin.FourAmount, 0, plugin.FourName);
                                break;
                            case 5:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.FiveItem), plugin.FiveAmount, 0, plugin.FiveName);
                                break;
                            case 6:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.SixItem), plugin.SixAmount, 0, plugin.SixName);
                                break;
                            case 7:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.SevenItem), plugin.SevenAmount, 0, plugin.SevenName);
                                break;
                            case 8:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.EightItem), plugin.EightAmount, 0, plugin.EightName);
                                break;
                            case 9:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.NineItem), plugin.NineAmount, 0, plugin.NineName);
                                break;
                            case 10:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.TenItem), plugin.TenAmount, 0, plugin.TenName);
                                break;
                            case 11:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.ElevenItem), plugin.ElevenAmount, 0, plugin.ElevenName);
                                break;
                            case 12:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.TwelveItem), plugin.TwelveAmount, 0, plugin.TwelveName);
                                break;
                            case 13:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.ThirteenItem), plugin.ThirteenAmount, 0, plugin.ThirteenName);
                                break;
                            case 14:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.FourteenItem), plugin.FourteenAmount, 0, plugin.FourteenName);
                                break;
                            case 15:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.FifteenItem), plugin.FifteenAmount, 0, plugin.FifteenName);
                                break;
                            case 16:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.SixteenItem), plugin.SixteenAmount, 0, plugin.SixteenName);
                                break;
                            case 17:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.SeventeenItem), plugin.SeventeenAmount, 0, plugin.SeventeenName);
                                break;
                            case 18:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.EighteenItem), plugin.EighteenAmount, 0, plugin.EighteenName);
                                break;
                            case 19:
                                random = ItemAPI.createItemNoEnch(Material.valueOf(plugin.NineteenItem), plugin.NineteenAmount, 0, plugin.NineteenName);
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