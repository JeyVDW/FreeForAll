package dev.minecode.freeforall.spigot.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.freeforall.api.FFAAPI;
import dev.minecode.freeforall.spigot.FFASpigot;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class BuildManager {

    private ArrayList<UUID> buildPlayers;

    public BuildManager() {
        buildPlayers = new ArrayList<>();
    }

    public boolean addPlayer(UUID uuid) {
        if (buildPlayers.add(uuid)) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                player.getInventory().clear();
                player.setExp(0);
                player.setGameMode(GameMode.CREATIVE);
                return true;
            }
        }
        return false;
    }

    public boolean removePlayer(UUID uuid) {
        if (buildPlayers.remove(uuid)) {
            Player player = Bukkit.getPlayer(uuid);
            player.getInventory().clear();
            player.setExp(0);
            player.setGameMode(GameMode.ADVENTURE);

            FFASpigot.getInstance().getKitManager().setKitForPlayer(CoreAPI.getInstance().getPlayerManager().getCorePlayer(uuid));
            return true;
        }
        return false;
    }

    public boolean isBuild(UUID uuid) {
        return buildPlayers.contains(uuid);
    }

    public boolean isBuildModeActivated() {
        return FFAAPI.getInstance().getFileManager().getConfig().getConf().node("buildmode").getBoolean();
    }

    public ArrayList<UUID> getBuildPlayers() {
        return buildPlayers;
    }
}
