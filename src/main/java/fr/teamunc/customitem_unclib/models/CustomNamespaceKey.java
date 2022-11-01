package fr.teamunc.customitem_unclib.models;

import fr.teamunc.customitem_unclib.CustomItemLib;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class CustomNamespaceKey<T> {
    public static final CustomNamespaceKey<String> CUSTOM_TYPE = new CustomNamespaceKey<>("custom_type", PersistentDataType.STRING, "", false);
    public static final CustomNamespaceKey<int[]> CUSTOM_DURABILITY = new CustomNamespaceKey<>("custom_durability", PersistentDataType.INTEGER_ARRAY, "§r§fDurability: %s / %s", true);
    public static final CustomNamespaceKey<Byte> CUSTOM_UNBREAKABLE = new CustomNamespaceKey<>("custom_unbreakable", PersistentDataType.BYTE, "§r§7Unbreakable", false);
    public static final CustomNamespaceKey<Double> CUSTOM_ATTACK_DAMAGE = new CustomNamespaceKey<>("custom_attackdamage", PersistentDataType.DOUBLE, "", false);
    public static final CustomNamespaceKey<Double> CUSTOM_DISPLAYED_ATTACK_DAMAGE = new CustomNamespaceKey<>("custom_displayed_attackdamage", PersistentDataType.DOUBLE, "§r§2 %s Attack Damage", true);
    public static final CustomNamespaceKey<Integer> CUSTOM_DISPLAYED_ARMOR = new CustomNamespaceKey<>("custom_displayed_armor", PersistentDataType.INTEGER, "§r§9+%s Armor", true);
    public static final CustomNamespaceKey<Integer> CUSTOM_DISPLAYED_ARMOR_TOUGHNESS = new CustomNamespaceKey<>("custom_displayed_armor_toughness", PersistentDataType.INTEGER, "§r§9+%s Armor Toughness", true);
    public static final CustomNamespaceKey<Integer> CUSTOM_DISPLAYED_ARMOR_KNOCKBACK = new CustomNamespaceKey<>("custom_displayed_armor_knockback", PersistentDataType.INTEGER, "§r§9+%s Knockback Resistance", true);
    public static final CustomNamespaceKey<Integer> CUSTOM_FOOD = new CustomNamespaceKey<>("custom_food", PersistentDataType.INTEGER, "§r§2 %s Food", true);
    public static final CustomNamespaceKey<Float> CUSTOM_SATURATION = new CustomNamespaceKey<>("custom_saturation", PersistentDataType.FLOAT, "§r§2 %s Saturation", true);


    private final String key;
    @Getter
    private final PersistentDataType<?, T> type;
    @Getter
    private final String loreBaseRow;
    private final boolean writeInLore;

    CustomNamespaceKey(String key, PersistentDataType<?, T> type, String loreBaseRow, boolean writeInLore) {
        this.key = "uncitemdata_" + key;
        this.type = type;
        this.loreBaseRow = loreBaseRow;
        this.writeInLore = writeInLore;
    }

    public static CustomNamespaceKey[] values() {
        return new CustomNamespaceKey[]{
                CUSTOM_TYPE,
                CUSTOM_DURABILITY,
                CUSTOM_UNBREAKABLE,
                CUSTOM_ATTACK_DAMAGE,
                CUSTOM_DISPLAYED_ATTACK_DAMAGE,
                CUSTOM_DISPLAYED_ARMOR,
                CUSTOM_DISPLAYED_ARMOR_TOUGHNESS,
                CUSTOM_DISPLAYED_ARMOR_KNOCKBACK,
                CUSTOM_FOOD,
                CUSTOM_SATURATION
        };
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
    public T getCustomData(ItemStack item) {
        if (hasCustomData(item)) {
            return Objects.requireNonNull(item.getItemMeta().getPersistentDataContainer().get(this.getNamespaceKey(), type));
        } else {
            throw new IllegalStateException("Item has no custom data");
        }
    }

    @NonNull
    public T getCustomData(ItemMeta meta) {
        if (hasCustomData(meta)) {
            return meta.getPersistentDataContainer().get(this.getNamespaceKey(), type);
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
                    int[] durability = (int[]) key.getCustomData(meta);
                    values.add(String.valueOf(durability[0]));
                    values.add(String.valueOf(durability[1]));
                }
                else values.add(key.getCustomData(meta).toString());

                data.put(key, values);
            }
        }
        return data;
    }

    public ItemMeta setCustomData(ItemMeta meta, T objectData) {
        if (meta != null) {
            meta.getPersistentDataContainer().set(this.getNamespaceKey(), type, objectData);
        }

        return meta;
    }
}
