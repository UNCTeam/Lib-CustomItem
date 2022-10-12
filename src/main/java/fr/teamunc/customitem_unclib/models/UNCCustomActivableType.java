package fr.teamunc.customitem_unclib.models;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class UNCCustomActivableType extends UNCCustomType {

    public UNCCustomActivableType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, String customKey, Material bukkitMaterial) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, bukkitMaterial);
    }

    @Override
    public ItemStack createCustomItem(int amount) {
        return null;
    }

    @Override
    public void updateLores(ItemMeta meta, HashMap<CustomNamespaceKey, List<String>> data) {

    }
}
