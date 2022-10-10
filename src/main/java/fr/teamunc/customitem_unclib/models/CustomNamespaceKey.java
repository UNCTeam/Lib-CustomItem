package fr.teamunc.customitem_unclib.models;

import fr.teamunc.customitem_unclib.CustomItemLib;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public enum CustomNamespaceKey {
    CUSTOM_TYPE("custom_type", PersistentDataType.STRING),
    CUSTOM_DURABILITY("custom_durability", PersistentDataType.INTEGER),
    CUSTOM_UNBREAKABLE("custom_unbreakable", PersistentDataType.BYTE);

    private final String key;
    private final PersistentDataType<?, ?> type;
    CustomNamespaceKey(String key, PersistentDataType persistentDataType) {
        this.key = key;
        type = persistentDataType;
    }

    public NamespacedKey getNamespaceKey() {
        if (CustomItemLib.isInit()) {
            return new NamespacedKey(CustomItemLib.getPlugin(), key);
        } else {
            throw new IllegalStateException("CustomItemLib is not initialized");
        }
    }

    public boolean hasCustomData(ItemStack item) {
        return item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getPersistentDataContainer().has(this.getNamespaceKey(), type);
    }
}
