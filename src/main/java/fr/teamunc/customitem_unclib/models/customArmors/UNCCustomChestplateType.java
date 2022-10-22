package fr.teamunc.customitem_unclib.models.customArmors;

import fr.teamunc.customitem_unclib.models.UNCAction;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import java.util.ArrayList;

@Getter
public class UNCCustomChestplateType extends UNCCustomArmorType {

    @Builder
    public UNCCustomChestplateType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, int armor, int armorToughness, int knockbackResistance, String customKey, UNCAction action) {
        super(name, lore, modelData, maxDurability, unbreakable, armor, armorToughness, knockbackResistance, customKey, Material.LEATHER_CHESTPLATE, action);
    }

    public static UNCCustomChestplateTypeBuilder builder(String customKey) {
        return new UNCCustomChestplateTypeBuilder().customKey(customKey);
    }
}
