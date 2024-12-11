package io.mysticalshadow.easyservermanager;

import io.mysticalshadow.easyservermanager.listener.PlayerManager;
import io.mysticalshadow.easyservermanager.listener.ServerManager;
import io.mysticalshadow.easyservermanager.commands.*;
import io.mysticalshadow.easyservermanager.manager.JailManager;
import io.mysticalshadow.easyservermanager.manager.MaintenanceManager;
import io.mysticalshadow.easyservermanager.manager.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
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
    private CMD_ChatCensor cmdChatCensor;
    private CMD_Test cmdTest;
    private CMD_World cmdWorld;
    private CMD_Burn cmdBurn;

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
        File file = new File(this.getDataFolder(), "censor.yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            try {
                configuration.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
        this.cmdChatCensor = new CMD_ChatCensor(this);
        this.cmdTest = new CMD_Test(this);
        this.cmdWorld = new CMD_World(this);
        this.cmdBurn = new CMD_Burn(this);

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
    public String YourHealedMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YourHealedMSG"));
    public String YourHealedFromPlayer = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YourHealedFromPlayerMSG"));
    public String YourHealedAnnotherPlayer = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YourHealedAnnotherPlayerMSG"));
    public String YouActivatedGodFromPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YouActivatedGodFromPlayerMSG"));
    public String YouDeactivatedGodFromPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YouDeactivatedGodFromPlayerMSG"));
    public String HomeSetMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.HomeSetMSG"));
    public String HomeAlreadyExistMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.HomeAlreadyExistMSG"));
    public String HomeRemovedMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.HomeRemovedMSG"));
    public String ActivatedYourGodModeMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ActivatedYourGodModeMSG"));
    public String DeactivatedYourGodModeMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.DeactivatedYourGodModeMSG"));
    public String YouActivatedGodAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YouActivatedGodAnnotherPlayerMSG"));
    public String YouDeactivatedGodAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YouDeactivatedGodAnnotherPlayerMSG"));
    public String MOTDMaintenance = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.MOTDMaintenance"));
    public String SetWarpMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.SetWarpMSG"));
    public String YourJailedHomeMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YourJailedHomeMSG"));
    public String YouTeleportToYourHomeMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YouTeleportToYourHomeMSG"));
    public String HomeNotExistMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.HomeNotExistMSG"));
    public String WarpAlreadyExistMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.WarpAlreadyExistMSG"));
    public String WarpDoesNotExistMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.WarpDoesNotExistMSG"));
    public String QuitMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.QuitMessage"));
    public String UseCommandMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.UseCommandMSG"));
    public String OnlyRealPlayer = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.OnlyRealPlayer"));
    public String WorldCreatedMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.WorldCreatedMSG"));
    public String WorldAlreadyExistMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.WorldAlreadyExistMSG"));
    public String WorldDoesNotExistMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.WorldDoesNotExistMSG"));
    public String YourRemovedTheWorldMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YourRemovedTheWorldMSG"));
    public String YourTeleportToTheWorldMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YourTeleportToTheWorldMSG"));
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
    public String BurnPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.BurnPlayerMSG"));
    public String ActivatedFlyByPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ActivatedFlyByPlayerMSG"));
    public String DeactivatedFlyByPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.DeactivatedFlyByPlayerMSG"));
    public String ActivatedFlyFromMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ActivatedFlyFromMSG"));
    public String DeactivatedFlyFromMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.DeactivatedFlyFromMSG"));
    public String OpenEnderchestMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.OpenEnderchestMSG"));
    public String InvseeMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.InvseeMSG"));
    public String YouJailedAPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YouJailedAPlayerMSG"));
    public String YouJailedFromPlayer = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YouJailedFromPlayer"));
    public String InvseeYourselfMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.InvseeYourselfMSG"));
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
    public String PlayerAlreadyJailedMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.PlayerAlreadyJailedMSG"));
    public String JailNotExistMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.JailNotExistMSG"));
    public String PingInMSMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.PingInMSMSG"));
    public String PingYourselfMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.PingYourselfMSG"));
    public String PlayerIsNotJailedMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.PlayerIsNotJailedMSG"));
    public String WarpSpawnNotFoundMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.WarpSpawnNotFoundMSG"));
    public String PlayerUnjailedMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.PlayerUnjailedMSG"));
    public String PlayerUnjailedFromMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.PlayerUnjailedFromMSG"));
    public String ThePathNotFoundMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ThePathNotFoundMSG"));
    public String UnjailedYourselfMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.UnjailedYourselfMSG"));
    public String KickMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.KickMSG"));
    public String AlreadyDeactivatedYourselfPvPMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.AlreadyDeactivatedYourselfPvPMSG"));
    public String AlreadyActivatedYourselfPvPMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.AlreadyActivatedYourselfPvPMSG"));
    public String DeactivatedYourselfPvPMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.DeactivatedYourselfPvPMSG"));
    public String ActivatedYourselfPvPMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ActivatedYourselfPvPMSG"));
    public String AddedLevelMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.AddedPlayerLevelMSG"));
    public String AddedLevelFromPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.AddedLevelFromPlayerMSG"));
    public String RemovedPlayerLevelMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.RemovedPlayerLevelMSG"));
    public String RemovedLevelFromPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.RemovedLevelFromPlayerMSG"));
    public String KickPlayer = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.KickPlayerMSG"));
    public String AnnotherPlayerPingMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.AnnotherPlayerPingMSG"));
    public String EnoughPlayerLevel = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.EnoughPlayerLevel"));
    public String KickYourself = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.KickYourselfMSG"));
    public String YouGotRewardMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YouGotRewardMSG"));
    public String PlayerExistNotAFileMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.AnnotherPlayerExistNotAFileMSG"));
    public String SuccessfullyKickPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.SuccessfullyKickPlayerMSG"));
    public String AnnotherPlayerHaveNoHomesMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.AnnotherPlayerHaveNoHomesMSG"));
    public String AnnotherPlayerHomes = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.AnnotherPlayerHomes"));
    public String YourHomesMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YourHomesMSG"));
    public String SetJailMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.SetJailMSG"));
    public String TeleportYourselfMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.TeleportYourselfMSG"));
    public String TeleportToThePlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.TeleportToThePlayerMSG"));
    public String TeleportToSpawnMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.TeleportToSpawnMSG"));
    public String GotPlayerRewardNotify = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.GotPlayerRewardNotify"));
    public String PayedLevelMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.PayedLevelMSG"));
    public String AlreadyPickupRewardMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.AlreadyPickupRewardMSG"));
    public String PayoutSuccessfullyMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.PayoutSuccessfullyMSG"));
    public String PayedLevelAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.PayedLevelAnnotherPlayerMSG"));
    public String YourHaveNoHomesMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YourHaveNoHomesMSG"));
    public String ActivateMaintenanceMode = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ActivateMaintenanceModeMSG"));
    public String KickPlayerWhenActivateMaintenance = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.KickPlayerWhenActivateMaintenanceMSG"));
    public String DeactivateMaintenanceModeMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.DeactivateMaintenanceModeMSG"));
    public String SuccessSetDayMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.SuccessSetDayMSG"));
    public String YouDontTPARequestMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.YouDontTPARequestMSG"));
    public String SuccessSetNightMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.SuccessSetNightMSG"));
    public String DenyTPAMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.DenyTPAMSG"));
    public String AcceptTPAMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.AcceptTPAMSG"));
    public String PlayerAcceptTPAMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.PlayerAcceptTPAMSG"));
    public String PlayerDeniedTPAMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.PlayerDeniedTPAMSG"));
    public String SendPlayerTPAMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.SendPlayerTPAMSG"));
    public String SuccessSetMidnightMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.SuccessSetMidnightMSG"));
    public String AcceptRequestMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.AcceptRequestMSG"));
    public String DeniedRequestMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.DeniedRequestMSG"));
    public String PlayerDeathMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.PlayerDeathMSG"));
    public String PlayerKilledByPlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.PlayerKilledByPlayerMSG"));
    public String ReceivedRequestMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ReceivedRequestMSG"));
    public String TpherePlayerMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.TpherePlayerMSG"));
    public String CurrentlyJailed = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.CurrentlyJailed"));
    public String AllChatClearMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.AllChatClearMSG"));
    public String WeatherChangedSunMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.WeatherChangedSunMSG"));
    public String WeatherChangedRainMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.WeatherChangedRainMSG"));
    public String NoWordsBlockedMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.NoWordsBlockedMSG"));
    public String ThisWordIsNotBlocked = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ThisWordIsNotBlocked"));
    public String ThisWordIsBlocked = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ThisWordIsBlocked"));
    public String ThisWordIsAlreadyBlocked = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ThisWordIsAlreadyBlocked"));
    public String AddedWordsBlockedMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.AddedWordsBlockedMSG"));
    public String RemovedWordsBlockedMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.RemovedWordsBlockedMSG"));
    public String WeatherChangedThunderMSG = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.WeatherChangedThunderMSG"));

    // Title \\
    public String TitleHeader = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Title.Header"));
    public String TitleFooter = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Title.Footer"));

    // Ranks \\
    public String PrefixOwner = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.Owner"));
    public String PrefixCoOwner = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.CoOwner"));
    public String PrefixSrAdmin = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.SrAdmin"));
    public String PrefixAdmin = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.Admin"));
    public String PrefixSrDeveloper = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.SrDeveloper"));
    public String PrefixDeveloper = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.Developer"));
    public String PrefixTestDeveloper = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.TestDeveloper"));
    public String PrefixSrModerator = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.SrModerator"));
    public String PrefixModerator = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.Moderator"));
    public String PrefixTestModerator = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.TestModerator"));
    public String PrefixSrSupporter = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.SrSupporter"));
    public String PrefixSupporter = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.Supporter"));
    public String PrefixTestSupporter = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.TestSupporter"));
    public String PrefixSrBuilder = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.SrBuilder"));
    public String PrefixBuilder = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.Builder"));
    public String PrefixTestBuilder = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.TestBuilder"));
    public String PrefixFriend = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.Friend"));
    public String PrefixYouTuber = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.YouTuber"));
    public String PrefixStreamer = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.Streamer"));
    public String PrefixPremiumThree = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.PremiumThree"));
    public String PrefixPremiumTwo = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.PremiumTwo"));
    public String PrefixPremiumOne = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.PremiumOne"));
    public String PrefixPlayer = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Rank.Player"));

    // RewardSettings \\
    public String OneItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.One.Item"));
    public String OneName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.One.Name"));
    public int OneAmount = getConfig().getInt("Reward.One.Amount");
    public String TwoItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Two.Item"));
    public String TwoName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Two.Name"));
    public int TwoAmount = getConfig().getInt("Reward.Two.Amount");
    public String ThreeItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Three.Item"));
    public String ThreeName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Three.Name"));
    public int ThreeAmount = getConfig().getInt("Reward.Three.Amount");
    public String FourItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Four.Item"));
    public String FourName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Four.Name"));
    public int FourAmount = getConfig().getInt("Reward.Four.Amount");
    public String FiveItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Five.Item"));
    public String FiveName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Five.Name"));
    public int FiveAmount = getConfig().getInt("Reward.Five.Amount");
    public String SixItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Six.Item"));
    public String SixName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Six.Name"));
    public int SixAmount = getConfig().getInt("Reward.Six.Amount");
    public String SevenItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Seven.Item"));
    public String SevenName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Seven.Name"));
    public int SevenAmount = getConfig().getInt("Reward.Seven.Amount");
    public String EightItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Eight.Item"));
    public String EightName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Eight.Name"));
    public int EightAmount = getConfig().getInt("Reward.Eight.Amount");
    public String NineItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Nine.Item"));
    public String NineName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Nine.Name"));
    public int NineAmount = getConfig().getInt("Reward.Nine.Amount");
    public String TenItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Ten.Item"));
    public String TenName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Ten.Name"));
    public int TenAmount = getConfig().getInt("Reward.Ten.Amount");
    public String ElevenItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Eleven.Item"));
    public String ElevenName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Eleven.Name"));
    public int ElevenAmount = getConfig().getInt("Reward.Eleven.Amount");
    public String TwelveItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Twelve.Item"));
    public String TwelveName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Twelve.Name"));
    public int TwelveAmount = getConfig().getInt("Reward.Twelve.Amount");
    public String ThirteenItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Thirteen.Item"));
    public String ThirteenName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Thirteen.Name"));
    public int ThirteenAmount = getConfig().getInt("Reward.Thirteen.Amount");
    public String FourteenItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Fourteen.Item"));
    public String FourteenName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Fourteen.Name"));
    public int FourteenAmount = getConfig().getInt("Reward.Fourteen.Amount");
    public String FifteenItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Fifteen.Item"));
    public String FifteenName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Fifteen.Name"));
    public int FifteenAmount = getConfig().getInt("Reward.Fifteen.Amount");
    public String SixteenItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Sixteen.Item"));
    public String SixteenName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Sixteen.Name"));
    public int SixteenAmount = getConfig().getInt("Reward.Sixteen.Amount");
    public String SeventeenItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Seventeen.Item"));
    public String SeventeenName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Seventeen.Name"));
    public int SeventeenAmount = getConfig().getInt("Reward.Seventeen.Amount");
    public String EighteenItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Eighteen.Item"));
    public String EighteenName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Eighteen.Name"));
    public int EighteenAmount = getConfig().getInt("Reward.Eighteen.Amount");
    public String NineteenItem = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Nineteen.Item"));
    public String NineteenName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reward.Nineteen.Name"));
    public int NineteenAmount = getConfig().getInt("Reward.Nineteen.Amount");

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
    public String PermClear = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermClear"));
    public String PermEnderchest = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermEnderchest"));
    public String PermFly = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermFly"));
    public String PermAnnotherFly = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermAnnotherFly"));
    public String PermRewardNotify = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermRewardNotify"));
    public String PermGameMode = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermGameMode"));
    public String PermGodMode = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermGodMode"));
    public String PermGodModeAnnotherPlayer = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermGodModeAnnotherPlayer"));
    public String PermGameModeAnnotherPlayer = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermGameModeAnnotherPlayer"));
    public String PermHeal = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermHeal"));
    public String PermAnnotherPing = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermAnnotherPing"));
    public String PermInvsee = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermInvsee"));
    public String PermTPHERE = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermTPHERE"));
    public String PermJail = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermJail"));
    public String PermHealAnnotherPlayer = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermHealAnnotherPlayer"));
    public String PermKick = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermKick"));
    public String PermLevel = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermLevel"));
    public String PermTeleport = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermTeleport"));
    public String PermTime = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermTime"));
    public String PermGetOtherHomes = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermGetOtherHomes"));
    public String PermMaintenanceJoin = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermMaintenanceJoin"));
    public String PermMaintenanceActivate = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermMaintenanceActivate"));
    public String PermSetJail = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermSetJail"));
    public String PermWeather = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermWeather"));
    public String PermCensor = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermCensor"));
    public String PermPrefixOwner = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermOwnerPrefix"));
    public String PermPrefixCoOwner = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermCoOwnerPrefix"));
    public String PermPrefixSrAdmin = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermSrAdminPrefix"));
    public String PermPrefixAdmin = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermAdminPrefix"));
    public String PermPrefixSrDeveloper = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermSrDeveloperPrefix"));
    public String PermPrefixDeveloper = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermDeveloperPrefix"));
    public String PermPrefixTestDeveloper = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermTestDeveloperPrefix"));
    public String PermPrefixSrModerator = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermSrModeratorPrefix"));
    public String PermPrefixModerator = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermModeratorPrefix"));
    public String PermPrefixTestModerator = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermTestModeratorPrefix"));
    public String PermPrefixSrSupporter = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermSrSupporterPrefix"));
    public String PermPrefixSupporter = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermSupporterPrefix"));
    public String PermPrefixTestSupporter = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermTestSupporterPrefix"));
    public String PermPrefixSrBuilder = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermSrBuilderPrefix"));
    public String PermPrefixBuilder = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermBuilderPrefix"));
    public String PermPrefixTestBuilder = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermTestBuilderPrefix"));
    public String PermPrefixFriend = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermFriendPrefix"));
    public String PermPrefixYouTuber = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermYouTuberPrefix"));
    public String PermPrefixStreamer = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermStreamerPrefix"));
    public String PermPrefixPremiumThree = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermPremiumThreePrefix"));
    public String PermPrefixPremiumTwo = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermPremiumTwoPrefix"));
    public String PermPrefixPremiumOne = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermPremiumOnePrefix"));
    public String PermSeePlugins = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermSeePlugins"));
    public String PermsWorld = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermsWorld"));
    public String PermsBurn = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Perms.PermsBurn"));
}