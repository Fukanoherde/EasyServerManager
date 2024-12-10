package io.mysticalshadow.easyservermanager.listener;

import io.mysticalshadow.easyservermanager.EasyServerManager;
import io.mysticalshadow.easyservermanager.commands.CMD_GodMode;
import io.mysticalshadow.easyservermanager.manager.MaintenanceManager;
import io.mysticalshadow.easyservermanager.manager.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerManager implements Listener {

    private EasyServerManager plugin;
    public PlayerManager(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        String path = plugin.ServerName + ".";
        if (MaintenanceManager.config.getBoolean(path + "Maintenance", Boolean.valueOf(true))) {
            if (p.hasPermission(plugin.PermMaintenanceJoin) || p.hasPermission(plugin.PermSternchen)) {
                return;
            } else {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, plugin.KickPlayerWhenActivateMaintenance);
            }
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        File file = new File("plugins//EasyServerManager//Players", p.getUniqueId().toString() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (int i = 0; i <= 1000; i++) {
            p.sendMessage(" ");
        }
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String orginal = date.format(new Date());
        String currentDateHeader = plugin.TabHeader;
        currentDateHeader = currentDateHeader.replace("%date%", orginal);
        p.setPlayerListHeaderFooter(currentDateHeader.replaceAll("&", "§"), plugin.TabFooter.replaceAll("&", "§"));
        p.sendTitle(plugin.TitleHeader.replaceAll("&", "§"), plugin.TitleFooter.replaceAll("&", "§"), 45, 45, 45);
        if (!file.exists()) {
            config.set(p.getName() + ".Rewards.Pickup.Date.", null);
            config.set(p.getName() + ".Level", 0);
            config.set(p.getName() + ".Jail.Status", false);
            config.set(p.getName() + ".PvP.Activated", false);
            config.set(p.getName() + ".LastLogin", null);
            config.set(p.getName() + ".Homes", null);
            try {
                config.save(file);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        config.set(p.getName() + ".LastLogin.Date", null);
        config.set(p.getName() + ".LastLogin.Clock", null);
        try {
            config.save(file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    ScoreboardManager.setBoard(all);
                    ScoreboardManager.updateBoard(all);
                }
            }
        }.runTaskLater(plugin, 1L);
        if (!p.hasPlayedBefore()) {
            ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
            ItemStack leggins = new ItemStack(Material.LEATHER_LEGGINGS);
            ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
            ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
            ItemStack axe = new ItemStack(Material.WOODEN_AXE, 1);
            ItemStack sword = new ItemStack(Material.WOODEN_SWORD, 1);
            ItemStack pickaxe = new ItemStack(Material.WOODEN_PICKAXE, 1);
            ItemStack steak = new ItemStack(Material.COOKED_BEEF, 64);
            p.getInventory().setBoots(boots);
            p.getInventory().setLeggings(leggins);
            p.getInventory().setChestplate(chestplate);
            p.getInventory().setHelmet(helmet);
            p.getInventory().addItem(axe);
            p.getInventory().addItem(sword);
            p.getInventory().addItem(pickaxe);
            p.getInventory().addItem(steak);
        }
        try {
            config.load(file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidConfigurationException ex) {
            throw new RuntimeException(ex);
        }
        if (config.getBoolean(p.getName() + ".Jail.Status", Boolean.valueOf(true))) {
            if (config.isSet(p.getName() + ".Jail.Location")) {
                String name = config.getString(p.getName() + ".Jail.Location.Name");
                String world = config.getString(p.getName() + ".Jail.Location.Welt");
                double x = config.getDouble(p.getName() + ".Jail.Location.X");
                double y = config.getDouble(p.getName() + ".Jail.Location.Y");
                double z = config.getDouble(p.getName() + ".Jail.Location.Z");
                float yaw = (float) config.getDouble(p.getName() + ".Jail.Location.Yaw");
                float pitch = (float) config.getDouble(p.getName() + ".Jail.Location.Pitch");
                assert world != null;
                Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                p.teleport(location);
            } else {
                p.sendMessage(plugin.Prefix + "§4The jail Location does not Found!");
            }
        }
        if (plugin.AllowJoinMessage == true) {
            String join = plugin.JoinMessage;
            join = join.replace("%player%", e.getPlayer().getName());
            e.setJoinMessage("");
            Bukkit.broadcastMessage(join);
        } else {
            e.setJoinMessage("");
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    ScoreboardManager.setBoard(all);
                    ScoreboardManager.updateBoard(all);
                }
            }
        }.runTaskLater(plugin, 1L);
        File file = new File("plugins//EasyServerManager//Players", e.getPlayer().getUniqueId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String orginalDate = date.format(new Date());
        SimpleDateFormat hour = new SimpleDateFormat("dd/MM/yyyy");
        String clock = hour.format(new Date());
        config.set(e.getPlayer().getName() + ".LastLogin.Date", orginalDate);
        config.set(e.getPlayer().getName() + ".LastLogin.Clock", clock);
        try {
            config.save(file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        if (plugin.AllowQuitMessage == true) {
            String quit = plugin.QuitMessage;
            quit = quit.replace("%player%", e.getPlayer().getName());
            e.setQuitMessage("");
            Bukkit.broadcastMessage(quit);
        } else {
            e.setQuitMessage("");
        }
    }
    @EventHandler
    public void onBreak (BlockBreakEvent e) {
        Player p = (Player) e.getPlayer();
        File file = new File("plugins//EasyServerManager//Players", p.getUniqueId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        try {
            config.load(file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidConfigurationException ex) {
            throw new RuntimeException(ex);
        }
        if (config.isSet(p.getName() + ".Jail.Status")) {
            if (config.getBoolean(p.getName() + ".Jail.Status", Boolean.valueOf(true))) {
                e.setCancelled(true);
                p.sendMessage(plugin.Prefix + plugin.CurrentlyJailed);
            } else {
                return;
            }
        } else {
            return;
        }
    }
    @EventHandler
    public void onPlace (BlockPlaceEvent e) {
        Player p = (Player) e.getPlayer();
        File file = new File("plugins//EasyServerManager//Players", p.getUniqueId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        try {
            config.load(file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidConfigurationException ex) {
            throw new RuntimeException(ex);
        }
        if (config.isSet(p.getName() + ".Jail.Status")) {
            if (config.getBoolean(p.getName() + ".Jail.Status", Boolean.valueOf(true))) {
                e.setCancelled(true);
                p.sendMessage(plugin.Prefix + plugin.CurrentlyJailed);
            } else {
                return;
            }
        } else {
            return;
        }
    }
    @EventHandler
    public void onFood (FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        File file = new File("plugins//EasyServerManager//Players", p.getUniqueId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        try {
            config.load(file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidConfigurationException ex) {
            throw new RuntimeException(ex);
        }
        if (config.isSet(p.getName() + ".Jail.Status")) {
            if (config.getBoolean(p.getName() + ".Jail.Status", Boolean.valueOf(true))) {
                e.setCancelled(true);
            } else {
                return;
            }
        } else {
            return;
        }
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        Player target = event.getEntity().getKiller();
        event.setDeathMessage("");
        if (target != null) {
            String playerKilledByPlayer = plugin.PlayerKilledByPlayerMSG;
            playerKilledByPlayer = playerKilledByPlayer.replace("%player%", p.getDisplayName());
            playerKilledByPlayer = playerKilledByPlayer.replace("%killer%", target.getDisplayName());
            Bukkit.broadcastMessage(playerKilledByPlayer);
        } else {
            String playerDeath = plugin.PlayerDeathMSG;
            playerDeath = playerDeath.replace("%player%", p.getDisplayName());
            Bukkit.broadcastMessage(playerDeath);
        }
    }
    @EventHandler
    public void onDamaged (EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (CMD_GodMode.godMode.contains(p)) {
                e.setCancelled(true);
            } else {
                e.setCancelled(false);
            }
        }
    }
    @EventHandler
    public void DamageByPlayer (EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player damaged = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            File file = new File("plugins//EasyServerManager//Players", damaged.getUniqueId() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                try {
                    config.load(file);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidConfigurationException ex) {
                    throw new RuntimeException(ex);
                }
                if (config.getBoolean(damaged.getName() + ".PvP.Activated", Boolean.valueOf(true))) {
                    damager.sendMessage(plugin.Prefix + "§4The player §c" + damaged.getName() + " §4has deactivated pvp!");
                    e.setCancelled(true);
                } else {
                    e.setCancelled(false);
                }
            } else {
                return;
            }
        } else {
            return;
        }
    }
    @EventHandler
    public void onChat (AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        File file = new File("plugins//EasyServerManager//Players", p.getUniqueId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        try {
            config.load(file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidConfigurationException ex) {
            throw new RuntimeException(ex);
        }
        if (config.getBoolean(p.getName() + ".Jail.Status", Boolean.valueOf(true))) {
            e.setCancelled(true);
            p.sendMessage(plugin.Prefix + plugin.CurrentlyJailed);
        } else {
            String msg = e.getMessage();
            for (String block : plugin.getConfig().getStringList("deniedWords")) {
                if (msg.contains(block)) {
                    String replace = "";
                    for (int i = 0; i < block.length(); i++) {
                        replace = replace + "*";
                    }
                    String newmsg = msg.replace(block, replace);
                    if (p.hasPermission(plugin.PermPrefixOwner)) {
                        String ownerPrefix = plugin.PrefixOwner;
                        ownerPrefix = ownerPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(ownerPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixCoOwner)) {
                        String coOwnerPrefix = plugin.PrefixCoOwner;
                        coOwnerPrefix = coOwnerPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(coOwnerPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixSrAdmin)) {
                        String srAdminPrefix = plugin.PrefixSrAdmin;
                        srAdminPrefix = srAdminPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(srAdminPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixAdmin)) {
                        String adminPrefix = plugin.PrefixAdmin;
                        adminPrefix = adminPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(adminPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixSrDeveloper)) {
                        String srDeveloperPrefix = plugin.PrefixSrDeveloper;
                        srDeveloperPrefix = srDeveloperPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(srDeveloperPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixDeveloper)) {
                        String developerPrefix = plugin.PrefixDeveloper;
                        developerPrefix = developerPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(developerPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixTestDeveloper)) {
                        String testDeveloperPrefix = plugin.PrefixTestDeveloper;
                        testDeveloperPrefix = testDeveloperPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(testDeveloperPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixSrModerator)) {
                        String SrModeratorPrefix = plugin.PrefixSrModerator;
                        SrModeratorPrefix = SrModeratorPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(SrModeratorPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixModerator)) {
                        String moderatorPrefix = plugin.PrefixModerator;
                        moderatorPrefix = moderatorPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(moderatorPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixTestModerator)) {
                        String testModeratorPrefix = plugin.PrefixTestModerator;
                        testModeratorPrefix = testModeratorPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(testModeratorPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixSrSupporter)) {
                        String srSupporterPrefix = plugin.PrefixSrSupporter;
                        srSupporterPrefix = srSupporterPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(srSupporterPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixSupporter)) {
                        String supporterPrefix = plugin.PrefixSupporter;
                        supporterPrefix = supporterPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(supporterPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixTestSupporter)) {
                        String testSupporterPrefix = plugin.PrefixTestSupporter;
                        testSupporterPrefix = testSupporterPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(testSupporterPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixSrBuilder)) {
                        String srBuilderPrefix = plugin.PrefixSrBuilder;
                        srBuilderPrefix = srBuilderPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(srBuilderPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixBuilder)) {
                        String builderPrefix = plugin.PrefixBuilder;
                        builderPrefix = builderPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(builderPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixTestBuilder)) {
                        String testBuilderPrefix = plugin.PrefixTestBuilder;
                        testBuilderPrefix = testBuilderPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(testBuilderPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixFriend)) {
                        String friendPrefix = plugin.PrefixFriend;
                        friendPrefix = friendPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(friendPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixYouTuber)) {
                        String youtuberPrefix = plugin.PrefixYouTuber;
                        youtuberPrefix = youtuberPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(youtuberPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixStreamer)) {
                        String streamerPrefix = plugin.PrefixStreamer;
                        streamerPrefix = streamerPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(streamerPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixPremiumThree)) {
                        String premiumThreePrefix = plugin.PrefixPremiumThree;
                        premiumThreePrefix = premiumThreePrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(premiumThreePrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixPremiumTwo)) {
                        String premiumTwoPrefix = plugin.PrefixPremiumTwo;
                        premiumTwoPrefix = premiumTwoPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(premiumTwoPrefix + newmsg);
                    } else if (p.hasPermission(plugin.PermPrefixPremiumOne)) {
                        String premiumOnePrefix = plugin.PrefixPremiumOne;
                        premiumOnePrefix = premiumOnePrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(premiumOnePrefix + newmsg);
                    } else {
                        String playerPrefix = plugin.PrefixPlayer;
                        playerPrefix = playerPrefix.replace("%player%", p.getDisplayName());
                        e.setFormat(playerPrefix + newmsg);
                    }
                    p.sendMessage(plugin.Prefix + "§4Pay attention to your choice of words.");
                    return;
                }
            }
        }
    }
    @EventHandler
    public void onReload(PlayerCommandPreprocessEvent e) {
        Player p = (Player) e.getPlayer();

        if (e.getMessage().equalsIgnoreCase("/rl")) {
            e.setCancelled(true);
            for (int i = 0; i <= 1000; i++) {
                Bukkit.broadcastMessage(" ");
            }
            Bukkit.broadcastMessage("§l ");
            Bukkit.broadcastMessage(plugin.Prefix + "§cPlease everyone stay standing!");
            Bukkit.reload();
            Bukkit.broadcastMessage(plugin.Prefix + "§aThe server has been reloaded!");
            Bukkit.broadcastMessage("§l  ");
        }
    }
    @EventHandler
    public void onBlockCommand (PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        File file = new File("plugins//EasyServerManager//Players", p.getUniqueId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        try {
            config.load(file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidConfigurationException ex) {
            throw new RuntimeException(ex);
        }
        if (config.getBoolean(p.getName() + ".Jail.Status", Boolean.valueOf(true))) {
            e.setCancelled(true);
            p.sendMessage(plugin.Prefix + plugin.CurrentlyJailed);
        }
        if (e.getMessage().equalsIgnoreCase("/pl") || e.getMessage().equalsIgnoreCase("/plugins")
                || e.getMessage().equalsIgnoreCase("/help") || e.getMessage().equalsIgnoreCase("/version")
                || e.getMessage().equalsIgnoreCase("/bukkit:pl") || e.getMessage().equalsIgnoreCase("/bukkit:plugins")
        || e.getMessage().equalsIgnoreCase("/bukkit:version") || e.getMessage().equalsIgnoreCase("/bukkit:help")
                || e.getMessage().equalsIgnoreCase("/?")
                || e.getMessage().equalsIgnoreCase("/bukkit:?")
                || e.getMessage().equalsIgnoreCase("/bukkit:ver")
                || e.getMessage().equalsIgnoreCase("/ver")
                || e.getMessage().equalsIgnoreCase("/bukkit:about")
                || e.getMessage().equalsIgnoreCase("/about")) {
            if (!(p.hasPermission(plugin.PermSeePlugins) || p.hasPermission(plugin.PermSternchen))) {
                p.sendMessage(plugin.Prefix + plugin.NoPermMessage);
                e.setCancelled(true);
            } else {
                return;
            }
        }
    }
}