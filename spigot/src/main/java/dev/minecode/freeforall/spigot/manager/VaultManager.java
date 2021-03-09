package dev.minecode.freeforall.spigot.manager;

import dev.minecode.freeforall.api.FFAAPI;
import dev.minecode.freeforall.spigot.FFASpigot;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.spongepowered.configurate.ConfigurationNode;

public class VaultManager {

    private Economy econ;
    private Plugin vaultPlugin;

    private boolean vaultEnabled;

    // config
    private boolean vaultConfig;

    public VaultManager() {
        if ((vaultPlugin = FFASpigot.getInstance().getServer().getPluginManager().getPlugin("Vault")) == null) return;

        ConfigurationNode conf = FFAAPI.getInstance().getFileManager().getConfig().getConf();
        vaultConfig = conf.node("rewards", "money", "vault").getBoolean();

        if (!vaultConfig && !vaultPlugin.isEnabled()) return;

        if (setupEconomy()) {
            vaultEnabled = true;
        }
    }

    public boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = FFASpigot.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;

        return (econ = rsp.getProvider()) != null;
    }

    public Economy getEcon() {
        return econ;
    }

    public boolean isVaultEnabled() {
        return vaultEnabled;
    }

    public void setVaultEnabled(boolean vaultEnabled) {
        this.vaultEnabled = vaultEnabled;
    }
}
