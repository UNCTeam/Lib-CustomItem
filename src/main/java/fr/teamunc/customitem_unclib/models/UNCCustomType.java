package fr.teamunc.customitem_unclib.models;

import fr.teamunc.customitem_unclib.CustomItemLib;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

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
        ArrayList<String> lore = new ArrayList<>(getLore());


        res.setDisplayName("§r" + getName());
        res.setCustomModelData(getModelData());

        // Durability
        NamespacedKey durabilityKey = CustomItemLib.getUNCCustomItemController().getCustomDurabilityNamespacedKey();
        res.getPersistentDataContainer().set(durabilityKey, PersistentDataType.INTEGER, getMaxDurability());
        lore.add("");
        lore.add("§r§fDurability : §d§l" + getMaxDurability() + "§r§f/§d§l" + getMaxDurability());

        // Unbreakable
        NamespacedKey unbreakableKey = CustomItemLib.getUNCCustomItemController().getCustomUnbreakableNamespacedKey();
        res.getPersistentDataContainer().set(unbreakableKey, PersistentDataType.BYTE, (byte) (isUnbreakable() ? 1 : 0));

        // CustomKey
        NamespacedKey CustomTypeKey = CustomItemLib.getUNCCustomItemController().getCustomTypeNamespacedKey();
        res.getPersistentDataContainer().set(CustomTypeKey, PersistentDataType.STRING, getCustomKey());

        // Final lore
        res.setLore(lore);

        return res;
    }
}
