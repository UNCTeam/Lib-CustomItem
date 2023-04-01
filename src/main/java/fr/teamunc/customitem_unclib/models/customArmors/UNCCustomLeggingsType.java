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
public class UNCCustomLeggingsType extends UNCCustomArmorType {

    @Builder
    public UNCCustomLeggingsType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, int armor, int armorToughness, int knockbackResistance, String customKey, UNCAction action, UNCActionOnTick actionToRun, Map<String, Object> defaultAdditionalInformation) {
        super(name, lore, EquipmentSlot.LEGS, modelData, maxDurability, unbreakable, armor, armorToughness, knockbackResistance, customKey, Material.LEATHER_LEGGINGS, action, actionToRun, defaultAdditionalInformation);
    }

    public static UNCCustomLeggingsTypeBuilder builder(String customKey) {
        return new UNCCustomLeggingsTypeBuilder().customKey(customKey);
    }
}
