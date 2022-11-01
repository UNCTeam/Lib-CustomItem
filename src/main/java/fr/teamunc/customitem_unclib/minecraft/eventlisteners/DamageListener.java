package fr.teamunc.customitem_unclib.minecraft.eventlisteners;

import fr.teamunc.customitem_unclib.CustomItemLib;
import fr.teamunc.customitem_unclib.models.CustomNamespaceKey;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class DamageListener implements Listener {

    @EventHandler
    public void customDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity) {
            LivingEntity entityCause = (LivingEntity) event.getDamager();
            ItemStack originItem = entityCause.getEquipment().getItemInMainHand();

            if (originItem.getItemMeta() == null || !CustomNamespaceKey.CUSTOM_TYPE.hasCustomData(originItem) || !CustomNamespaceKey.CUSTOM_ATTACK_DAMAGE.hasCustomData(originItem)) return;

            double baseAttack = event.getFinalDamage();
            double addedCustomValue = Double.parseDouble(CustomNamespaceKey.CUSTOM_ATTACK_DAMAGE.getCustomData(originItem).toString()) - 8;
            event.setDamage(baseAttack + addedCustomValue);
        }
    }

    @EventHandler
    public void onEnchantCustomItem(EnchantItemEvent event) {
        ItemStack item = event.getItem();

        // check enchant for custom attacking items
        if (item.getItemMeta() != null && CustomNamespaceKey.CUSTOM_TYPE.hasCustomData(item) && CustomNamespaceKey.CUSTOM_DISPLAYED_ATTACK_DAMAGE.hasCustomData(item)) {
            for (Map.Entry<Enchantment, Integer> entry : event.getEnchantsToAdd().entrySet()) {
                Enchantment enchantment = entry.getKey();
                if (enchantment.getKey().equals(NamespacedKey.minecraft("sharpness"))) {
                    Integer integer = entry.getValue();
                    double extraDamage = 0.5 * Math.max(0, integer - 1) + 1.0;
                    double baseDamage = CustomNamespaceKey.CUSTOM_DISPLAYED_ATTACK_DAMAGE.getCustomData(item);

                    item.setItemMeta(CustomNamespaceKey.CUSTOM_DISPLAYED_ATTACK_DAMAGE.setCustomData(item.getItemMeta(), (baseDamage + extraDamage)));

                    List<String> newAttackDamage = new ArrayList<>();
                    newAttackDamage.add("" + (baseDamage + extraDamage));
                    EnumMap<CustomNamespaceKey, List<String>> data = new EnumMap<>(CustomNamespaceKey.class);
                    data.put(CustomNamespaceKey.CUSTOM_DISPLAYED_ATTACK_DAMAGE, newAttackDamage);

                    CustomItemLib.getUNCCustomItemController().updateLores(item, data);
                }
            }
        }
    }

    @EventHandler
    public void onAnvilCustomItem(PrepareAnvilEvent event) {
        ItemStack item = event.getInventory().getItem(0);
        if (item == null || item.getItemMeta() == null || !CustomNamespaceKey.CUSTOM_TYPE.hasCustomData(item) || !CustomNamespaceKey.CUSTOM_ATTACK_DAMAGE.hasCustomData(item)) {
            item = event.getInventory().getItem(1);
            if (item == null || item.getItemMeta() == null || !CustomNamespaceKey.CUSTOM_TYPE.hasCustomData(item) || !CustomNamespaceKey.CUSTOM_ATTACK_DAMAGE.hasCustomData(item)) return;
        }
        ItemStack result = event.getResult();
        if (result != null) {

            int levelResult = result.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            int levelDifference = levelResult - item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);

            if (levelDifference == 0) return;

            double newDamage = ((double) CustomNamespaceKey.CUSTOM_DISPLAYED_ATTACK_DAMAGE.getCustomData(item)) + 0.5 * Math.max(0, levelResult - 1) + 1.0;

            result.setItemMeta(CustomNamespaceKey.CUSTOM_DISPLAYED_ATTACK_DAMAGE.setCustomData(result.getItemMeta(), newDamage));

            CustomItemLib.getUNCCustomItemController().updateLores(result, new EnumMap<>(CustomNamespaceKey.class));
        }
    }

    @EventHandler
    public void onGrindstoneItem(InventoryClickEvent event) {
        if ((event.getInventory() instanceof GrindstoneInventory)){
            GrindstoneInventory inventory = (GrindstoneInventory) event.getInventory();
            ItemStack item = inventory.getItem(2);
            if (item == null || item.getItemMeta() == null || !CustomNamespaceKey.CUSTOM_TYPE.hasCustomData(item) || !CustomNamespaceKey.CUSTOM_DISPLAYED_ATTACK_DAMAGE.hasCustomData(item)) return;

            item.setItemMeta(CustomNamespaceKey.CUSTOM_DISPLAYED_ATTACK_DAMAGE.setCustomData(item.getItemMeta(), CustomNamespaceKey.CUSTOM_ATTACK_DAMAGE.getCustomData(item)));
            CustomItemLib.getUNCCustomItemController().updateLores(item, new EnumMap<>(CustomNamespaceKey.class));
        }
    }
}
