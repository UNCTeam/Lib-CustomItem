package fr.teamunc.customitem_unclib.controllers;

import fr.teamunc.customitem_unclib.models.CustomNamespaceKey;
import fr.teamunc.customitem_unclib.models.UNCCustomType;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class UNCCustomItemController {

    private HashMap<String, UNCCustomType> customItemsMap;

    public UNCCustomItemController() {
        customItemsMap = new HashMap<>();
    }

    public void registerCustomItem(UNCCustomType... customItems) {
        for (UNCCustomType customItem : customItems) {
            if (customItemsMap.containsKey(customItem.getCustomKey())) {
                throw new IllegalArgumentException("Custom item with key " + customItem.getCustomKey() + " already exists");
            } else {
                customItemsMap.put(customItem.getCustomKey(), customItem);
            }
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

    public UNCCustomType getCustomItemType(String customKey) {
        return customItemsMap.get(customKey);
    }

    public void changeDurability(ItemStack item, Player player, int amount) {
        ItemMeta meta = item.getItemMeta();
        int[] durabilities = CustomNamespaceKey.CUSTOM_DURABILITY.getCustomData(item);

        if (durabilities[0] - amount >= 0) {
            // changing durability
            durabilities[0] -= amount;
            CustomNamespaceKey.CUSTOM_DURABILITY.setCustomData(meta, durabilities);

            // actualizing item damage amount
            int displayDurability = ((durabilities[1]-durabilities[0])*item.getType().getMaxDurability())/durabilities[1];
            ((Damageable)meta).setDamage(displayDurability);

            item.setItemMeta(meta);

            this.updateLores(item,new HashMap<>());

        } else {
            player.getInventory().remove(item);
        }
    }

    public void updateLores(ItemStack result, HashMap<CustomNamespaceKey, List<String>> newDataLore) {
        ItemMeta meta = result.getItemMeta();
        String customType = CustomNamespaceKey.CUSTOM_TYPE.getCustomData(result);

        this.customItemsMap.get(customType).updateLores(meta, newDataLore);

        result.setItemMeta(meta);
    }
}
