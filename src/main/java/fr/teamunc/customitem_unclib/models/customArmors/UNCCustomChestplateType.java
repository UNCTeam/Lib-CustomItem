package fr.teamunc.customitem_unclib.models.customArmors;

import fr.teamunc.customitem_unclib.models.UNCAction;
import fr.teamunc.customitem_unclib.models.UNCActionOnTick;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.Map;

@Getter
public class UNCCustomChestplateType extends UNCCustomArmorType {

    @Builder
    public UNCCustomChestplateType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, int armor, int armorToughness, int knockbackResistance, String customKey, UNCAction action, UNCActionOnTick actionToRun, Map<String, Object> defaultAdditionalInformation) {
        super(name, lore, EquipmentSlot.CHEST, modelData, maxDurability, unbreakable, armor, armorToughness, knockbackResistance, customKey, Material.LEATHER_CHESTPLATE, action, actionToRun, defaultAdditionalInformation);
    }

    public static UNCCustomChestplateTypeBuilder builder(String customKey) {
        return new UNCCustomChestplateTypeBuilder().customKey(customKey);
    }
}
