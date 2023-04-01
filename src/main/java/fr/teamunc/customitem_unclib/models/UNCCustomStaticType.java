package fr.teamunc.customitem_unclib.models;

import lombok.Builder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UNCCustomStaticType extends UNCCustomType {

    @Builder
    public UNCCustomStaticType(String name, ArrayList<String> lore, int modelData, String customKey, UNCActionOnTick actionToRun, Map<String, Object> defaultAdditionalInformation) {
        super(name, lore, modelData, 0, false, customKey, Material.STRUCTURE_BLOCK, null, actionToRun, defaultAdditionalInformation);
    }

    public static UNCCustomStaticType.UNCCustomStaticTypeBuilder builder(String customKey) {
        return new UNCCustomStaticType.UNCCustomStaticTypeBuilder().customKey(customKey);
    }

    @Override
    public ItemStack createCustomItem(int amount) {
        ItemStack res = new ItemStack(getBukkitMaterial(), amount);
        ItemMeta meta = createCustomItemMeta(false);

        res.setItemMeta(meta);

        return res;
    }
}
