package io.siedlermc.easysiedlermanager;

import io.siedlermc.easysiedlermanager.listener.InventoryManager;
import io.siedlermc.easysiedlermanager.listener.PlayerManager;
import io.siedlermc.easysiedlermanager.listener.ServerManager;
import io.siedlermc.easysiedlermanager.manager.JailManager;
import io.siedlermc.easysiedlermanager.manager.MaintenanceManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import io.siedlermc.easysiedlermanager.commands.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EasySiedlerManager extends JavaPlugin {

    public static EasySiedlerManager instance;
    public static List<Player> vanish = new ArrayList<>();
    public static List<Player> fly = new ArrayList<>();
    public static List<Player> pvp = new ArrayList<>();
    public HashMap<String, Boolean> titleOnPlayerJoin = new HashMap<>();
    public boolean broadcast;

    private PlayerManager playerManager;
    private InventoryManager inventoryManager;
    private ServerManager serverManager;

    private CMD_GameMode gameMode;
    private CMD_Kick kick;
    private CMD_ChatClear chatClear;
    private CMD_Teleport teleport;
    private CMD_Vanish cmdVanish;
    private CMD_Time cmdTime;
    private CMD_Wetter cmdWetter;
    private CMD_Invsee cmdInvsee;
    private CMD_TPHere cmdTPHere;
    private CMD_Heal cmdHeal;
    private CMD_Ping cmdPing;
    private CMD_Alert cmdAlert;
    private CMD_Clear cmdClear;
    //private CMD_Admin cmdAdmin;
    private CMD_TPA cmdTPA;
    private CMD_Fly cmdFly;
    private CMD_Farm cmdFarm;
    private CMD_Spawn cmdSpawn;
    private CMD_GodMode cmdGodMode;
    private CMD_Maintenance cmdMaintenance;
    private CMD_EnderChest cmdEnderChest;
    private CMD_SetJail cmdSetJail;
    private CMD_Home cmdHome;
    private CMD_ListHome cmdListHome;
    private CMD_Reward cmdReward;
    private CMD_Deposit cmdDeposit;
    private CMD_Payout cmdPayout;
    private CMD_Pay cmdPay;
    private CMD_PvP cmdPvP;
    private CMD_Jail cmdJail;
    private CMD_Back cmdBack;
    private CMD_Level cmdLevel;

    @Override
    public void onEnable() {

        // Utils \\
        this.instance = this;
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveResource("config.yml", true);
        }
        try {
            MaintenanceManager.savecfg();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            JailManager.savecfg();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Listener \\
        this.playerManager = new PlayerManager(this);
        this.serverManager = new ServerManager(this);
        //this.inventoryManager = new InventoryManager(this);

        // Commands \\
        this.gameMode = new CMD_GameMode(this);
        this.kick = new CMD_Kick(this);
        this.chatClear = new CMD_ChatClear(this);
        this.teleport = new CMD_Teleport(this);
        this.cmdVanish = new CMD_Vanish(this);
        this.cmdTime = new CMD_Time(this);
        this.cmdWetter = new CMD_Wetter(this);
        this.cmdInvsee = new CMD_Invsee(this);
        this.cmdTPHere = new CMD_TPHere(this);
        this.cmdHeal = new CMD_Heal(this);
        this.cmdPing = new CMD_Ping(this);
        this.cmdAlert = new CMD_Alert(this);
        this.cmdClear = new CMD_Clear(this);
        //this.cmdAdmin = new CMD_Admin(this);
        this.cmdFly = new CMD_Fly(this);
        this.cmdTPA = new CMD_TPA(this);
        this.cmdFarm = new CMD_Farm(this);
        this.cmdSpawn = new CMD_Spawn(this);
        this.cmdGodMode = new CMD_GodMode(this);
        this.cmdMaintenance = new CMD_Maintenance(this);
        this.cmdEnderChest = new CMD_EnderChest(this);
        this.cmdSetJail = new CMD_SetJail(this);
        this.cmdHome = new CMD_Home(this);
        this.cmdListHome = new CMD_ListHome(this);
        this.cmdReward = new CMD_Reward(this);
        this.cmdDeposit = new CMD_Deposit(this);
        this.cmdPayout = new CMD_Payout(this);
        this.cmdPay = new CMD_Pay(this);
        this.cmdPvP = new CMD_PvP(this);
        this.cmdJail = new CMD_Jail(this);
        this.cmdBack = new CMD_Back(this);
        this.cmdLevel = new CMD_Level(this);

        // Enable Message \\

        Bukkit.getConsoleSender().sendMessage("§l");
        Bukkit.getConsoleSender().sendMessage("§9---------- §3§lEasy§b§lSiedler§3§lManager §9----------");
        Bukkit.getConsoleSender().sendMessage("§l ");
        Bukkit.getConsoleSender().sendMessage("§7Version: §e" + this.instance.getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("§7Authors: §e" + this.instance.getDescription().getAuthors());
        Bukkit.getConsoleSender().sendMessage("§7Status: §eAktiviert");
        Bukkit.getConsoleSender().sendMessage("§l  ");
        Bukkit.getConsoleSender().sendMessage("§9----------------------------------------");
    }

    @Override
    public void onDisable() {

        Bukkit.getConsoleSender().sendMessage("§l");
        Bukkit.getConsoleSender().sendMessage("§9---------- §3§lEasy§b§lSiedler§3§lManager §9----------");
        Bukkit.getConsoleSender().sendMessage("§l ");
        Bukkit.getConsoleSender().sendMessage("§7Version: §e" + this.instance.getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("§7Authors: §e" + this.instance.getDescription().getAuthors());
        Bukkit.getConsoleSender().sendMessage("§7Status: §4Deaktiviert");
        Bukkit.getConsoleSender().sendMessage("§l  ");
        Bukkit.getConsoleSender().sendMessage("§9----------------------------------------");
    }
    public static EasySiedlerManager getInstance() {
        return instance;
    }
    public String Prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("SiedlerManager.Prefix"));
    public String Farminworld = ChatColor.translateAlternateColorCodes('&', getConfig().getString("SiedlerManager.Farminworld"));
    public boolean AllowJoinMessage = getConfig().getBoolean("SiedlerManager.AllowJoinMessage");
    public int MaxPlayer = getConfig().getInt("SiedlerManager.MaxPlayers");
    public String JoinMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.JoinMessage"));
    public String MOTDHeader = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.MOTDHeader"));
    public String MOTDFooter = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.MOTDFooter"));
    public String MOTDMaintenance = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.MOTDMaintenance"));
    public String Discord = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.Discord"));
    public boolean AllowQuitMessage = getConfig().getBoolean("SiedlerManager.AllowQuitMessage");
    public String QuitMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.QuitMessage"));
    public String User = ChatColor.translateAlternateColorCodes('&', getConfig().getString("MySQL.Username"));
    public String Password = ChatColor.translateAlternateColorCodes('&', getConfig().getString("MySQL.Password"));
    public String Host = ChatColor.translateAlternateColorCodes('&', getConfig().getString("MySQL.Host"));
    public String Datenbank = ChatColor.translateAlternateColorCodes('&', getConfig().getString("MySQL.Datenbank"));
    public String Port = ChatColor.translateAlternateColorCodes('&', getConfig().getString("MySQL.Port"));
}