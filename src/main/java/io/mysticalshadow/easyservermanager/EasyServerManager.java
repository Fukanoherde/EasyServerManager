package io.mysticalshadow.easyservermanager;

import io.mysticalshadow.easyservermanager.listener.PlayerManager;
import io.mysticalshadow.easyservermanager.listener.ServerManager;
import io.mysticalshadow.easyservermanager.commands.*;
import io.mysticalshadow.easyservermanager.manager.JailManager;
import io.mysticalshadow.easyservermanager.manager.MaintenanceManager;
import io.mysticalshadow.easyservermanager.manager.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EasyServerManager extends JavaPlugin {

    public static EasyServerManager instance;
    public static List<Player> vanish = new ArrayList<>();
    public static List<Player> fly = new ArrayList<>();
    public static List<Player> pvp = new ArrayList<>();
    public HashMap<String, Boolean> titleOnPlayerJoin = new HashMap<>();
    public boolean broadcast;

    private PlayerManager playerManager;
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
    private CMD_Warp cmdWarp;
    private CMD_SetWarp cmdSetWarp;
    private CMD_RemoveWarp cmdRemoveWarp;

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
        try {
            WarpManager.config.save(WarpManager.file);
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
        this.cmdWarp = new CMD_Warp(this);
        this.cmdSetWarp = new CMD_SetWarp(this);
        this.cmdRemoveWarp = new CMD_RemoveWarp(this);

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
    public static EasyServerManager getInstance() {
        return instance;
    }
    // Plugin Settings \\
    public String Prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("EasyServerManager.Prefix"));
    public String Farminworld = ChatColor.translateAlternateColorCodes('&', getConfig().getString("EasyServerManager.Farminworld"));
    public boolean AllowJoinMessage = getConfig().getBoolean("EasyServerManager.AllowJoinMessage");
    public boolean AllowQuitMessage = getConfig().getBoolean("EasyServerManager.AllowQuitMessage");
    public boolean AllowPlayTeleportSound = getConfig().getBoolean("EasyServerManager.AllowPlayTeleportSound");
    public int MaxPlayer = getConfig().getInt("EasyServerManager.MaxPlayers");
    public int MaxSaveLevel = getConfig().getInt("EasyServerManager.MaxSaveLevel");
    public String SaveType = ChatColor.translateAlternateColorCodes('&', getConfig().getString("EasyServerManager.SaveType"));
    public String ServerName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("EasyServerManager.ServerName"));

    // Messages \\
    public String JoinMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.JoinMessage"));
    public String MOTDHeader = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.MOTDHeader"));
    public String MOTDFooter = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.MOTDFooter"));
    public String YouActivatedGodFromPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YouActivatedGodFromPlayerMSG"));
    public String YouDeactivatedGodFromPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YouDeactivatedGodFromPlayerMSG"));
    public String ActivatedYourGodModeMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ActivatedYourGodModeMSG"));
    public String DeactivatedYourGodModeMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.DeactivatedYourGodModeMSG"));
    public String YouActivatedGodAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YouActivatedGodAnnotherPlayerMSG"));
    public String YouDeactivatedGodAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YouDeactivatedGodAnnotherPlayerMSG"));
    public String MOTDMaintenance = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.MOTDMaintenance"));
    public String SetWarpMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.SetWarpMSG"));
    public String WarpAlreadyExistMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.WarpAlreadyExistMSG"));
    public String WarpDoesNotExistMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.WarpDoesNotExistMSG"));
    public String QuitMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.QuitMessage"));
    public String UseCommandMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.UseCommandMSG"));
    public String OnlyRealPlayer = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.OnlyRealPlayer"));
    public String RemoveWarpMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.RemoveWarpMSG"));
    public String PlayerNotExist = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.PlayerNotExist"));
    public String TeleportToWarpMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.TeleportToWarpMSG"));
    public String TeleportToDeathMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.TeleportToDeathMSG"));
    public String DeathNotFoundMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.DeathNotFoundMSG"));
    public String PlayerClearMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.PlayerClearMSG"));
    public String AnotherPlayerClearMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.AnotherPlayerClearMSG"));
    public String EnoughLevelMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.EnoughLevelMSG"));
    public String DeactivatedFlyMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.DeactivatedFlyMSG"));
    public String ActivatedFlyMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ActivatedFlyMSG"));
    public String ActivatedFlyByPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ActivatedFlyByPlayerMSG"));
    public String DeactivatedFlyByPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.DeactivatedFlyByPlayerMSG"));
    public String ActivatedFlyFromMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ActivatedFlyFromMSG"));
    public String DeactivatedFlyFromMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.DeactivatedFlyFromMSG"));
    public String OpenEnderchestMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.OpenEnderchestMSG"));
    public String OpenYourselfEnderchestMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.OpenYourselfEnderchestMSG"));
    public String ReachMaxLevelMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ReachMaxLevelMSG"));
    public String TeleportToFarmMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.TeleportToFarmMSG"));
    public String CannotFindFarmworldMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.CannotFindFarmworldMSG"));
    public String SuccessfullyChatClearedMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.SuccessfullyChatClearedMSG"));
    public String DepositSuccessfullyMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.DepositSuccessfullyMSG"));
    public String GameModeSurvivalSelfMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeSurvivalSelfMSG"));
    public String GameModeCreativeSelfMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeCreativeSelfMSG"));
    public String GameModeAdventureSelfMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeAdventureSelfMSG"));
    public String GameModeSpectatorSelfMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeSpectatorSelfMSG"));
    public String GameModeAlreadyInSurvivalSelfMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeAlreadyInSurvivalSelfMSG"));
    public String GameModeAlreadyInCreativeSelfMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeAlreadyInCreativeSelfMSG"));
    public String GameModeAlreadyInAdventureSelfMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeAlreadyInAdventureSelfMSG"));
    public String GameModeAlreadyInSpectatorSelfMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeAlreadyInSpectatorSelfMSG"));
    public String GameModeSurvivalAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeSurvivalAnnotherPlayerMSG"));
    public String GameModeCreativeAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeCreativeAnnotherPlayerMSG"));
    public String GameModeAdventureAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeAdventureAnnotherPlayerMSG"));
    public String GameModeSpectatorAnntotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeSpectatorAnntotherPlayerMSG"));
    public String GameModeSetInSurvivalAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeSetInSurvivalAnnotherPlayerMSG"));
    public String GameModeSetInCreativeAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeSetInCreativeAnnotherPlayerMSG"));
    public String GameModeSetInAdventureAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeSetInAdventureAnnotherPlayerMSG"));
    public String GameModeSetInSpectatorAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeSetInSpectatorAnnotherPlayerMSG"));
    public String GameModeAlreadyPlayerInSurvivalMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeAlreadyPlayerInSurvivalMSG"));
    public String GameModeAlreadyPlayerInCreativeMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeAlreadyPlayerInCreativeMSG"));
    public String GameModeAlreadyPlayerInAdventureMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeAlreadyPlayerInAdventureMSG"));
    public String GameModeAlreadyPlayerInSpectatorMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GameModeAlreadyPlayerInSpectatorMSG"));

    // Title \\
    public String TitleHeader = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Title.Header"));
    public String TitleFooter = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Title.Footer"));

    // Tablist \\

    public String TabHeader = ChatColor.translateAlternateColorCodes('&', getConfig().getString("TabList.Header"));
    public String TabFooter = ChatColor.translateAlternateColorCodes('&', getConfig().getString("TabList.Footer"));

    // MySQL \\
    public String User = ChatColor.translateAlternateColorCodes('&', getConfig().getString("MySQL.Username"));
    public String Password = ChatColor.translateAlternateColorCodes('&', getConfig().getString("MySQL.Password"));
    public String Host = ChatColor.translateAlternateColorCodes('&', getConfig().getString("MySQL.Host"));
    public String Datenbank = ChatColor.translateAlternateColorCodes('&', getConfig().getString("MySQL.Datenbank"));
    public String Port = ChatColor.translateAlternateColorCodes('&', getConfig().getString("MySQL.Port"));

    // Permissions \\
    public String NoPermMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.NoPermMSG"));
    public String PermSternchen = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermSternchen"));
    public String PermSetWarp = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermSetWarp"));
    public String PermRemoveWarp = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermRemoveWarp"));
    public String PermTPWarp = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermTPWarp"));
    public String PermListWarp = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermListWarp"));
    public String PermChatClear = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermChatClear"));
    public String AllChatClearMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermAllChatClearMSG"));
    public String PermClear = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermClear"));
    public String PermEnderchest = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermEnderchest"));
    public String PermFly = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermFly"));
    public String PermAnnotherFly = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermAnnotherFly"));
    public String PermGameMode = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermGameMode"));
    public String PermGodMode = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermGodMode"));
    public String PermGodModeAnnotherPlayer = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermGodModeAnnotherPlayer"));
    public String PermGameModeAnnotherPlayer = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermGameModeAnnotherPlayer"));
}