package fr.teamunc.customitem_unclib.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
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

    public abstract ItemStack createCustomItem(int amount);

    protected ItemMeta createCustomItemMeta() {
        ItemMeta res = new ItemStack(getBukkitMaterial()).getItemMeta();
        res.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        res.setDisplayName("§r" + getName());
        res.setCustomModelData(getModelData());

        res.getPersistentDataContainer().set(CustomNamespaceKey.CUSTOM_UNBREAKABLE.getNamespaceKey(), PersistentDataType.BYTE, (byte) (isUnbreakable() ? 1 : 0));
        res.getPersistentDataContainer().set(CustomNamespaceKey.CUSTOM_DURABILITY.getNamespaceKey(), PersistentDataType.INTEGER_ARRAY, new int[]{getMaxDurability(), getMaxDurability()});
        res.getPersistentDataContainer().set(CustomNamespaceKey.CUSTOM_TYPE.getNamespaceKey(), PersistentDataType.STRING, getCustomKey());
        /**
         * IMPORTANT : The lore must be set in the createCustomItem method
         */

        return res;
    }

    protected ArrayList<String> getBaseLores(HashMap<CustomNamespaceKey, List<String>> data) {
        ArrayList<String> res = new ArrayList<>(getLore());
        lore.add("");

        if (isUnbreakable()) lore.add("§r§7Unbreakable");

        for (CustomNamespaceKey customNamespaceKey : CustomNamespaceKey.values()) {
            List<String> o = data.get(customNamespaceKey);
            if (o != null) {
                String row = customNamespaceKey.getLoreRow(o);
                res.add(row);
            }
        }

        return res;
    }

    public abstract void updateLores(ItemMeta meta, HashMap<CustomNamespaceKey, List<String>> data);
}
