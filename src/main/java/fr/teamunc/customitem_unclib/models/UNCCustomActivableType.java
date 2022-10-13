package fr.teamunc.customitem_unclib.models;

import fr.teamunc.customitem_unclib.models.customArmors.UNCCustomLeggingsType;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class UNCCustomActivableType extends UNCCustomType {

    @Builder
    public UNCCustomActivableType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, String customKey, Material bukkitMaterial, UNCAction action) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, bukkitMaterial, action);
    }

    public static UNCCustomActivableTypeBuilder builder(String customKey) {
        return new UNCCustomActivableTypeBuilder().customKey(customKey);
    }

    @Override
    public ItemStack createCustomItem(int amount) {
        return null;
    }

    @Override
    public void updateLores(ItemMeta meta, HashMap<CustomNamespaceKey, List<String>> data) {

    }
}
