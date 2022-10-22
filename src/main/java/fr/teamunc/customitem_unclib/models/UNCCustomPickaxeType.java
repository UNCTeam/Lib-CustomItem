package fr.teamunc.customitem_unclib.models;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import java.util.ArrayList;

@Getter
public class UNCCustomPickaxeType extends UNCCustomType {

    @Builder
    public UNCCustomPickaxeType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, String customKey, Material bukkitMaterial, UNCAction action) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, bukkitMaterial, action);
    }

    public static UNCCustomPickaxeTypeBuilder builder(String customKey, Material bukkitMaterial) {
        return new UNCCustomPickaxeTypeBuilder().customKey(customKey).bukkitMaterial(bukkitMaterial);
    }
}
