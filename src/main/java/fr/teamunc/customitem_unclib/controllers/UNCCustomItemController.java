package fr.teamunc.customitem_unclib.controllers;

import fr.teamunc.customitem_unclib.CustomItemLib;
import fr.teamunc.customitem_unclib.models.CustomNamespaceKey;
import fr.teamunc.customitem_unclib.models.UNCCustomType;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UNCCustomItemController {

    private HashMap<String, UNCCustomType> customItemsMap;
    private int customItemActionTickId;
    public UNCCustomItemController() {
        customItemsMap = new HashMap<>();
        startActionOnCustomItems();
    }

    private void startActionOnCustomItems() {
        this.customItemActionTickId = Bukkit.getScheduler().runTaskTimer(CustomItemLib.getPlugin(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item != null && CustomNamespaceKey.CUSTOM_TYPE.hasCustomData(item)) {
                        String customType = CustomNamespaceKey.CUSTOM_TYPE.getCustomData(item);
                        if (customItemsMap.containsKey(customType)) {
                            UNCCustomType customItem = customItemsMap.get(customType);
                            if (customItem.getActionToRun() != null) {
                                customItem.getActionToRun().execute(item, player);
                            }
                        }
                    }
                }
            }
        }, 0, 20).getTaskId();
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

    public List<String> getRegisteredCustomItemTypes() {
        return new ArrayList<>(customItemsMap.keySet());
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

    public void setAdditionalInformations(ItemStack item, String key, @NonNull Object objectData) {
        ItemMeta meta = item.getItemMeta();
        NamespacedKey namespaceKey = new NamespacedKey(CustomItemLib.getPlugin(), key);
        String value = objectData.toString();

        if (meta == null || !meta.getPersistentDataContainer().has(namespaceKey, PersistentDataType.STRING))
            throw new IllegalStateException("Item "+ item.getType() +" doesn't have the key " + key);

        meta.getPersistentDataContainer().set(namespaceKey, PersistentDataType.STRING, value);
        item.setItemMeta(meta);

    }

    public String getAdditionalInformations(ItemStack item, String key) {
        ItemMeta meta = item.getItemMeta();
        NamespacedKey namespaceKey = new NamespacedKey(CustomItemLib.getPlugin(), key);

        if (meta == null || !meta.getPersistentDataContainer().has(namespaceKey, PersistentDataType.STRING))
            throw new IllegalStateException("Item "+ item.getType() +" doesn't have the key " + key);

        return meta.getPersistentDataContainer().get(namespaceKey, PersistentDataType.STRING);
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

        } else if (player != null){
            player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 1.0f, 1.0f);
            player.getInventory().remove(item);
        }
    }

    public void updateLores(ItemStack result, HashMap<CustomNamespaceKey, List<String>> newDataLore) {
        ItemMeta meta = result.getItemMeta();
        String customType = CustomNamespaceKey.CUSTOM_TYPE.getCustomData(result);

        this.customItemsMap.get(customType).updateLores(meta, newDataLore);

        result.setItemMeta(meta);
    }

    public void stopCustomItemActionTimer() {
        Bukkit.getScheduler().cancelTask(customItemActionTickId);
    }
}
