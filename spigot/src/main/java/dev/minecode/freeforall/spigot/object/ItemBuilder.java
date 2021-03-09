package dev.minecode.freeforall.spigot.object;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta itemMeta;

    public ItemBuilder(Material mat) {
        item = new ItemStack(mat);
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder(Material mat, short subID) {
        item = new ItemStack(mat, 1, subID);
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder(ItemStack is) {
        item = is;
        itemMeta = is.getItemMeta();
    }

    public ItemBuilder setDisplayName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setColor(Color color) {
        LeatherArmorMeta meta = (LeatherArmorMeta) itemMeta;
        meta.setColor(color);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment e, int level) {
        itemMeta.addEnchant(e, level, true);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment e, int level) {
        item.addUnsafeEnchantment(e, level);
        return this;
    }

    public ItemBuilder addEnchantmentMap(Map<Enchantment, Integer> map) {
        item.addEnchantments(map);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        itemMeta.spigot().setUnbreakable(unbreakable);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(itemMeta);
        return item;
    }
}
