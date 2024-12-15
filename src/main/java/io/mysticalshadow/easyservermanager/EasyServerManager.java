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
import java.util.Objects;

public class EasyServerManager extends JavaPlugin {

    public static EasyServerManager instance;
    public static List<Player> vanish = new ArrayList<>();
    public static List<Player> fly = new ArrayList<>();
    public static List<Player> seeCommands = new ArrayList<>();
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
    private CMD_Weather cmdWetter;
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
    private CMD_CommandSee cmdCommandSee;
    private CMD_ListWarps cmdListWarps;
    private CMD_Warn cmdWarn;
    private CMD_Report cmdReport;
    private CMD_EasyServerManager cmdEasyServerManager;
    private CMD_TPHereAll cmdTpHereAll;

    @Override
    public void onEnable() {

        // Utils \\
        instance = this;
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveResource("config.yml", true);
        }
        File fileReports = new File(getDataFolder(), "reports.yml");
        YamlConfiguration configReports = YamlConfiguration.loadConfiguration(fileReports);
        if (!fileReports.exists()) {
            try {
                configReports.save(fileReports);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
        this.cmdWetter = new CMD_Weather(this);
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
        this.cmdCommandSee = new CMD_CommandSee(this);
        this.cmdListWarps = new CMD_ListWarps(this);
        this.cmdWarn = new CMD_Warn(this);
        this.cmdReport = new CMD_Report(this);
        this.cmdEasyServerManager = new CMD_EasyServerManager(this);
        this.cmdTpHereAll = new CMD_TPHereAll(this);

        // Enable Message \\

        Bukkit.getConsoleSender().sendMessage("§l");
        Bukkit.getConsoleSender().sendMessage("§9---------- §3§lEasy§b§lServer§3§lManager §9----------");
        Bukkit.getConsoleSender().sendMessage("§l ");
        Bukkit.getConsoleSender().sendMessage("§7Version: §e" + instance.getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("§7Authors: §e" + instance.getDescription().getAuthors());
        Bukkit.getConsoleSender().sendMessage("§7Status: §eAktiviert");
        Bukkit.getConsoleSender().sendMessage("§l  ");
        Bukkit.getConsoleSender().sendMessage("§9----------------------------------------");
    }

    @Override
    public void onDisable() {

        Bukkit.getConsoleSender().sendMessage("§l");
        Bukkit.getConsoleSender().sendMessage("§9---------- §3§lEasy§b§lServer§3§lManager §9----------");
        Bukkit.getConsoleSender().sendMessage("§l ");
        Bukkit.getConsoleSender().sendMessage("§7Version: §e" + instance.getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("§7Authors: §e" + instance.getDescription().getAuthors());
        Bukkit.getConsoleSender().sendMessage("§7Discord: §4morphinedev");
        Bukkit.getConsoleSender().sendMessage("§7Status: §4Deaktiviert");
        Bukkit.getConsoleSender().sendMessage("§l  ");
        Bukkit.getConsoleSender().sendMessage("§9----------------------------------------");
    }
    public static EasyServerManager getInstance() {
        return instance;

    }
    // Plugin Settings \\
    public String Prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("EasyServerManager.Prefix")));
    public boolean AllowJoinMessage = getConfig().getBoolean("EasyServerManager.AllowJoinMessage");
    public boolean AllowQuitMessage = getConfig().getBoolean("EasyServerManager.AllowQuitMessage");
    public boolean AllowPlayTeleportSound = getConfig().getBoolean("EasyServerManager.AllowPlayTeleportSound");
    public boolean AllowPlayRestartSound = getConfig().getBoolean("EasyServerManager.AllowPlayRestartSound");
    public boolean AllowJoinTitle = getConfig().getBoolean("EasyServerManager.AllowJoinTitle");
    public int MaxPlayer = getConfig().getInt("EasyServerManager.MaxPlayers");
    public int MaxWarnsBeforeKick = getConfig().getInt("EasyServerManager.MaxWarnsBeforeKick");
    public int MaxSaveLevel = getConfig().getInt("EasyServerManager.MaxSaveLevel");
    public String SaveType = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("EasyServerManager.SaveType")));
    public String ServerName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("EasyServerManager.ServerName")));

    // Messages \\
    public String JoinMessage = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.JoinMessage")));
    public String MOTDHeader = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.MOTDHeader")));
    public String MOTDFooter = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.MOTDFooter")));
    public String YourHealedMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.YourHealedMSG")));
    public String YourHealedFromPlayer = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.YourHealedFromPlayerMSG")));
    public String YourHealedAnnotherPlayer = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.YourHealedAnnotherPlayerMSG")));
    public String YouActivatedGodFromPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.YouActivatedGodFromPlayerMSG")));
    public String YouDeactivatedGodFromPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.YouDeactivatedGodFromPlayerMSG")));
    public String HomeSetMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.HomeSetMSG")));
    public String HomeAlreadyExistMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.HomeAlreadyExistMSG")));
    public String HomeRemovedMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.HomeRemovedMSG")));
    public String ActivatedYourGodModeMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.ActivatedYourGodModeMSG")));
    public String DeactivatedYourGodModeMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.DeactivatedYourGodModeMSG")));
    public String YouActivatedGodAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.YouActivatedGodAnnotherPlayerMSG")));
    public String YouDeactivatedGodAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.YouDeactivatedGodAnnotherPlayerMSG")));
    public String MOTDMaintenance = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.MOTDMaintenance")));
    public String SetWarpMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.SetWarpMSG")));
    public String WorldNotExistMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.WorldNotExistMSG")));
    public String FinishReportMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.FinishReportMSG")));
    public String ReportsFromThePlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.ReportsFromThePlayerMSG")));
    public String ThePlayerClosedReportMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.ThePlayerClosedReportMSG")));
    public String YouCloseReportMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.YouCloseReportMSG")));
    public String ReportEditorFromThisReportMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.ReportEditorFromThisReportMSG")));
    public String YouNotEditorFromThisReportMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.YouNotEditorFromThisReportMSG")));
    public String YouAcceptReportMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.YouAcceptReportMSG")));
    public String ReportAlreadyProgressingByMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.ReportAlreadyProgressingByMSG")));
    public String ReportAlreadyProgressingMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.ReportAlreadyProgressingMSG")));
    public String PlayerHaveNoReportsWithThisReasonMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.PlayerHaveNoReportsWithThisReasonMSG")));
    public String PlayerHaveNoReportsMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.PlayerHaveNoReportsMSG")));
    public String ReportReasonIsAlreadyExistMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.ReportReasonIsAlreadyExistMSG")));
    public String YourJailedHomeMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.YourJailedHomeMSG")));
    public String YouTeleportToYourHomeMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.YouTeleportToYourHomeMSG")));
    public String HomeNotExistMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.HomeNotExistMSG")));
    public String WarpAlreadyExistMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.WarpAlreadyExistMSG")));
    public String WarpDoesNotExistMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.WarpDoesNotExistMSG")));
    public String QuitMessage = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.QuitMessage")));
    public String UseCommandMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.UseCommandMSG")));
    public String SuccessReportPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.SuccessReportPlayerMSG")));
    public String OnlyRealPlayer = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.OnlyRealPlayer")));
    public String WorldCreatedMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.WorldCreatedMSG")));
    public String WorldAlreadyExistMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.WorldAlreadyExistMSG")));
    public String WorldDoesNotExistMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.WorldDoesNotExistMSG")));
    public String YourRemovedTheWorldMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.YourRemovedTheWorldMSG")));
    public String YourTeleportToTheWorldMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.YourTeleportToTheWorldMSG")));
    public String RemoveWarpMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.RemoveWarpMSG")));
    public String PlayerNotExist = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.PlayerNotExist")));
    public String TeleportToWarpMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.TeleportToWarpMSG")));
    public String TeleportToDeathMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.TeleportToDeathMSG")));
    public String DeathNotFoundMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.DeathNotFoundMSG")));
    public String PlayerClearMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.PlayerClearMSG")));
    public String AnotherPlayerClearMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.AnotherPlayerClearMSG")));
    public String EnoughLevelMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.EnoughLevelMSG")));
    public String DeactivatedFlyMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.DeactivatedFlyMSG")));
    public String ActivatedFlyMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.ActivatedFlyMSG")));
    public String BurnPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.BurnPlayerMSG")));
    public String ActivatedFlyByPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Messages.ActivatedFlyByPlayerMSG")));
    public String DeactivatedFlyByPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.DeactivatedFlyByPlayerMSG"))));
    public String ActivatedFlyFromMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.ActivatedFlyFromMSG"))));
    public String DeactivatedFlyFromMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.DeactivatedFlyFromMSG"))));
    public String OpenEnderchestMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.OpenEnderchestMSG"))));
    public String InvseeMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.InvseeMSG"))));
    public String YouJailedAPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.YouJailedAPlayerMSG"))));
    public String YouJailedFromPlayer = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.YouJailedFromPlayer"))));
    public String InvseeYourselfMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.InvseeYourselfMSG"))));
    public String OpenYourselfEnderchestMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.OpenYourselfEnderchestMSG"))));
    public String ReachMaxLevelMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.ReachMaxLevelMSG"))));
    public String TeleportToFarmMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.TeleportToFarmMSG"))));
    public String CannotFindFarmworldMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.CannotFindFarmworldMSG"))));
    public String SuccessfullyChatClearedMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.SuccessfullyChatClearedMSG"))));
    public String DepositSuccessfullyMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.DepositSuccessfullyMSG"))));
    public String GameModeSurvivalSelfMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeSurvivalSelfMSG"))));
    public String GameModeCreativeSelfMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeCreativeSelfMSG"))));
    public String GameModeAdventureSelfMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeAdventureSelfMSG"))));
    public String GameModeSpectatorSelfMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeSpectatorSelfMSG"))));
    public String GameModeAlreadyInSurvivalSelfMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeAlreadyInSurvivalSelfMSG"))));
    public String GameModeAlreadyInCreativeSelfMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeAlreadyInCreativeSelfMSG"))));
    public String GameModeAlreadyInAdventureSelfMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeAlreadyInAdventureSelfMSG"))));
    public String GameModeAlreadyInSpectatorSelfMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeAlreadyInSpectatorSelfMSG"))));
    public String GameModeSurvivalAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeSurvivalAnnotherPlayerMSG"))));
    public String GameModeCreativeAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeCreativeAnnotherPlayerMSG"))));
    public String GameModeAdventureAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeAdventureAnnotherPlayerMSG"))));
    public String GameModeSpectatorAnntotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeSpectatorAnntotherPlayerMSG"))));
    public String GameModeSetInSurvivalAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeSetInSurvivalAnnotherPlayerMSG"))));
    public String GameModeSetInCreativeAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeSetInCreativeAnnotherPlayerMSG"))));
    public String GameModeSetInAdventureAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeSetInAdventureAnnotherPlayerMSG"))));
    public String GameModeSetInSpectatorAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeSetInSpectatorAnnotherPlayerMSG"))));
    public String GameModeAlreadyPlayerInSurvivalMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeAlreadyPlayerInSurvivalMSG"))));
    public String GameModeAlreadyPlayerInCreativeMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeAlreadyPlayerInCreativeMSG"))));
    public String GameModeAlreadyPlayerInAdventureMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeAlreadyPlayerInAdventureMSG"))));
    public String GameModeAlreadyPlayerInSpectatorMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GameModeAlreadyPlayerInSpectatorMSG"))));
    public String PlayerAlreadyJailedMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PlayerAlreadyJailedMSG"))));
    public String JailNotExistMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.JailNotExistMSG"))));
    public String PingInMSMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PingInMSMSG"))));
    public String PingYourselfMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PingYourselfMSG"))));
    public String PlayerIsNotJailedMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PlayerIsNotJailedMSG"))));
    public String WarpSpawnNotFoundMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.WarpSpawnNotFoundMSG"))));
    public String PlayerUnjailedMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PlayerUnjailedMSG"))));
    public String PlayerUnjailedFromMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PlayerUnjailedFromMSG"))));
    public String ThePathNotFoundMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.ThePathNotFoundMSG"))));
    public String UnjailedYourselfMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.UnjailedYourselfMSG"))));
    public String KickMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.KickMSG"))));
    public String AlreadyDeactivatedYourselfPvPMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.AlreadyDeactivatedYourselfPvPMSG"))));
    public String AlreadyActivatedYourselfPvPMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.AlreadyActivatedYourselfPvPMSG"))));
    public String DeactivatedYourselfPvPMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.DeactivatedYourselfPvPMSG"))));
    public String ActivatedYourselfPvPMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.ActivatedYourselfPvPMSG"))));
    public String AddedLevelMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.AddedPlayerLevelMSG"))));
    public String AddedLevelFromPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.AddedLevelFromPlayerMSG"))));
    public String RemovedPlayerLevelMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.RemovedPlayerLevelMSG"))));
    public String RemovedLevelFromPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.RemovedLevelFromPlayerMSG"))));
    public String KickPlayer = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.KickPlayerMSG"))));
    public String AnnotherPlayerPingMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.AnnotherPlayerPingMSG"))));
    public String EnoughPlayerLevel = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.EnoughPlayerLevel"))));
    public String KickYourself = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.KickYourselfMSG"))));
    public String YouGotRewardMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.YouGotRewardMSG"))));
    public String PlayerExistNotAFileMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.AnnotherPlayerExistNotAFileMSG"))));
    public String SuccessfullyKickPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.SuccessfullyKickPlayerMSG"))));
    public String AnnotherPlayerHaveNoHomesMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.AnnotherPlayerHaveNoHomesMSG"))));
    public String AnnotherPlayerHomes = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.AnnotherPlayerHomes"))));
    public String YourHomesMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.YourHomesMSG"))));
    public String SetJailMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.SetJailMSG"))));
    public String TeleportYourselfMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.TeleportYourselfMSG"))));
    public String TeleportToThePlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.TeleportToThePlayerMSG"))));
    public String TeleportToSpawnMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.TeleportToSpawnMSG"))));
    public String GotPlayerRewardNotify = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.GotPlayerRewardNotify"))));
    public String PayedLevelMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PayedLevelMSG"))));
    public String AlreadyPickupRewardMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.AlreadyPickupRewardMSG"))));
    public String PayoutSuccessfullyMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PayoutSuccessfullyMSG"))));
    public String PayedLevelAnnotherPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PayedLevelAnnotherPlayerMSG"))));
    public String YourHaveNoHomesMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.YourHaveNoHomesMSG"))));
    public String ActivateMaintenanceMode = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.ActivateMaintenanceModeMSG"))));
    public String KickPlayerWhenActivateMaintenance = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.KickPlayerWhenActivateMaintenanceMSG"))));
    public String DeactivateMaintenanceModeMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.DeactivateMaintenanceModeMSG"))));
    public String SuccessSetDayMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.SuccessSetDayMSG"))));
    public String YouDontTPARequestMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.YouDontTPARequestMSG"))));
    public String SuccessSetNightMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.SuccessSetNightMSG"))));
    public String DenyTPAMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.DenyTPAMSG"))));
    public String AcceptTPAMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.AcceptTPAMSG"))));
    public String PlayerAcceptTPAMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PlayerAcceptTPAMSG"))));
    public String PlayerDeniedTPAMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PlayerDeniedTPAMSG"))));
    public String SendPlayerTPAMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.SendPlayerTPAMSG"))));
    public String SuccessSetMidnightMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.SuccessSetMidnightMSG"))));
    public String AcceptRequestMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.AcceptRequestMSG"))));
    public String DeniedRequestMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.DeniedRequestMSG"))));
    public String PlayerDeathMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PlayerDeathMSG"))));
    public String PlayerKilledByPlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PlayerKilledByPlayerMSG"))));
    public String ReceivedRequestMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.ReceivedRequestMSG"))));
    public String TpherePlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.TpherePlayerMSG"))));
    public String CurrentlyJailed = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.CurrentlyJailed"))));
    public String AllChatClearMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.AllChatClearMSG"))));
    public String WeatherChangedSunMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.WeatherChangedSunMSG"))));
    public String WeatherChangedRainMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.WeatherChangedRainMSG"))));
    public String NoWordsBlockedMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.NoWordsBlockedMSG"))));
    public String ThisWordIsNotBlocked = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.ThisWordIsNotBlocked"))));
    public String ThisWordIsBlocked = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.ThisWordIsBlocked"))));
    public String ThisWordIsAlreadyBlocked = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.ThisWordIsAlreadyBlocked"))));
    public String AddedWordsBlockedMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.AddedWordsBlockedMSG"))));
    public String RemovedWordsBlockedMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.RemovedWordsBlockedMSG"))));
    public String AnnotherPlayerWarnsMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.AnnotherPlayerWarnsMSG"))));
    public String YouCanNowSeeCommandsMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.YouCanNowSeeCommandsMSG"))));
    public String YourWarnedThePlayerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.YourWarnedThePlayerMSG"))));
    public String YouCannotSeeCommandsMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.YouCannotSeeCommandsMSG"))));
    public String WarnReasonAlreadyListedMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.WarnReasonAlreadyListedMSG"))));
    public String SuccessfullyRemoveWarnMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.SuccessfullyRemoveWarnMSG"))));
    public String WarpListMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.WarpListMSG"))));
    public String PlayerHasNoWarnWithThisReasonMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PlayerHasNoWarnWithThisReasonMSG"))));
    public String PlayerKickedWarnMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PlayerKickedWarnMSG"))));
    public String PlayerWarnKickMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PlayerWarnKickMSG"))));
    public String PlayerHaveNoWarnsMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PlayerHaveNoWarnsMSG"))));
    public String WeatherChangedThunderMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.WeatherChangedThunderMSG"))));
    public String WarpsNotExistOnServerMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.WarpsNotExistOnServerMSG"))));
    public String PlayerKickWhenServerRestartMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.PlayerKickWhenServerRestartMSG"))));
    public String StartRestartCountdownMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.StartRestartCountdownMSG"))));
    public String RestartInTenSecondsMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.RestartInTenSecondsMSG"))));
    public String RestartInFiveSecondsMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.RestartInFiveSecondsMSG"))));
    public String RestartInThreeSecondsMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.RestartInThreeSecondsMSG"))));
    public String RestartInTwoSecondsMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.RestartInTwoSecondsMSG"))));
    public String RestartInOneSecondsMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.RestartInOneSecondsMSG"))));
    public String RemoveWarnYourselfMSG = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Messages.RemoveWarnYourselfMSG"))));

    // Title \\
    public String TitleHeader = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Title.Header"))));
    public String TitleFooter = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Title.Footer"))));

    // Ranks \\
    public String PrefixOwner = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.Owner"))));
    public String PrefixCoOwner = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.CoOwner"))));
    public String PrefixSrAdmin = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.SrAdmin"))));
    public String PrefixAdmin = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.Admin"))));
    public String PrefixSrDeveloper = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.SrDeveloper"))));
    public String PrefixDeveloper = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.Developer"))));
    public String PrefixTestDeveloper = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.TestDeveloper"))));
    public String PrefixSrModerator = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.SrModerator"))));
    public String PrefixModerator = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.Moderator"))));
    public String PrefixTestModerator = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.TestModerator"))));
    public String PrefixSrSupporter = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.SrSupporter"))));
    public String PrefixSupporter = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.Supporter"))));
    public String PrefixTestSupporter = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.TestSupporter"))));
    public String PrefixSrBuilder = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.SrBuilder"))));
    public String PrefixBuilder = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.Builder"))));
    public String PrefixTestBuilder = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.TestBuilder"))));
    public String PrefixFriend = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.Friend"))));
    public String PrefixYouTuber = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.YouTuber"))));
    public String PrefixStreamer = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.Streamer"))));
    public String PrefixPremiumThree = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.PremiumThree"))));
    public String PrefixPremiumTwo = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.PremiumTwo"))));
    public String PrefixPremiumOne = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.PremiumOne"))));
    public String PrefixPlayer = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Rank.Player"))));

    // RewardSettings \\
    public boolean ActivatedRewards = getConfig().getBoolean("Reward.Activated");
    public String OneItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.One.Item"))));
    public String OneName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.One.Name"))));
    public int OneAmount = getConfig().getInt("Reward.One.Amount");
    public String TwoItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Two.Item"))));
    public String TwoName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Two.Name"))));
    public int TwoAmount = getConfig().getInt("Reward.Two.Amount");
    public String ThreeItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Three.Item"))));
    public String ThreeName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Three.Name"))));
    public int ThreeAmount = getConfig().getInt("Reward.Three.Amount");
    public String FourItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Four.Item"))));
    public String FourName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Four.Name"))));
    public int FourAmount = getConfig().getInt("Reward.Four.Amount");
    public String FiveItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Five.Item"))));
    public String FiveName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Five.Name"))));
    public int FiveAmount = getConfig().getInt("Reward.Five.Amount");
    public String SixItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Six.Item"))));
    public String SixName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Six.Name"))));
    public int SixAmount = getConfig().getInt("Reward.Six.Amount");
    public String SevenItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Seven.Item"))));
    public String SevenName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Seven.Name"))));
    public int SevenAmount = getConfig().getInt("Reward.Seven.Amount");
    public String EightItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Eight.Item"))));
    public String EightName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Eight.Name"))));
    public int EightAmount = getConfig().getInt("Reward.Eight.Amount");
    public String NineItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Nine.Item"))));
    public String NineName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Nine.Name"))));
    public int NineAmount = getConfig().getInt("Reward.Nine.Amount");
    public String TenItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Ten.Item"))));
    public String TenName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Ten.Name"))));
    public int TenAmount = getConfig().getInt("Reward.Ten.Amount");
    public String ElevenItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Eleven.Item"))));
    public String ElevenName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Eleven.Name"))));
    public int ElevenAmount = getConfig().getInt("Reward.Eleven.Amount");
    public String TwelveItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Twelve.Item"))));
    public String TwelveName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Twelve.Name"))));
    public int TwelveAmount = getConfig().getInt("Reward.Twelve.Amount");
    public String ThirteenItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Thirteen.Item"))));
    public String ThirteenName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Thirteen.Name"))));
    public int ThirteenAmount = getConfig().getInt("Reward.Thirteen.Amount");
    public String FourteenItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Fourteen.Item"))));
    public String FourteenName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Fourteen.Name"))));
    public int FourteenAmount = getConfig().getInt("Reward.Fourteen.Amount");
    public String FifteenItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Fifteen.Item"))));
    public String FifteenName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Fifteen.Name"))));
    public int FifteenAmount = getConfig().getInt("Reward.Fifteen.Amount");
    public String SixteenItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Sixteen.Item"))));
    public String SixteenName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Sixteen.Name"))));
    public int SixteenAmount = getConfig().getInt("Reward.Sixteen.Amount");
    public String SeventeenItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Seventeen.Item"))));
    public String SeventeenName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Seventeen.Name"))));
    public int SeventeenAmount = getConfig().getInt("Reward.Seventeen.Amount");
    public String EighteenItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Eighteen.Item"))));
    public String EighteenName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Eighteen.Name"))));
    public int EighteenAmount = getConfig().getInt("Reward.Eighteen.Amount");
    public String NineteenItem = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Nineteen.Item"))));
    public String NineteenName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Reward.Nineteen.Name"))));
    public int NineteenAmount = getConfig().getInt("Reward.Nineteen.Amount");

    // Tablist \\
    public String TabHeader = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("TabList.Header"))));
    public String TabFooter = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("TabList.Footer"))));

    // MySQL \\
    public String User = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("MySQL.Username"))));
    public String Password = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("MySQL.Password"))));
    public String Host = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("MySQL.Host"))));
    public String Datenbank = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("MySQL.Datenbank"))));
    public String Port = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("MySQL.Port"))));

    // Permissions \\
    public String NoPermMessage = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.NoPermMSG"))));
    public String PermSternchen = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermSternchen"))));
    public String PermSetWarp = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermSetWarp"))));
    public String PermRemoveWarp = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermRemoveWarp"))));
    public String PermTPWarp = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermTPWarp"))));
    public String PermListWarp = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermListWarp"))));
    public String PermChatClear = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermChatClear"))));
    public String PermClear = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermClear"))));
    public String PermEnderchest = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermEnderchest"))));
    public String PermFly = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermFly"))));
    public String PermAnnotherFly = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermAnnotherFly"))));
    public String PermRewardNotify = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermRewardNotify"))));
    public String PermGameMode = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermGameMode"))));
    public String PermGodMode = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermGodMode"))));
    public String PermGodModeAnnotherPlayer = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermGodModeAnnotherPlayer"))));
    public String PermGameModeAnnotherPlayer = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermGameModeAnnotherPlayer"))));
    public String PermHeal = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermHeal"))));
    public String PermAnnotherPing = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermAnnotherPing"))));
    public String PermInvsee = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermInvsee"))));
    public String PermTPHERE = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermTPHERE"))));
    public String PermJail = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermJail"))));
    public String PermHealAnnotherPlayer = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermHealAnnotherPlayer"))));
    public String PermKick = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermKick"))));
    public String PermLevel = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermLevel"))));
    public String PermTeleport = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermTeleport"))));
    public String PermTime = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermTime"))));
    public String PermGetOtherHomes = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermGetOtherHomes"))));
    public String PermMaintenanceJoin = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermMaintenanceJoin"))));
    public String PermMaintenanceActivate = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermMaintenanceActivate"))));
    public String PermSetJail = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermSetJail"))));
    public String PermWeather = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermWeather"))));
    public String PermCensor = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermCensor"))));
    public String PermPrefixOwner = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(getConfig().getString("Perms.PermOwnerPrefix"))));
    public String PermPrefixCoOwner = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermCoOwnerPrefix")));
    public String PermPrefixSrAdmin = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermSrAdminPrefix")));
    public String PermPrefixAdmin = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermAdminPrefix")));
    public String PermPrefixSrDeveloper = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermSrDeveloperPrefix")));
    public String PermPrefixDeveloper = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermDeveloperPrefix")));
    public String PermPrefixTestDeveloper = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermTestDeveloperPrefix")));
    public String PermPrefixSrModerator = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermSrModeratorPrefix")));
    public String PermPrefixModerator = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermModeratorPrefix")));
    public String PermPrefixTestModerator = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermTestModeratorPrefix")));
    public String PermPrefixSrSupporter = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermSrSupporterPrefix")));
    public String PermPrefixSupporter = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermSupporterPrefix")));
    public String PermPrefixTestSupporter = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermTestSupporterPrefix")));
    public String PermPrefixSrBuilder = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermSrBuilderPrefix")));
    public String PermPrefixBuilder = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermBuilderPrefix")));
    public String PermPrefixTestBuilder = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermTestBuilderPrefix")));
    public String PermPrefixFriend = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermFriendPrefix")));
    public String PermPrefixYouTuber = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermYouTuberPrefix")));
    public String PermPrefixStreamer = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermStreamerPrefix")));
    public String PermPrefixPremiumThree = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermPremiumThreePrefix")));
    public String PermPrefixPremiumTwo = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermPremiumTwoPrefix")));
    public String PermPrefixPremiumOne = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermPremiumOnePrefix")));
    public String PermSeePlugins = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermSeePlugins")));
    public String PermsWorld = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermsWorld")));
    public String PermsBurn = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermsBurn")));
    public String PermsWarn = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermsWarn")));
    public String PermsNotifyWarn = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermsNotifyWarn")));
    public String PermsGetWarn = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermsGetWarn")));
    public String PermsRemoveWarn = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermsRemoveWarn")));
    public String PermsEnableSeeCommands = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermsEnableSeeCommands")));
    public String PermsListReports = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermsListReports")));
    public String PermsNotifyReports = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermsNotifyReports")));
    public String PermsCloseReport = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermsCloseReport")));
    public String PermsAcceptReport = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermsAcceptReport")));
    public String PermsServerRestart = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermsServerRestart")));
    public String PermsTPAll = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("Perms.PermsTPAll")));
}