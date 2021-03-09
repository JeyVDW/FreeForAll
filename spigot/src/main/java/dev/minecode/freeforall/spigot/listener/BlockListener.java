package dev.minecode.freeforall.spigot.listener;

import dev.minecode.freeforall.spigot.FFASpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void handleBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand().isSimilar(FFASpigot.getInstance().getItemManager().getWandItem())) {
            if (player.hasPermission("ffa.setup")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handleBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand().isSimilar(FFASpigot.getInstance().getItemManager().getWandItem())) {
            if (player.hasPermission("ffa.setup")) {
                event.setCancelled(true);
            }
        }
    }

}
