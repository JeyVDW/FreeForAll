package dev.minecode.freeforall.spigot.manager;

import dev.minecode.freeforall.spigot.object.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ItemManager {

    private ItemStack wandItem;

    public ItemManager() {
        createWandItem();
    }

    private void createWandItem() {
        setWandItem(new ItemBuilder(Material.BONE)
                .setAmount(1)
                .setDisplayName("§6§l§nWanditem")
                .setUnbreakable(true)
                .setLore(Arrays.asList("§7Click on blocks to set the locations and run §6/ffa setregion"))
                .build());
    }

    public void setWandItem(ItemStack wandItem) {
        this.wandItem = wandItem;
    }

    public ItemStack getWandItem() {
        return wandItem;
    }
}
