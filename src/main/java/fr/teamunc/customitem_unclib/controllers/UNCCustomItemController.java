package fr.teamunc.customitem_unclib.controllers;

import fr.teamunc.customitem_unclib.CustomItemLib;
import fr.teamunc.customitem_unclib.models.UNCCustomType;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class UNCCustomItemController {

    private HashMap<String, UNCCustomType> customItemsMap;

    public UNCCustomItemController() {
        customItemsMap = new HashMap<>();
    }

    public void registerCustomItem(UNCCustomType customItem) {
        if (customItemsMap.containsKey(customItem.getCustomKey())) {
            throw new IllegalArgumentException("Custom item with key " + customItem.getCustomKey() + " already exists");
        } else {
            customItemsMap.put(customItem.getCustomKey(), customItem);
        }
    }

    public ItemStack createCustomItem(String customKey, int amount) {
        if (customItemsMap.containsKey(customKey)) {
            return customItemsMap.get(customKey).createCustomItem(amount);
        } else {
            throw new IllegalArgumentException("Custom item with key " + customKey + " doesn't exists");
        }
    }

    public NamespacedKey getCustomTypeNamespacedKey() {
        if (CustomItemLib.isInit()) {
            return new NamespacedKey(CustomItemLib.getPlugin(), "custom_type");
        } else {
            throw new IllegalStateException("CustomItemLib is not initialized");
        }
    }

    public NamespacedKey getCustomDurabilityNamespacedKey() {
        if (CustomItemLib.isInit()) {
            return new NamespacedKey(CustomItemLib.getPlugin(), "custom_durability");
        } else {
            throw new IllegalStateException("CustomItemLib is not initialized");
        }
    }

    public NamespacedKey getCustomUnbreakableNamespacedKey() {
        if (CustomItemLib.isInit()) {
            return new NamespacedKey(CustomItemLib.getPlugin(), "custom_unbreakable");
        } else {
            throw new IllegalStateException("CustomItemLib is not initialized");
        }
    }

    public void giveCustomItem(Player player, String customKey, int amount) {
        player.getInventory().addItem(createCustomItem(customKey, amount));
    }
}
