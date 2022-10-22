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
public class UNCCustomBootsType extends UNCCustomArmorType {

    @Builder
    public UNCCustomBootsType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, String customKey, UNCAction action) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, Material.LEATHER_BOOTS, action);
    }

    public static UNCCustomBootsTypeBuilder builder(String customKey) {
        return new UNCCustomBootsTypeBuilder().customKey(customKey);
    }
}
