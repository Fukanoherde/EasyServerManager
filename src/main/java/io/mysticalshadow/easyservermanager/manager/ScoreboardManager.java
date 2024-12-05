package io.siedlermc.easysiedlermanager.manager;

import io.siedlermc.easysiedlermanager.EasySiedlerManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ScoreboardManager {

    private EasySiedlerManager plugin;
    public static Scoreboard board;
    public static HashMap<Scoreboard, Player> boards = new HashMap();

    public ScoreboardManager(EasySiedlerManager plugin) {
        this.plugin = plugin;
    }

    public static void setBoard(Player p) {
        File filePerPlayer = new File("plugins//EasySiedlerManager//Players", p.getUniqueId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(filePerPlayer);

        board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("test", "bbb");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§b§lSiedler§3§lMC.de");
        obj.getScore("§l      ").setScore(14);
        obj.getScore("§7§lDatum:").setScore(13);
        obj.getScore("§l    ").setScore(11);
        obj.getScore("§7§lDeine Level:").setScore(10);
        obj.getScore("§l").setScore(8);
        obj.getScore("§7§lUnser Forum:").setScore(7);
        obj.getScore("§b§lsiedlermc.de").setScore(6);
        obj.getScore("§l ").setScore(5);
        obj.getScore("§7§lDein Rang:").setScore(4);
        if (p.hasPermission("prefix.owner")) {
            obj.getScore("§4§lInhaber").setScore(3);
        } else if (p.hasPermission("prefix.admin")) {
            obj.getScore("§c§lPrimeAdmin").setScore(3);
        } else if (p.hasPermission("prefix.srdev")) {
            obj.getScore("§b§lSrDeveloper").setScore(3);
        } else if (p.hasPermission("prefix.dev")) {
            obj.getScore("§b§lDeveloper").setScore(3);
        } else if (p.hasPermission("prefix.tdev")) {
            obj.getScore("§b§lTest-Developer").setScore(3);
        } else if (p.hasPermission("prefix.srmod")) {
            obj.getScore("§3§lSrModerator").setScore(3);
        } else if (p.hasPermission("prefix.mod")) {
            obj.getScore("§3§lModerator").setScore(3);
        } else if (p.hasPermission("prefix.tmod")) {
            obj.getScore("§3§lTest-Moderator").setScore(3);
        } else if (p.hasPermission("prefix.srsupporter")) {
            obj.getScore("§1§lSrSupporter").setScore(3);
        } else if (p.hasPermission("prefix.supporter")) {
            obj.getScore("§1§lSupporter").setScore(3);
        } else if (p.hasPermission("prefix.tsupporter")) {
            obj.getScore("§1§lTest-Supporter").setScore(3);
        } else if (p.hasPermission("prefix.srbuilder")) {
            obj.getScore("§a§lSrBuilder").setScore(3);
        } else if (p.hasPermission("prefix.builder")) {
            obj.getScore("§a§lBuilder").setScore(3);
        } else if (p.hasPermission("prefix.tbuilder")) {
            obj.getScore("§a§lTest-Builder").setScore(3);
        } else if (p.hasPermission("prefix.youtuber")) {
            obj.getScore("§5§lYouTuber").setScore(3);
        } else if (p.hasPermission("prefix.premium")) {
            obj.getScore("§6§l§lPremium").setScore(3);
        } else {
            obj.getScore("§8§lEinwohner").setScore(3);
        }
        Team owner = board.registerNewTeam("aaa");
        Team admin = board.registerNewTeam("bbb");
        Team developer = board.registerNewTeam("ccc");
        Team supporter = board.registerNewTeam("kkk");
        Team tsupporter = board.registerNewTeam("lll");
        Team builder = board.registerNewTeam("nnn");
        Team tbuilder = board.registerNewTeam("ooo");
        Team spieler = board.registerNewTeam("rrr");

        p.setScoreboard(board);

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.hasPermission("prefix.owner")) {
                if (config.getBoolean(p.getName() + ".PvP.Activated", Boolean.valueOf(true))) {
                        owner.setPrefix("§4§lInhaber §8| ");
                        owner.setSuffix(" §8[§2PvP§8]");
                        owner.addPlayer(all);
                    } else {
                    owner.setPrefix("§4§lInhaber §8| ");
                    owner.addPlayer(all);
                }
            } else if (all.hasPermission("prefix.admin")) {
                if (config.getBoolean(all.getName() + ".PvP.Activated", Boolean.valueOf(true))) {
                    admin.setPrefix("§c§lAdmin §8| ");
                    admin.setSuffix(" §8[§2PvP§8]");
                    admin.addPlayer(all);
                } else {
                    admin.setPrefix("§c§lAdmin §8| ");
                    admin.addPlayer(all);
                }
            } else if (all.hasPermission("prefix.dev")) {
                if (config.getBoolean(all.getName() + ".PvP.Activated", Boolean.valueOf(true))) {
                    developer.setPrefix("§b§lDev §8| ");
                    developer.setSuffix(" §8[§2PvP§8]");
                    developer.addPlayer(all);
                } else {
                    developer.setPrefix("§b§lDev §8| ");
                    developer.addPlayer(all);
                }
            } else if (all.hasPermission("prefix.supporter")) {
                if (config.getBoolean(all.getName() + ".PvP.Activated", Boolean.valueOf(true))) {
                    supporter.setPrefix("§1§lSupporter §8| ");
                    supporter.setSuffix(" §8[§2PvP§8]");
                    supporter.addPlayer(all);
                } else {
                    supporter.setPrefix("§1§lSupporter §8| ");
                    supporter.addPlayer(all);
                }
            } else if (all.hasPermission("prefix.t-supporter")) {
                if (config.getBoolean(all.getName() + ".PvP.Activated", Boolean.valueOf(true))) {
                    tsupporter.setPrefix("§1§lT-Supporter §8| ");
                    supporter.setSuffix(" §8[§2PvP§8]");
                    tsupporter.addPlayer(all);
                } else {
                    tsupporter.setPrefix("§1§lT-Supporter §8| ");
                    tsupporter.addPlayer(all);
                }
            } else if (all.hasPermission("prefix.builder")) {
                if (config.getBoolean(all.getName() + ".PvP.Activated", Boolean.valueOf(true))) {
                    builder.setPrefix("§a§lBuilder §8| ");
                    builder.setSuffix(" §8[§2PvP§8]");
                    builder.addPlayer(all);
                } else {
                    builder.setPrefix("§a§lBuilder §8| ");
                    builder.addPlayer(all);
                }
            } else if (all.hasPermission("prefix.t-builder")) {
                if (config.getBoolean(all.getName() + ".PvP.Activated", Boolean.valueOf(true))) {
                    tbuilder.setPrefix("§a§lT-Builder §8| ");
                    builder.setSuffix(" §8[§2PvP§8]");
                    tbuilder.addPlayer(all);
                } else {
                    tbuilder.setPrefix("§a§lT-Builder §8| ");
                    tbuilder.addPlayer(all);
                }
            } else {
                if (config.getBoolean(all.getName() + ".PvP.Activated", Boolean.valueOf(true))) {
                    spieler.setPrefix("§8§lEinwohner §8| ");
                    spieler.setSuffix(" §8[§2PvP§8]");
                    spieler.addPlayer(all);
                } else {
                    spieler.setPrefix("§8§lEinwohner §8| ");
                    spieler.addPlayer(all);
                }
            }
        }
    }
    public static void updateBoard (Player p) {
        File filePerPlayer = new File("plugins//EasySiedlerManager//Players", p.getUniqueId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(filePerPlayer);
        board = p.getScoreboard();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String orginal = date.format(new Date());
        Objective obj = board.getObjective("test");
        obj.getScore("§e" + orginal).setScore(12);
        if (config.isSet(p.getName() + "." + "Level")) {
            try {
                config.load(filePerPlayer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
            obj.getScore("§e" + config.getInt(p.getName() + "." + "Level")).setScore(9);
        } else {
            obj.getScore("§4Not Found!").setScore(9);
        }
    }
}