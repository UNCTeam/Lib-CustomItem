package fr.teamunc.customitem_unclib.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

@Getter
@AllArgsConstructor
public abstract class UNCCustomType {
    private String name;
    private ArrayList<String> lore;
    private int modelData;
    private int maxDurability;
    private boolean unbreakable;
    /**
     * example : "AMETHYST_SWORD"
     */
    private String customKey;
    private Material bukkitMaterial;

    private UNCAction action;

    public ItemStack createCustomItem(int amount) {
        ItemStack res = new ItemStack(getBukkitMaterial(), amount);
        ItemMeta meta = createCustomItemMeta(true);

        EnumMap<CustomNamespaceKey, List<String>> data = new EnumMap<>(CustomNamespaceKey.class);
        updateLores(meta, data);

        res.setItemMeta(meta);

        return res;
    }

    protected ItemMeta createCustomItemMeta(boolean withDurability) {
        ItemMeta res = new ItemStack(getBukkitMaterial()).getItemMeta();
        res.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        res.setDisplayName("§r" + getName());
        res.setCustomModelData(getModelData());

        CustomNamespaceKey.CUSTOM_UNBREAKABLE.setCustomData(res, (byte) (isUnbreakable() ? 1 : 0));
        if (withDurability) CustomNamespaceKey.CUSTOM_DURABILITY.setCustomData(res, new int[]{getMaxDurability(), getMaxDurability()});
        CustomNamespaceKey.CUSTOM_TYPE.setCustomData(res, getCustomKey());
        /**
         * IMPORTANT : The lore must be set in the createCustomItem method
         */

        return res;
    }

    protected ArrayList<String> getBaseLores(EnumMap<CustomNamespaceKey, List<String>> data) {
        ArrayList<String> res = new ArrayList<>(getLore());
        res.add("");

        if (isUnbreakable()) res.add("§r§7Unbreakable");

        for (CustomNamespaceKey customNamespaceKey : CustomNamespaceKey.values()) {
            List<String> o = data.get(customNamespaceKey);
            if (o != null) {
                String row = customNamespaceKey.getLoreRow(o);
                res.add(row);
            }
        }

        return res;
    }

    public void updateLores(ItemMeta meta, EnumMap<CustomNamespaceKey, List<String>> data) {
        // refill with default lores
        CustomNamespaceKey.refillEmptyDataLore(data, meta);

        ArrayList<String> newLore = getBaseLores(data);
        meta.setLore(newLore);
    }
}
