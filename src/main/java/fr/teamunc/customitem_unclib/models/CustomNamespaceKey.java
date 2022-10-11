package fr.teamunc.customitem_unclib.models;

import fr.teamunc.customitem_unclib.CustomItemLib;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public enum CustomNamespaceKey {
    CUSTOM_TYPE("custom_type", PersistentDataType.STRING),
    CUSTOM_DURABILITY("custom_durability", PersistentDataType.INTEGER),
    CUSTOM_UNBREAKABLE("custom_unbreakable", PersistentDataType.BYTE);

    private final String key;
    @Getter
    private final PersistentDataType type;
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

    @NonNull
    public <T> T getCustomData(ItemStack item) {
        if (hasCustomData(item)) {
            return (T) item.getItemMeta().getPersistentDataContainer().get(this.getNamespaceKey(), type);
        } else {
            throw new IllegalStateException("Item has no custom data");
        }
    }
}
