package org.hjun.plugin.hjuneventcanceller;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

public final class HjunEventCanceller extends JavaPlugin {
    Logger logger = getServer().getLogger();
    public static int blockplace = 1;
    public static HashSet<String> spblockplace = new HashSet<String>();
    public static int blockbreak = 1;
    public static HashSet<String> spblockbreak = new HashSet<String>();
    public static int allplayertp = 1;
    public static HashSet<org.bukkit.entity.Player> tptomehashset = new HashSet<org.bukkit.entity.Player>();
    public static HashSet<String> sptphashset = new HashSet<String>();
    public static int tntexplode = 1;
    public static int creeperexplode = 1;
    public static int explode = 1;
    public static int spreadevent = 1;
    public static int blockignite = 1;
    public static int lock = 0;
    List<String> spblockplacelist = new ArrayList<String>();
    List<String> spblockbreaklist = new ArrayList<String>();
    List<String> spteleportlist = new ArrayList<String>();
    // 1 = true, 0 = false

    //for allaypoop
    public static int Allay = 0;
    public static int allaypoop = 0;
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new HECevent(), this);
        getCommand("hjuneventcanceller").setExecutor(new HECcommand());
        getCommand("hecreload");
        logger.info("[EventCanceller] Hjun Event Canceller has started!");

        saveDefaultConfig();
        File cfile = new File(getDataFolder(), "config.yml");
        if (cfile.length() == 0) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
        //config.yml 파일 불러오기
        lock = getConfig().getInt("player-lock");
        blockplace = getConfig().getInt("blockplace.all-player");
        spblockplace.addAll(getConfig().getStringList("blockplace.specific-player-false"));
        blockbreak = getConfig().getInt("blockbreak.all-player");
        spblockbreak.addAll(getConfig().getStringList("blockbreak.specific-player-false"));
        allplayertp = getConfig().getInt("teleport.all-player");
        tntexplode = getConfig().getInt("explode.tnt");
        creeperexplode = getConfig().getInt("explode.creeper");
        explode = getConfig().getInt("explode.all");
        spreadevent = getConfig().getInt("spread-event");
        blockignite = getConfig().getInt("blockignite-event");
        allaypoop = getConfig().getInt("allaypoop");
    }

    @Override
    public void onDisable() {
        spblockplacelist.addAll(spblockplace);
        getConfig().set("blockplace.specific-player-false", spblockplacelist);

        spblockbreaklist.addAll(spblockbreak);
        getConfig().set("blockbreak.specific-player-false", spblockbreaklist);

        spteleportlist.addAll(sptphashset);
        getConfig().set("teleport.specific-player-false", spteleportlist);

        saveDefaultConfig();

        logger.info("[EventCanceller] Hjun Event Canceller has stoped!");
    }
}
