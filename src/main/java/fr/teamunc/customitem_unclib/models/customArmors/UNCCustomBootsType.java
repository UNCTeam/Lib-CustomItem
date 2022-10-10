package fr.teamunc.customitem_unclib.models.customArmors;


import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

@Getter
public class UNCCustomBootsType extends UNCCustomArmorType {

    public UNCCustomBootsType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, String customKey, Material bukkitMaterial) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, bukkitMaterial);
    }

    @Override
    public ItemStack createCustomItem(int amount) {
        return null;
    }
}