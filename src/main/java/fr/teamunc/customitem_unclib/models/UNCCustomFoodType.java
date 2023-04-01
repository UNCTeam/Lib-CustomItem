package fr.teamunc.customitem_unclib.models;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class UNCCustomFoodType extends UNCCustomType {
    private final int foodLevel;
    private final float saturation;

    @Builder
    public UNCCustomFoodType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, int foodLevel, float saturation, String customKey, Material bukkitMaterial, UNCAction action, UNCActionOnTick actionToRun, Map<String, Object> defaultAdditionalInformation) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, bukkitMaterial, action, actionToRun, defaultAdditionalInformation);
        this.foodLevel = foodLevel;
        this.saturation = saturation;
    }

    public static UNCCustomFoodTypeBuilder builder(String customKey, Material bukkitMaterial) {
        return new UNCCustomFoodTypeBuilder().customKey(customKey).bukkitMaterial(bukkitMaterial);
    }

    @Override
    public ItemStack createCustomItem(int amount) {
        ItemStack res = new ItemStack(getBukkitMaterial(), amount);

        ItemMeta meta = createCustomItemMeta(false);

        CustomNamespaceKey.CUSTOM_FOOD.setCustomData(meta, getFoodLevel());
        CustomNamespaceKey.CUSTOM_SATURATION.setCustomData(meta, getSaturation());

        List<String> customFood = new ArrayList<>();
        customFood.add("" + getFoodLevel());
        List<String> customSaturation = new ArrayList<>();
        customSaturation.add("" + getSaturation());

        HashMap<CustomNamespaceKey, List<String>> data = new HashMap<>();
        data.put(CustomNamespaceKey.CUSTOM_FOOD, customFood);
        data.put(CustomNamespaceKey.CUSTOM_SATURATION, customSaturation);

        updateLores(meta, data);

        res.setItemMeta(meta);

        return res;
    }
}
