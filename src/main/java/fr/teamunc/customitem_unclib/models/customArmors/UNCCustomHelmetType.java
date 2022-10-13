package fr.teamunc.customitem_unclib.models.customArmors;

import fr.teamunc.customitem_unclib.models.CustomNamespaceKey;
import fr.teamunc.customitem_unclib.models.UNCAction;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class UNCCustomHelmetType extends UNCCustomArmorType {

    @Builder
    public UNCCustomHelmetType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, String customKey, Material bukkitMaterial, UNCAction action) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, bukkitMaterial, action);
    }

    public static UNCCustomHelmetTypeBuilder builder(String customKey) {
        return new UNCCustomHelmetTypeBuilder().customKey(customKey);
    }

    @Override
    public ItemStack createCustomItem(int amount) {
        return null;
    }

}
