package fr.teamunc.customitem_unclib.models;

import lombok.Builder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class UNCCustomStaticType extends UNCCustomType {

    @Builder
    public UNCCustomStaticType(String name, ArrayList<String> lore, int modelData, String customKey) {
        super(name, lore, modelData, 0, true, customKey, Material.STRUCTURE_BLOCK, null);
    }

    public static UNCCustomStaticType.UNCCustomStaticTypeBuilder builder(String customKey) {
        return new UNCCustomStaticType.UNCCustomStaticTypeBuilder().customKey(customKey);
    }
}
