package fr.teamunc.customitem_unclib.controllers;

import fr.teamunc.customitem_unclib.models.UNCCustomType;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

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

    public void registerCraft(Recipe recipe, NamespacedKey namespacedKey, boolean replace) {
        if (replace) Bukkit.getServer().removeRecipe(namespacedKey);
        Bukkit.getServer().addRecipe(recipe);
    }

    public ItemStack createCustomItem(String customKey, int amount) {
        if (customItemsMap.containsKey(customKey)) {
            return customItemsMap.get(customKey).createCustomItem(amount);
        } else {
            throw new IllegalArgumentException("Custom item with key " + customKey + " doesn't exists");
        }
    }

    public void giveCustomItem(Player player, String customKey, int amount) {
        player.getInventory().addItem(createCustomItem(customKey, amount));
    }
}
