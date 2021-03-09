package dev.minecode.freeforall.spigot.listener;

import dev.minecode.freeforall.spigot.FFASpigot;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void handleHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            if (FFASpigot.getInstance().getRegionManager().isInRegion(player.getLocation()) || FFASpigot.getInstance().getRegionManager().isInRegion(damager.getLocation())) {
                event.setCancelled(true);
                return;
            } else {
                event.setCancelled(false);
                return;
            }
        } else if (event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                Player damager = (Player) arrow.getShooter();
                if (FFASpigot.getInstance().getRegionManager().isInRegion(player.getLocation()) || FFASpigot.getInstance().getRegionManager().isInRegion(damager.getLocation())) {
                    event.setCancelled(true);
                    return;
                } else {
                    event.setCancelled(false);
                    return;
                }
            } else {
                event.setCancelled(false);
                return;
            }
        } else if (event.getDamager() instanceof FishHook) {
            FishHook fishHook = (FishHook) event.getDamager();
            if (fishHook.getShooter() instanceof Player) {
                Player damager = (Player) fishHook.getShooter();
                if (FFASpigot.getInstance().getRegionManager().isInRegion(player.getLocation()) || FFASpigot.getInstance().getRegionManager().isInRegion(damager.getLocation())) {
                    event.setCancelled(true);
                    return;
                } else {
                    event.setCancelled(false);
                    return;
                }
            } else {
                event.setCancelled(false);
                return;
            }
        }
        event.setCancelled(false);

    }

}
