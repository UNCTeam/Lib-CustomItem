package fr.teamunc.customitem_unclib.models.customArmors;

import fr.teamunc.customitem_unclib.models.UNCAction;
import fr.teamunc.customitem_unclib.models.UNCActionOnTick;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;

@Getter
public class UNCCustomBootsType extends UNCCustomArmorType {

    @Builder
    public UNCCustomBootsType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, int armor, int armorToughness, int knockbackResistance, String customKey, UNCAction action, UNCActionOnTick actionToRun) {
        super(name, lore, EquipmentSlot.FEET, modelData, maxDurability, unbreakable, armor, armorToughness, knockbackResistance, customKey, Material.LEATHER_BOOTS, action, actionToRun);
    }

    public static UNCCustomBootsTypeBuilder builder(String customKey) {
        return new UNCCustomBootsTypeBuilder().customKey(customKey);
    }
}
