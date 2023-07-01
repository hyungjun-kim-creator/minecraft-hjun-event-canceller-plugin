package org.hjun.plugin.hjuneventcanceller;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.awt.Color;
import java.util.Iterator;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.Bukkit.*;
import static org.bukkit.ChatColor.COLOR_CHAR;
import static org.bukkit.DyeColor.BROWN;
import static org.bukkit.Material.BROWN_DYE;
import static org.bukkit.Sound.*;
import static org.hjun.plugin.hjuneventcanceller.HjunEventCanceller.*;

public class HECevent implements Listener {
    public static HjunEventCanceller plugin;

    public static void setPlugin(HjunEventCanceller MainPlugin) {
        plugin = MainPlugin;
    }
    @EventHandler
    public void onjoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        p.sendMessage("환영합니다 " + String.valueOf(event.getPlayer().getName()) + "님");
    }

    @EventHandler
    public void placeblockevent(BlockPlaceEvent event){
        Player p = event.getPlayer();
        if (plugin.blockplace == 0){
            event.setCancelled(true);
            p.sendMessage(ChatColor.DARK_RED + "현재 블럭 설치가 비활성화되어 있습니다.");
        }
        if(spblockplace.contains(p.getName())){
            event.setCancelled(true);
            p.sendMessage(ChatColor.DARK_RED + "현재 "+ p.getName() + "에게 블럭 설치가 비활성화되어 있습니다.");
        }
    }

    @EventHandler
    public void breakblockevent(BlockBreakEvent event){
        Player p = event.getPlayer();
        if (plugin.blockbreak == 0){
            event.setCancelled(true);
            p.sendMessage(ChatColor.DARK_RED + "현재 블럭 파괴가 비활성화 되어있습니다.");
        }
        if(spblockbreak.contains(p.getName())){
            event.setCancelled(true);
            p.sendMessage(ChatColor.DARK_RED + "현재 "+ p.getName() + "에게 블럭 파괴가 비활성화되어 있습니다.");
        }
    }

    @EventHandler
    public void allplayertpevent(PlayerTeleportEvent event){
        Player p = event.getPlayer();
        if (plugin.allplayertp == 0){
            event.setCancelled(true);
            p.sendMessage(ChatColor.DARK_RED + "현재 tp가 비활성화되어 있습니다.");
        }
    }

    @EventHandler
    public void tptomeevent(PlayerTeleportEvent event){
        Player p = event.getPlayer();

        Iterator tptomeiter = tptomehashset.iterator();

        while(tptomeiter.hasNext()){
            Player tomeeventtarget = (Player) tptomeiter.next();
            if(tomeeventtarget.getLocation().getX() == event.getTo().getX()){
                if(tomeeventtarget.getLocation().getY() == event.getTo().getY()){
                    if(tomeeventtarget.getLocation().getZ() == event.getTo().getZ()){
                        event.setCancelled(true);
                        p.sendMessage(ChatColor.DARK_RED + "현재 " +  String.valueOf(tomeeventtarget.getName()) + "에게 tp가 비활성화되어 있습니다.");
                    }
                }
            }
        }
    }

    @EventHandler
    public void sptp(PlayerTeleportEvent event){
        Player p = event.getPlayer();

        Iterator sptpiter = sptphashset.iterator();

        while(sptpiter.hasNext()){
            Player sptpplayer = (Player) sptpiter.next();
            if(p==sptpplayer){
                event.setCancelled(true);
                p.sendMessage(ChatColor.DARK_RED + "현재 " +  String.valueOf(sptpplayer.getName()) + "이 tp 하는 것이 비활성화되어 있습니다.");
            }
        }
    }

    @EventHandler
    public void tntexplode(EntityExplodeEvent event){
        if(tntexplode == 0){
            if(String.valueOf(event.getEntityType()).equals("PRIMED_TNT")){
                event.setCancelled(true);
                getServer().broadcastMessage(ChatColor.DARK_RED + "현재 TNT 폭발이 허용되어 있지 않습니다.");
            }
        }
    }

    @EventHandler
    public void creeperexplode(EntityExplodeEvent event){
        if(creeperexplode == 0){
            if(String.valueOf(event.getEntityType()).equals("CREEPER")){
                event.setCancelled(true);
                getServer().broadcastMessage(ChatColor.DARK_RED + "현재 크리퍼 폭발이 허용되어 있지 않습니다.");
            }
        }
    }

    @EventHandler
    public void blockexplode(BlockExplodeEvent event){
        if(explode == 0){
            event.setCancelled(true);
            getServer().broadcastMessage(ChatColor.DARK_RED + "현재 모든 폭발이 허용되어 있지 않습니다.");
        }
    }
    @EventHandler
    public void entityexplode(EntityExplodeEvent event){
        if(explode == 0){
            event.setCancelled(true);
            getServer().broadcastMessage(ChatColor.DARK_RED + "현재 모든 폭발이 허용되어 있지 않습니다.");
        }
    }

    @EventHandler
    public void spreadevent(BlockSpreadEvent event){
        if(spreadevent == 0){
            event.setCancelled(true);
            getServer().broadcastMessage(ChatColor.DARK_RED + "현재 Spread 이벤트가 허용되어 있지 않습니다.");
        }
    }

    @EventHandler
    public void blockignite(BlockIgniteEvent event){
        if(blockignite == 0){
            event.setCancelled(true);
            getServer().broadcastMessage(ChatColor.DARK_RED + "현재 블럭 연소 이벤트가 허용되어 있지 않습니다.");
        }
    }
}
