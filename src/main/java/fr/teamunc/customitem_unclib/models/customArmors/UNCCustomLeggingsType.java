package fr.teamunc.customitem_unclib.models.customArmors;

import fr.teamunc.customitem_unclib.models.UNCAction;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;

@Getter
public class UNCCustomLeggingsType extends UNCCustomArmorType {

    @Builder
    public UNCCustomLeggingsType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, String customKey, Material bukkitMaterial, UNCAction action) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, bukkitMaterial, action);
    }

    public static UNCCustomLeggingsTypeBuilder builder(String customKey) {
        return new UNCCustomLeggingsTypeBuilder().customKey(customKey);
    }

    @Override
    public ItemStack createCustomItem(int amount) {
        return null;
    }
}
