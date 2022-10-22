package fr.teamunc.customitem_unclib.models;

import fr.teamunc.customitem_unclib.CustomItemLib;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public enum CustomNamespaceKey {
    CUSTOM_TYPE("custom_type", PersistentDataType.STRING, "", false),
    CUSTOM_DURABILITY("custom_durability", PersistentDataType.INTEGER_ARRAY, "§r§fDurability: %s / %s", true),
    CUSTOM_UNBREAKABLE("custom_unbreakable", PersistentDataType.BYTE, "§r§7Unbreakable", false),
    CUSTOM_ATTACK_DAMAGE("custom_attackdamage", PersistentDataType.DOUBLE, "", false),
    CUSTOM_DISPLAYED_ATTACK_DAMAGE("custom_displayed_attackdamage", PersistentDataType.DOUBLE, "§r§2 %s Attack Damage", true),
    CUSTOM_DISPLAYED_ARMOR("custom_displayed_armor", PersistentDataType.INTEGER, "§r§9+%s Armor", true),
    CUSTOM_DISPLAYED_ARMOR_TOUGHNESS("custom_displayed_armor_toughness", PersistentDataType.INTEGER, "§r§9+%s Armor Toughness", true),
    CUSTOM_DISPLAYED_ARMOR_KNOCKBACK("custom_displayed_armor_knockback", PersistentDataType.INTEGER, "§r§9+%s Knockback Resistance", true);


    private final String key;
    @Getter
    private final PersistentDataType type;
    @Getter
    private final String loreBaseRow;
    private final boolean writeInLore;

    CustomNamespaceKey(String key, PersistentDataType type, String loreBaseRow, boolean writeInLore) {
        this.key = key;
        this.type = type;
        this.loreBaseRow = loreBaseRow;
        this.writeInLore = writeInLore;
    }

    public NamespacedKey getNamespaceKey() {
        if (CustomItemLib.isInit()) {
            return new NamespacedKey(CustomItemLib.getPlugin(), key);
        } else {
            throw new IllegalStateException("CustomItemLib is not initialized");
        }
    }

    public boolean hasCustomData(ItemStack item) {
        return item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(this.getNamespaceKey(), type);
    }

    public boolean hasCustomData(ItemMeta meta) {
        return meta != null && meta.getPersistentDataContainer().has(this.getNamespaceKey(), type);
    }

    @NonNull
    public <T> T getCustomData(ItemStack item) {
        if (hasCustomData(item)) {
            return (T) Objects.requireNonNull(item.getItemMeta().getPersistentDataContainer().get(this.getNamespaceKey(), type));
        } else {
            throw new IllegalStateException("Item has no custom data");
        }
    }

    @NonNull
    public <T> T getCustomData(ItemMeta meta) {
        if (hasCustomData(meta)) {
            return (T) meta.getPersistentDataContainer().get(this.getNamespaceKey(), type);
        } else {
            throw new IllegalStateException("Meta has no custom data");
        }
    }

    public String getLoreRow(List<String> value) {
        return String.format(loreBaseRow, value.toArray());
    }

    public static HashMap<CustomNamespaceKey, List<String>> refillEmptyDataLore(HashMap<CustomNamespaceKey, List<String>> data, ItemMeta meta) {
        for (CustomNamespaceKey key : CustomNamespaceKey.values()) {
            if (!data.containsKey(key) && key.hasCustomData(meta)) {

                if (!key.writeInLore) {
                    continue;
                }

                ArrayList<String> values = new ArrayList<>();
                if (key == CUSTOM_DURABILITY) {
                    int[] durability = key.getCustomData(meta);
                    values.add(String.valueOf(durability[0]));
                    values.add(String.valueOf(durability[1]));
                }
                else values.add(key.getCustomData(meta).toString());

                data.put(key, values);
            }
        }
        return data;
    }

    public ItemMeta setCustomData(ItemMeta meta, Object durabilities) {
        if (meta != null) {
            meta.getPersistentDataContainer().set(this.getNamespaceKey(), type, durabilities);
        }

        return meta;
    }
}
