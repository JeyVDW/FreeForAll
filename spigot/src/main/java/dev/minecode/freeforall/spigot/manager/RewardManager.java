package dev.minecode.freeforall.spigot.manager;

import dev.minecode.coins.api.CoinsAPI;
import dev.minecode.coins.api.object.CoinsPlayer;
import dev.minecode.core.api.CoreAPI;
import dev.minecode.freeforall.api.FFAAPI;
import dev.minecode.freeforall.api.object.FFAPlayer;
import dev.minecode.freeforall.spigot.FFASpigot;
import org.bukkit.Bukkit;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;

public class RewardManager {

    private ConfigurationNode configNode = FFAAPI.getInstance().getFileManager().getConfig().getConf();

    private int pointsOnKill, pointsOnDeath, moneyOnKill, moneyOnDeath;
    private boolean moneyEnabled, vaultEnabled, commandsEnabled;
    private List<String> commandsOnKill, commandsOnDeath;

    public RewardManager() {
        reload();
    }

    private void reload() {
        pointsOnKill = configNode.node("rewards", "points", "kill").getInt();
        pointsOnDeath = configNode.node("rewards", "points", "death").getInt();
        moneyEnabled = configNode.node("rewards", "money", "enable").getBoolean();
        vaultEnabled = configNode.node("rewards", "money", "vault").getBoolean();
        moneyOnKill = configNode.node("rewards", "money", "kill").getInt();
        moneyOnDeath = configNode.node("rewards", "money", "death").getInt();
        commandsEnabled = configNode.node("rewards", "commands", "enable").getBoolean();
        try {
            commandsOnKill = configNode.node("rewards", "commands", "kill").getList(String.class);
            commandsOnDeath = configNode.node("rewards", "commands", "kill").getList(String.class);
        } catch (SerializationException e) {
            e.printStackTrace();
        }
    }

    public void killReward(FFAPlayer ffaPlayer) {
        ffaPlayer.addPoints(pointsOnKill);
        ffaPlayer.save();
        if (moneyEnabled) {
            if (vaultEnabled) {
                FFASpigot.getInstance().getVaultManager().getEcon().depositPlayer(Bukkit.getOfflinePlayer(ffaPlayer.getCorePlayer().getUuid()), moneyOnKill);
            } else {
                if (!Bukkit.getPluginManager().isPluginEnabled("Coins")) {
                    Bukkit.getLogger().warning("§cFFA &8» &7 No money could be given because neither Vault or Coins is activated");
                    return;
                }
                CoinsPlayer coinsPlayer = CoinsAPI.getInstance().getPlayerManager().getCoinsPlayer(ffaPlayer.getCorePlayer().getUuid());
                coinsPlayer.save();
            }
        }

        if (commandsEnabled) {
            for (String commands : commandsOnKill)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        CoreAPI.getInstance().getReplaceManager(commands).corePlayer(ffaPlayer.getCorePlayer(), "target").getMessage());
        }
    }

    public void deathReward(FFAPlayer ffaPlayer) {
        ffaPlayer.removePoints(pointsOnDeath);
        ffaPlayer.save();
        if (moneyEnabled) {
            if (vaultEnabled) {
                FFASpigot.getInstance().getVaultManager().getEcon().withdrawPlayer(Bukkit.getOfflinePlayer(ffaPlayer.getCorePlayer().getUuid()), moneyOnDeath);
            } else {
                if (!Bukkit.getPluginManager().isPluginEnabled("Coins")) {
                    Bukkit.getLogger().warning("§cFFA &8» &7 No money could be removed because neither Vault or Coins is activated");
                    return;
                }

                CoinsPlayer coinsPlayer = CoinsAPI.getInstance().getPlayerManager().getCoinsPlayer(ffaPlayer.getCorePlayer().getUuid());
                coinsPlayer.removeCoins(moneyOnDeath);
                coinsPlayer.save();
            }
        }

        if (commandsEnabled) {
            for (String commands : commandsOnDeath)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        CoreAPI.getInstance().getReplaceManager(commands).corePlayer(ffaPlayer.getCorePlayer(), "target").getMessage());
        }
    }

    public int getPointsOnKill() {
        return pointsOnKill;
    }

    public int getPointsOnDeath() {
        return pointsOnDeath;
    }

    public boolean isMoneyEnabled() {
        return moneyEnabled;
    }

    public boolean isVaultEnabled() {
        return vaultEnabled;
    }

    public int getMoneyOnKill() {
        return moneyOnKill;
    }

    public int getMoneyOnDeath() {
        return moneyOnDeath;
    }

    public boolean isCommandsEnabled() {
        return commandsEnabled;
    }

    public List<String> getCommandsOnKill() {
        return commandsOnKill;
    }

    public List<String> getCommandsOnDeath() {
        return commandsOnDeath;
    }

}
