package dev.minecode.freeforall.spigot.manager;

import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.freeforall.api.FFAAPI;
import dev.minecode.freeforall.spigot.object.ItemBuilder;
import dev.minecode.freeforall.spigot.object.ItemSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Arrays;

public class KitManager {

    private ItemStack[] kitContents, kitArmorContents;

    private ConfigurationNode kitContentsNode, kitArmorContentsNode;

    public KitManager() {
        kitContentsNode = FFAAPI.getInstance().getFileManager().getData().getConf().node("kit", "contents");
        kitArmorContentsNode = FFAAPI.getInstance().getFileManager().getData().getConf().node("kit", "armorcontents");
        loadKit();
    }

    private void loadKit() {
        if (kitContentsNode.empty() && kitArmorContentsNode.empty()) {
            setKitContents(getDefaultContents());
            setKitArmorContents(getDefaultArmorContents());
            return;
        }

        setKitContents(ItemSerializer.stringToItems(kitContentsNode.getString()));
        setKitArmorContents(ItemSerializer.stringToItems(kitArmorContentsNode.getString()));
    }

    private void saveKit(ItemStack[] kitContents, ItemStack[] kitArmorContents) {
        String contentsString = ItemSerializer.itemsToString(kitContents);
        String armorContentsString = ItemSerializer.itemsToString(kitArmorContents);
        try {
            kitContentsNode.set(contentsString);
            kitArmorContentsNode.set(armorContentsString);
            FFAAPI.getInstance().getFileManager().getData().save();
        } catch (SerializationException e) {
            e.printStackTrace();
        }
    }

    private ItemStack[] getDefaultContents() {
        return new ItemStack[]{new ItemBuilder(Material.WOOD_SWORD)
                .setAmount(1)
                .setDisplayName("§6§k--§r§5§n§lPOWERSWORD§r§6§k--")
                .setUnbreakable(true)
                .setLore(Arrays.asList("This is a default kit", "Change it with §6/ffa setKit")).build()};
    }

    private ItemStack[] getDefaultArmorContents() {
        return new ItemStack[]{
                new ItemBuilder(Material.LEATHER_BOOTS).setAmount(1).build(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).setAmount(1).build(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).setAmount(1).build(),
                new ItemBuilder(Material.LEATHER_HELMET).setAmount(1).build()
        };
    }

    public ItemStack[] getKitContents() {
        return kitContents;
    }

    private void setKitContents(ItemStack[] kitContents) {
        this.kitContents = kitContents;
    }

    public ItemStack[] getKitArmorContents() {
        return kitArmorContents;
    }

    private void setKitArmorContents(ItemStack[] kitArmorContents) {
        this.kitArmorContents = kitArmorContents;
    }

    public void setKit(ItemStack[] kitContents, ItemStack[] kitArmorContents) {
        this.kitContents = kitContents;
        this.kitArmorContents = kitArmorContents;
        saveKit(kitContents, kitArmorContents);
    }

    public void setKitForPlayer(CorePlayer corePlayer) {
        Player player = Bukkit.getPlayer(corePlayer.getUuid());
        if (player != null) {
            player.getInventory().setContents(getKitContents());
            player.getInventory().setArmorContents(getKitArmorContents());
        }
    }
}
