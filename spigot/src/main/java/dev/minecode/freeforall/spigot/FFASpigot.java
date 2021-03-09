package dev.minecode.freeforall.spigot;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.spigot.CoreSpigot;
import dev.minecode.freeforall.api.FFAAPI;
import dev.minecode.freeforall.api.object.FFAPlayer;
import dev.minecode.freeforall.common.FFACommon;
import dev.minecode.freeforall.spigot.command.FFACommand;
import dev.minecode.freeforall.spigot.listener.*;
import dev.minecode.freeforall.spigot.manager.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.serialize.SerializationException;

public class FFASpigot extends JavaPlugin {

    private static FFASpigot instance;

    private CoreSpigot coreSpigot;
    private FFACommon ffaCommon;

    private BuildManager buildManager;
    private ItemManager itemManager;
    private KitManager kitManager;
    private RegionManager regionManager;
    private RewardManager rewardManager;
    private VaultManager vaultManager;

    public static FFASpigot getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        makeInstances();
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        CoreSpigot.getInstance().onDisable();
        FFAAPI.getInstance().getFileManager().getData().save();
        if (!CoreAPI.getInstance().getPluginManager().isUsingSQL())
            FFAAPI.getInstance().getFileManager().getStats().save();
    }

    private void makeInstances() {
        instance = this;
        coreSpigot = new CoreSpigot(this);
        ffaCommon = new FFACommon();

        buildManager = new BuildManager();
        itemManager = new ItemManager();
        kitManager = new KitManager();
        regionManager = new RegionManager();
        rewardManager = new RewardManager();
        vaultManager = new VaultManager();
    }

    private void registerCommands() {
        new FFACommand(getCommand("ffa"));
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
        if (buildManager.isBuildModeActivated())
            Bukkit.getPluginManager().registerEvents(new BuildListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new WeatherListener(), this);
    }

    public BuildManager getBuildManager() {
        return buildManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public RegionManager getRegionManager() {
        return regionManager;
    }

    public RewardManager getRewardManager() {
        return rewardManager;
    }

    public VaultManager getVaultManager() {
        return vaultManager;
    }
}
