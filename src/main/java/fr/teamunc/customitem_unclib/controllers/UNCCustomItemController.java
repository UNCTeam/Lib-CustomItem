package fr.teamunc.customitem_unclib.controllers;

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
        Integer durability = CustomNamespaceKey.CUSTOM_DURABILITY.getCustomData(item);
        if (durability - amount >= 0) {
            // changing durability
            durability -= amount;
            meta.getPersistentDataContainer().set(CustomNamespaceKey.CUSTOM_DURABILITY.getNamespaceKey(), PersistentDataType.INTEGER, durability);

            // actualizing durability lore line
            String customType = CustomNamespaceKey.CUSTOM_TYPE.getCustomData(item);
            int maxDurability = CustomItemLib.getUNCCustomItemController().getCustomItemType(customType).getMaxDurability();
            List<String> lore = meta.getLore();
            lore.set(lore.size()-1, "§r§fDurability : §d§l" + durability + "§r§f/§d§l" + maxDurability);
            meta.setLore(lore);

            // actualizing item damage amount
            int displayDurability = ((maxDurability-durability)*item.getType().getMaxDurability())/maxDurability;
            ((Damageable)meta).setDamage(displayDurability);

            item.setItemMeta(meta);
        } else {
            player.getInventory().remove(item);
        }
    }
}
