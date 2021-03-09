package dev.minecode.freeforall.spigot.listener;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.freeforall.spigot.FFASpigot;
import dev.minecode.freeforall.spigot.object.FFALanguageSpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        CorePlayer corePlayer = CoreAPI.getInstance().getPlayerManager().getCorePlayer(player.getUniqueId());

        if (player.getItemInHand().isSimilar(FFASpigot.getInstance().getItemManager().getWandItem())) {
            if (player.hasPermission("ffa.setup")) {
                if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    FFASpigot.getInstance().getRegionManager().setPos1(player.getUniqueId(), event.getClickedBlock().getLocation());
                    player.sendMessage(CoreAPI.getInstance().getReplaceManager(corePlayer.getLanguage(), FFALanguageSpigot.ffaEventRegionSetPos1).chatcolorAll().getMessage());
                    event.setCancelled(true);
                } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    FFASpigot.getInstance().getRegionManager().setPos2(player.getUniqueId(), event.getClickedBlock().getLocation());
                    player.sendMessage(CoreAPI.getInstance().getReplaceManager(corePlayer.getLanguage(), FFALanguageSpigot.ffaEventRegionSetPos2).chatcolorAll().getMessage());
                    event.setCancelled(true);
                }
            }
        }
    }

}
