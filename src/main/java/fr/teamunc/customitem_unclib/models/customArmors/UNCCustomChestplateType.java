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
public class UNCCustomChestplateType extends UNCCustomArmorType {

    @Builder
    public UNCCustomChestplateType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, String customKey, Material bukkitMaterial, UNCAction action) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, bukkitMaterial, action);
    }

    public static UNCCustomChestplateTypeBuilder builder(String customKey) {
        return new UNCCustomChestplateTypeBuilder().customKey(customKey);
    }
}
