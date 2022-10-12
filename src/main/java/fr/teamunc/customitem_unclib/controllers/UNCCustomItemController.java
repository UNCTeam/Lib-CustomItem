package fr.teamunc.customitem_unclib.controllers;

import fr.teamunc.base_unclib.utils.helpers.Message;
import fr.teamunc.customitem_unclib.CustomItemLib;
import fr.teamunc.customitem_unclib.models.CustomNamespaceKey;
import fr.teamunc.customitem_unclib.models.UNCCustomType;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.List;

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

    public UNCCustomType getCustomItemType(String customKey) {
        return customItemsMap.get(customKey);
    }

    public void changeDurability(ItemStack item, Player player, int amount) {
        ItemMeta meta = item.getItemMeta();
        int[] durabilities = CustomNamespaceKey.CUSTOM_DURABILITY.getCustomData(item);
        Message.Get().broadcastMessageToConsole("Durability: " + durabilities[0] + " / " + durabilities[1]);
        if (durabilities[0] - amount >= 0) {
            // changing durability
            durabilities[0] -= amount;
            meta.getPersistentDataContainer().set(CustomNamespaceKey.CUSTOM_DURABILITY.getNamespaceKey(), PersistentDataType.INTEGER_ARRAY, durabilities);

            // actualizing durability lore line
            List<String> lore = meta.getLore();
            lore.set(lore.size()-1, "§r§fDurability: " + durabilities[0] + " / " + durabilities[1]);
            meta.setLore(lore);

            // actualizing item damage amount
            int displayDurability = ((durabilities[1]-durabilities[0])*item.getType().getMaxDurability())/durabilities[1];
            ((Damageable)meta).setDamage(displayDurability);

            item.setItemMeta(meta);
        } else {
            player.getInventory().remove(item);
        }
    }

    public void updateLores(ItemStack result, HashMap<CustomNamespaceKey, List<String>> newDataLore) {
        ItemMeta meta = result.getItemMeta();
        String customType = CustomNamespaceKey.CUSTOM_TYPE.getCustomData(result);

        this.customItemsMap.get(customType).updateLores(meta, newDataLore);
    }
}
