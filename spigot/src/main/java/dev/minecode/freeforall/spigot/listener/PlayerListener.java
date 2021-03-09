package dev.minecode.freeforall.spigot.listener;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.Language;
import dev.minecode.freeforall.api.FFAAPI;
import dev.minecode.freeforall.api.object.FFAPlayer;
import dev.minecode.freeforall.spigot.FFASpigot;
import dev.minecode.freeforall.spigot.object.FFALanguageSpigot;
import dev.minecode.freeforall.spigot.object.LocationSerializer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.spongepowered.configurate.ConfigurationNode;

public class PlayerListener implements Listener {

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FFAPlayer ffaPlayer = FFAAPI.getInstance().getPlayerManager().getFFAPlayer(player.getUniqueId());

        event.setJoinMessage(null);
        for (Language language : CoreAPI.getInstance().getLanguageManager().getAllLanguages()) {
            String joinMessage = CoreAPI.getInstance().getLanguageManager().getString(language, FFALanguageSpigot.ffaEventJoinJoinMessage);
            if (!joinMessage.isEmpty())
                for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                    if (CoreAPI.getInstance().getPlayerManager().getCorePlayer(onlinePlayer.getUniqueId()).getLanguage() == language)
                        onlinePlayer.sendMessage(CoreAPI.getInstance().getReplaceManager(joinMessage)
                                .corePlayer(ffaPlayer.getCorePlayer(), "player").chatcolorAll().getMessage());
        }


        player.setHealth(20);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.ADVENTURE);

        FFASpigot.getInstance().getKitManager().setKitForPlayer(ffaPlayer.getCorePlayer());

        ConfigurationNode spawnNode = FFAAPI.getInstance().getFileManager().getData().getConf().node("spawn");
        if (spawnNode.empty()) {
            player.sendMessage(CoreAPI.getInstance().getReplaceManager(ffaPlayer.getCorePlayer().getLanguage(), FFALanguageSpigot.ffaEventJoinSpawnNotSet).chatcolorAll().getMessage());
            return;
        }

        player.teleport(LocationSerializer.stringToLocation(spawnNode.getString()));
    }

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        CorePlayer corePlayer = CoreAPI.getInstance().getPlayerManager().getCorePlayer(player.getUniqueId());

        event.setQuitMessage(null);
        for (Language language : CoreAPI.getInstance().getLanguageManager().getAllLanguages()) {
            String quitMessage = CoreAPI.getInstance().getLanguageManager().getString(language, FFALanguageSpigot.ffaEventQuitQuitMessage);
            if (!quitMessage.isEmpty())
                for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                    if (CoreAPI.getInstance().getPlayerManager().getCorePlayer(onlinePlayer.getUniqueId()).getLanguage() == language)
                        onlinePlayer.sendMessage(CoreAPI.getInstance().getReplaceManager(quitMessage)
                                .corePlayer(corePlayer, "player").chatcolorAll().getMessage());
        }

        FFASpigot.getInstance().getBuildManager().removePlayer(player.getUniqueId());
    }

    @EventHandler
    public void handlePlayerDeath(PlayerDeathEvent event) {
        Player target = event.getEntity();
        FFAPlayer ffaTarget = FFAAPI.getInstance().getPlayerManager().getFFAPlayer(target.getUniqueId());

        event.setDeathMessage(null);
        event.getDrops().clear();

        ffaTarget.addDeaths(1);
        ffaTarget.save();

        FFASpigot.getInstance().getRewardManager().deathReward(ffaTarget);

        Player killer = target.getKiller();
        if (killer != null && killer != target) {
            FFAPlayer ffaKiller = FFAAPI.getInstance().getPlayerManager().getFFAPlayer(killer.getUniqueId());
            ffaKiller.addKills(1);
            ffaKiller.save();

            FFASpigot.getInstance().getRewardManager().killReward(ffaKiller);

            if (killer.hasPotionEffect(PotionEffectType.REGENERATION))
                killer.removePotionEffect(PotionEffectType.REGENERATION);
            killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2 * 20, 20 * 2, true));
            killer.sendMessage(FFAAPI.getInstance().getReplaceManager(ffaKiller.getCorePlayer().getLanguage(), FFALanguageSpigot.ffaEventKilled)
                    .ffaPlayer(ffaTarget, "target").chatcolorAll().getMessage());
            target.sendMessage(FFAAPI.getInstance().getReplaceManager(ffaTarget.getCorePlayer().getLanguage(), FFALanguageSpigot.ffaEventKilledBy)
                    .ffaPlayer(ffaKiller, "killer").chatcolorAll().getMessage());
        } else
            target.sendMessage(CoreAPI.getInstance().getReplaceManager(ffaTarget.getCorePlayer().getLanguage(), FFALanguageSpigot.ffaEventDied).chatcolorAll().getMessage());

        Bukkit.getScheduler().runTaskLater(FFASpigot.getInstance(), new Runnable() {
            @Override
            public void run() {
                target.spigot().respawn();
            }
        }, 1);
    }

    @EventHandler
    public void handlePlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        CorePlayer corePlayer = CoreAPI.getInstance().getPlayerManager().getCorePlayer(player.getUniqueId());

        FFASpigot.getInstance().getKitManager().setKitForPlayer(corePlayer);
        Bukkit.getScheduler().runTaskLater(FFASpigot.getInstance(), new Runnable() {
            @Override
            public void run() {
                ConfigurationNode spawnNode = FFAAPI.getInstance().getFileManager().getData().getConf().node("spawn");
                if (spawnNode.empty()) {
                    player.sendMessage(CoreAPI.getInstance().getReplaceManager(corePlayer.getLanguage(), FFALanguageSpigot.ffaEventJoinSpawnNotSet).chatcolorAll().getMessage());
                    return;
                }
                player.teleport(LocationSerializer.stringToLocation(spawnNode.getString()));
            }
        }, 1);
    }

    @EventHandler
    public void handFoodChange(FoodLevelChangeEvent event) {
        /*if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (FFASpigot.getInstance().getRegionManager().isInRegion(player.getLocation())) {
            */
        event.setCancelled(true);/*
        } else {
            event.setCancelled(false);
        }*/
    }


}
