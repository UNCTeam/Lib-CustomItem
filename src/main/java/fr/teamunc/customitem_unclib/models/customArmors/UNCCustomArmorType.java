package fr.teamunc.customitem_unclib.models.customArmors;

import fr.teamunc.customitem_unclib.models.CustomNamespaceKey;
import fr.teamunc.customitem_unclib.models.UNCAction;
import fr.teamunc.customitem_unclib.models.UNCCustomType;
import lombok.Getter;
import lombok.val;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;

@Getter
public abstract class UNCCustomArmorType extends UNCCustomType {

    private int armor;
    private int armorToughness;
    private int armorKnockback;

    protected UNCCustomArmorType(String name, ArrayList<String> lore, int modelData, int maxDurability, boolean unbreakable, int armor, int armorToughness, int armorKnockback, String customKey, Material bukkitMaterial, UNCAction action) {
        super(name, lore, modelData, maxDurability, unbreakable, customKey, bukkitMaterial, action);
        this.armor = armor;
        this.armorToughness = armorToughness;
        this.armorKnockback = armorKnockback;
    }

    @Override
    public ItemStack createCustomItem(int amount) {
        ItemStack res = new ItemStack(getBukkitMaterial(), amount);

        ItemMeta meta = createCustomItemMeta(true);
        val metaLeather = (LeatherArmorMeta) meta;

        metaLeather.setColor(Color.fromRGB(255, 255, 256 - getModelData()));
        meta.addItemFlags(ItemFlag.HIDE_DYE);

        // Set lore
        CustomNamespaceKey.CUSTOM_DISPLAYED_ARMOR.setCustomData(meta, getArmor());
        CustomNamespaceKey.CUSTOM_DISPLAYED_ARMOR_TOUGHNESS.setCustomData(meta, getArmorToughness());
        CustomNamespaceKey.CUSTOM_DISPLAYED_ARMOR_KNOCKBACK.setCustomData(meta, getArmorKnockback());
        List<String> armorInfo = new ArrayList<>();
        armorInfo.add("" + getArmor());
        List<String> armorToughnessInfo = new ArrayList<>();
        armorToughnessInfo.add("" + getArmorToughness());
        List<String> armorKnockbackInfo = new ArrayList<>();
        armorKnockbackInfo.add("" + getArmorKnockback());

        EnumMap<CustomNamespaceKey, List<String>> data = new EnumMap<>(CustomNamespaceKey.class);
        data.put(CustomNamespaceKey.CUSTOM_DISPLAYED_ARMOR, armorInfo);
        data.put(CustomNamespaceKey.CUSTOM_DISPLAYED_ARMOR_TOUGHNESS, armorToughnessInfo);
        data.put(CustomNamespaceKey.CUSTOM_DISPLAYED_ARMOR_KNOCKBACK, armorKnockbackInfo);
        updateLores(meta, data);

        // set attributes of the armor
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(),"generic.armor",Double.parseDouble(getArmor()+""), AttributeModifier.Operation.ADD_NUMBER));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(),"generic.armor_toughness",Double.parseDouble(getArmorToughness()+""), AttributeModifier.Operation.ADD_NUMBER));
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(),"generic.knockback_resistance",Double.parseDouble(getArmorKnockback()+""), AttributeModifier.Operation.ADD_NUMBER));



        res.setItemMeta(meta);

        return res;
    }
}
