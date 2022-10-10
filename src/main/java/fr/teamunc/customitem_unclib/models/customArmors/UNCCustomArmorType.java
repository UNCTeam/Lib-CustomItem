package fr.teamunc.customitem_unclib.models.customArmors;

import fr.teamunc.customitem_unclib.models.UNCCustomType;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

@Getter
public abstract class UNCCustomArmorType extends UNCCustomType {

    public UNCCustomArmorType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, String customKey, Material bukkitMaterial) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, bukkitMaterial);
    }
}