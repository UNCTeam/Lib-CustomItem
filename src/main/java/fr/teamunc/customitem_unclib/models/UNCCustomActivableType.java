package fr.teamunc.customitem_unclib.models;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Map;

@Getter
public class UNCCustomActivableType extends UNCCustomType {

    @Builder
    public UNCCustomActivableType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, String customKey, Material bukkitMaterial, UNCAction action, UNCActionOnTick actionToRun, Map<String, Object> defaultAdditionalInformation) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, bukkitMaterial == null ? Material.CARROT_ON_A_STICK : bukkitMaterial, action, actionToRun, defaultAdditionalInformation);
    }

    public static UNCCustomActivableTypeBuilder builder(String customKey) {
        return new UNCCustomActivableTypeBuilder().customKey(customKey);
    }
}
