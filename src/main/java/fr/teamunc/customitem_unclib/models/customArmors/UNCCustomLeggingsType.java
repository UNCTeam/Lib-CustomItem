package fr.teamunc.customitem_unclib.models.customArmors;

import fr.teamunc.customitem_unclib.models.UNCAction;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import java.util.ArrayList;

@Getter
public class UNCCustomLeggingsType extends UNCCustomArmorType {

    @Builder
    public UNCCustomLeggingsType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, String customKey, UNCAction action) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, Material.LEATHER_LEGGINGS, action);
    }

    public static UNCCustomLeggingsTypeBuilder builder(String customKey) {
        return new UNCCustomLeggingsTypeBuilder().customKey(customKey);
    }
}
