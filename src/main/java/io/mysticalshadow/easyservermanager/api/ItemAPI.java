package io.siedlermc.easysiedlermanager.api;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemAPI {
    public static ItemStack createItemNoEnch(Material mat, int amount, int shortid, String DisplayName) {
        short s = (short) shortid;
        ItemStack item = new ItemStack(mat, amount, s);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(DisplayName);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack createItemNoEnch2(Material mat, int amount, int shortid, String DisplayName) {
        short s = (short) shortid;
        ItemStack item = new ItemStack(mat, amount, s, (byte) s);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(DisplayName);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack createPlayerHead(String Owner, String name, int amount, int shortId, String DisplayName) {
        ItemStack skull = new ItemStack(Material.LEGACY_SKULL_ITEM, amount);
        SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
        skullmeta.setOwner(Owner);
        skullmeta.setDisplayName(DisplayName);
        skullmeta.setDisplayName(name);
        return skull;
    }
}