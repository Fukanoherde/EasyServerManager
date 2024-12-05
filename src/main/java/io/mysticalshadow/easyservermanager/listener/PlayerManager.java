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
import java.util.Date;

public class PlayerManager implements Listener {

    private EasyServerManager plugin;
    public PlayerManager(EasyServerManager plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        String path = "SiedlerManager" + ".";
        if (MaintenanceManager.config.getBoolean(path + "Maintenance", Boolean.valueOf(true))) {
            if (p.hasPermission("siedlermanager.maintenance") || p.hasPermission("siedlermanager.*")) {
                return;
            } else {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§3SiedlerMC\n§aDu wurdest gekickt\n§4GRUND: §cWartungsarbeiten\n§3gekickt worden vom: §4System");
            }
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        File fileplayer = new File("plugins//EasyServerManager//Players", p.getUniqueId().toString() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(fileplayer);
        for (int i = 0; i <= 1000; i++) {
            p.sendMessage(" ");
        }
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String orginal = date.format(new Date());
        p.setPlayerListHeaderFooter("§3Willkommen auf \n§3SiedlerMC\n\n§3Datum §2" + orginal + "\n\n§4§l----------------------------------\n","\n§4§l----------------------------------\n\n§3Unser Discord " + plugin.Discord + "\n§3Live-Map §2siedlermc.de:8123");
        p.sendTitle("§3Welcome on the", "§2Server: §4§lSiedlerMC", 45, 45, 45);
        if (!fileplayer.exists()) {
            config.set(p.getName() + ".Rewards.Pickup.Date.", null);
            config.set(p.getName() + ".Level", 0);
            config.set(p.getName() + ".Jail.Status", false);
            config.set(p.getName() + ".PvP.Activated", false);
            config.set(p.getName() + ".Homes", null);
            try {
                config.save(fileplayer);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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
            config.load(fileplayer);
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
                p.sendMessage(plugin.Prefix + "§4Your jailed. you cannot break this Block!");
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
                p.sendMessage(plugin.Prefix + "§4Your jailed. you cannot place this Block!");
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
        File file = new File("plugins//EasyServerManager//Players", p.getUniqueId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        try {
            config.load(file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidConfigurationException ex) {
            throw new RuntimeException(ex);
        }
        String world = p.getLastDeathLocation().getWorld().getName();
        double x = p.getLastDeathLocation().getX();
        double z = p.getLastDeathLocation().getZ();
        double y = p.getLastDeathLocation().getY();
        config.set(p.getName() + ".Death.World", world);
        config.set(p.getName() + ".Death.X", x);
        config.set(p.getName() + ".Death.Z", z);
        config.set(p.getName() + ".Death.Y", y);
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (config.getBoolean(p.getName() + ".Jail.Status", true)) {
            if (config.isSet(p.getName() + ".Jail.Location")) {
                String name = config.getString(p.getName() + ".Jail.Location.Name");
                String worldJail = config.getString(p.getName() + ".Jail.Location.Welt");
                double xJail = config.getDouble(p.getName() + ".Jail.Location.X");
                double yJail = config.getDouble(p.getName() + ".Jail.Location.Y");
                double zJail = config.getDouble(p.getName() + ".Jail.Location.Z");
                float yaw = (float) config.getDouble(p.getName() + ".Jail.Location.Yaw");
                float pitch = (float) config.getDouble(p.getName() + ".Jail.Location.Pitch");
                assert worldJail != null;
                Location location = new Location(Bukkit.getWorld(worldJail), xJail, yJail, zJail, yaw, pitch);
                p.setBedSpawnLocation(location);
            }
            if (target != null) {
                Bukkit.broadcastMessage("§8the player §3" + p.getDisplayName() + " §8has killed by §3" + target.getDisplayName() + "38!");
            } else {
                Bukkit.broadcastMessage("§8the player §3" + p.getDisplayName() + " §8is died!");
            }
        } else {
            p.setRespawnLocation(p.getBedSpawnLocation());
            if (target != null) {
                Bukkit.broadcastMessage("§8the player §3" + p.getDisplayName() + " §8has killed by §3" + target.getDisplayName() + "38!");
            } else {
                Bukkit.broadcastMessage("§8the player §3" + p.getDisplayName() + " §8is died!");
            }
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
        String msg = e.getMessage();
        if (p.hasPermission("prefix.owner")) {
            e.setFormat("§8[§4Inhaber§8] " + p.getDisplayName() + " §7» " + msg);
        } else if (p.hasPermission("prefix.admin")) {
            e.setFormat("§8[§cAdmin§8] " + p.getDisplayName() + " §7» " + msg);
        } else if (p.hasPermission("prefix.srdev")) {
            e.setFormat("§8[§bSrDev§8] " + p.getDisplayName() + " §7» " + msg);
        } else if (p.hasPermission("prefix.dev")) {
            e.setFormat("§8[§bDev§8] " + p.getDisplayName() + " §7» " + msg);
        } else if (p.hasPermission("prefix.tdev")) {
            e.setFormat("§8[§bT-Dev§8] " + p.getDisplayName() + " §7» " + msg);
        } else if (p.hasPermission("prefix.srmod")) {
            e.setFormat("§8[§3SrMod§8] " + p.getDisplayName() + " §7» " + msg);
        } else if (p.hasPermission("prefix.mod")) {
            e.setFormat("§8[§3Mod§8] " + p.getDisplayName() + " §7» " + msg);
        } else if (p.hasPermission("prefix.tmod")) {
            e.setFormat("§8[§3T-Mod§8] " + p.getDisplayName() + " §7» " + msg);
        } else if (p.hasPermission("prefix.srsupporter")) {
            e.setFormat("§8[§1SrSupporter§8] " + p.getDisplayName() + " §7» " + msg);
        } else if (p.hasPermission("prefix.supporter")) {
            e.setFormat("§8[§1Supporter§8] " + p.getDisplayName() + " §7» " + msg);
        } else if (p.hasPermission("prefix.tsupporter")) {
            e.setFormat("§8[§1T-Supporter§8] " + p.getDisplayName() + " §7» " + msg);
        } else if (p.hasPermission("prefix.srbuilder")) {
            e.setFormat("§8[§aSrBuilder§8] " + p.getDisplayName() + " §7» " + msg);
        } else if (p.hasPermission("prefix.builder")) {
            e.setFormat("§8[§aBuilder§8] " + p.getDisplayName() + " §7» " + msg);
        } else if (p.hasPermission("prefix.tbuilder")) {
            e.setFormat("§8[§aT-Builder§8] " + p.getDisplayName() + " §7» " + msg);
        } else if (p.hasPermission("prefix.youtuber")) {
            e.setFormat("§5" + p.getDisplayName() + " §7» " + msg);
        } else if (p.hasPermission("prefix.premium")) {
            e.setFormat("§6" + p.getDisplayName() + " §7» " + msg);
        } else {
            e.setFormat("§8[§8Einwohner§8] " + p.getDisplayName() + " §7» " + msg);
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
            if (!(p.hasPermission("siedlermanager.plugins") || p.hasPermission("siedlermanager.*"))) {
                p.sendMessage(plugin.Prefix + "§cYou don't have permission to use this command!");
                e.setCancelled(true);
            } else {
                return;
            }
        }
    }
}