package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.Registry;

public class DashMace {

    public static final String DASH_MACE_KEY = "dash_mace";

    private static NamespacedKey getKey() {
        return new NamespacedKey(Main.getInstance(), DASH_MACE_KEY);
    }

    public static boolean isDashMace(ItemStack item) {
        if (item == null || item.getType() != Material.MACE) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        return Boolean.TRUE.equals(pdc.get(getKey(), PersistentDataType.BOOLEAN));
    }

    public static ItemStack createDashMace() {
        ItemStack mace = new ItemStack(Material.MACE, 1);
        ItemMeta meta = mace.getItemMeta();
        if (meta == null) {
            return mace;
        }

        meta.displayName(Component.text("Dash Mace").color(NamedTextColor.AQUA));
        meta.lore(java.util.List.of(
                Component.text("Right-click in the air to dash forward!").color(NamedTextColor.GRAY),
                Component.text("Has a cooldown.").color(NamedTextColor.DARK_GRAY)
        ));

        Enchantment mending = Registry.ENCHANTMENT.get(NamespacedKey.minecraft("mending"));
        if (mending != null) {
            meta.addEnchant(mending, 1, true);
        }

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(getKey(), PersistentDataType.BOOLEAN, true);

        mace.setItemMeta(meta);
        return mace;
    }
}
