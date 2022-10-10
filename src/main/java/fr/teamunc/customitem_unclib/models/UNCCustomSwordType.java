package fr.teamunc.customitem_unclib.models;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

@Getter
public class UNCCustomSwordType extends UNCCustomType {
    private int attackDamage;
    private int attackSpeed;

    @Builder
    public UNCCustomSwordType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, String customKey, int attackDamage, int attackSpeed) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, Material.NETHERITE_SWORD);
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    public static UNCCustomSwordTypeBuilder builder(String customKey) {
        return new UNCCustomSwordTypeBuilder().customKey(customKey);
    }

    @Override
    public ItemStack createCustomItem(int amount) {
        ItemStack res = new ItemStack(getBukkitMaterial(), amount);

        ItemMeta meta = createCustomItemMeta();
        res.setItemMeta(meta);

        return res;
    }
}
