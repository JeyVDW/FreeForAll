package dev.minecode.freeforall.spigot.listener;

import dev.minecode.freeforall.spigot.FFASpigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class BuildListener implements Listener {

    @EventHandler
    public void handlePlayerItemDrop(PlayerDropItemEvent event) {
        if (!FFASpigot.getInstance().getBuildManager().isBuild(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerItemPickup(PlayerPickupItemEvent event) {
        if (!FFASpigot.getInstance().getBuildManager().isBuild(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    public void handleBlockBreak(BlockBreakEvent event) {
        if (!FFASpigot.getInstance().getBuildManager().isBuild(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    public void handleBlockPlace(BlockPlaceEvent event) {
        if (!FFASpigot.getInstance().getBuildManager().isBuild(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }
}
