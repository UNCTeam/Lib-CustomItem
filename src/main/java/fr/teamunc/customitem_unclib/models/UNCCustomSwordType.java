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
public class UNCCustomSwordType extends UNCCustomType {
    private Double attackDamage;

    @Builder
    public UNCCustomSwordType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, String customKey, double attackDamage, int attackSpeed, UNCAction action, UNCActionOnTick actionToRun, Map<String, Object> defaultAdditionalInformation) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, Material.NETHERITE_SWORD, action, actionToRun, defaultAdditionalInformation);
        this.attackDamage = attackDamage;
    }

    public static UNCCustomSwordTypeBuilder builder(String customKey) {
        return new UNCCustomSwordTypeBuilder().customKey(customKey);
    }

    @Override
    public ItemStack createCustomItem(int amount) {
        ItemStack res = new ItemStack(getBukkitMaterial(), amount);

        ItemMeta meta = createCustomItemMeta(true);

        CustomNamespaceKey.CUSTOM_ATTACK_DAMAGE.setCustomData(meta, getAttackDamage());
        CustomNamespaceKey.CUSTOM_DISPLAYED_ATTACK_DAMAGE.setCustomData(meta, getAttackDamage());

        List<String> attackdamage = new ArrayList<>();
        attackdamage.add("" + getAttackDamage());

        HashMap<CustomNamespaceKey, List<String>> data = new HashMap<>();
        data.put(CustomNamespaceKey.CUSTOM_DISPLAYED_ATTACK_DAMAGE, attackdamage);

        updateLores(meta, data);

        res.setItemMeta(meta);

        return res;
    }
}
