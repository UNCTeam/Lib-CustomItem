package fr.teamunc.customitem_unclib.models;

import fr.teamunc.customitem_unclib.CustomItemLib;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final UNCActionOnTick actionToRun;
    private final Map<String, Object> defaultAdditionalInformation;

    public ItemStack createCustomItem(int amount) {
        ItemStack res = new ItemStack(getBukkitMaterial(), amount);
        ItemMeta meta = createCustomItemMeta(true);

        HashMap<CustomNamespaceKey, List<String>> data = new HashMap<>();
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
        /*
         * IMPORTANT : The lore must be set in the createCustomItem method
         */

        // add default additional informations
        if (defaultAdditionalInformation != null) for (Map.Entry<String, Object> keyVal : defaultAdditionalInformation.entrySet()) {
            if (keyVal.getValue() == null) continue;
            NamespacedKey namespacedKey = new NamespacedKey(CustomItemLib.getPlugin(), keyVal.getKey());
            Object value = keyVal.getValue();

            res.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, String.valueOf(value));
        }
        return res;
    }

    protected ArrayList<String> getBaseLores(HashMap<CustomNamespaceKey, List<String>> data) {
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

    public void updateLores(ItemMeta meta, HashMap<CustomNamespaceKey, List<String>> data) {
        // refill with default lores
        CustomNamespaceKey.refillEmptyDataLore(data, meta);

        ArrayList<String> newLore = getBaseLores(data);
        meta.setLore(newLore);
    }
}
