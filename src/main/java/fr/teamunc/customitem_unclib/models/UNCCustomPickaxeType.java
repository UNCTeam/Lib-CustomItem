package fr.teamunc.customitem_unclib.models;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class UNCCustomPickaxeType extends UNCCustomType {

    @Builder
    public UNCCustomPickaxeType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, String customKey, Material bukkitMaterial) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, bukkitMaterial);
    }

    public static UNCCustomPickaxeType.UNCCustomPickaxeTypeBuilder builder(String customKey, Material bukkitMaterial) {
        return new UNCCustomPickaxeType.UNCCustomPickaxeTypeBuilder().customKey(customKey).bukkitMaterial(bukkitMaterial);
    }

    @Override
    public ItemStack createCustomItem(int amount) {
        ItemStack res = new ItemStack(getBukkitMaterial(), amount);

        ItemMeta meta = createCustomItemMeta();

        HashMap<CustomNamespaceKey, List<String>> data = new HashMap<>();

        updateLores(meta, data);

        res.setItemMeta(meta);

        return res;
    }

    @Override
    public void updateLores(ItemMeta meta, HashMap<CustomNamespaceKey, List<String>> data) {
        // refill with default lores
        CustomNamespaceKey.refillEmptyDataLore(data, meta);

        ArrayList<String> newLore = getBaseLores(data);
        meta.setLore(newLore);
    }
}
