package fr.teamunc.customitem_unclib.models.customArmors;

import fr.teamunc.customitem_unclib.models.CustomNamespaceKey;
import fr.teamunc.customitem_unclib.models.UNCAction;
import fr.teamunc.customitem_unclib.models.UNCCustomType;
import lombok.Getter;
import lombok.val;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public abstract class UNCCustomArmorType extends UNCCustomType {

    public UNCCustomArmorType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, String customKey, Material bukkitMaterial, UNCAction action) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, bukkitMaterial, action);
    }

    @Override
    public ItemStack createCustomItem(int amount) {
        ItemStack res = new ItemStack(getBukkitMaterial(), amount);

        ItemMeta meta = createCustomItemMeta();
        val metaLeather = (LeatherArmorMeta) meta;

        metaLeather.setColor(Color.fromRGB(255, 255, 256 - getModelData()));
        meta.addItemFlags(ItemFlag.HIDE_DYE);

        updateLores(meta, new HashMap<>());

        res.setItemMeta(meta);

        return res;
    }
}
