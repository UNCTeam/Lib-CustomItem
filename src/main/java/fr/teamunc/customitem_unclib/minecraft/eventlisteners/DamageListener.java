package fr.teamunc.customitem_unclib.minecraft.eventlisteners;

import fr.teamunc.base_unclib.utils.helpers.Message;
import fr.teamunc.customitem_unclib.CustomItemLib;
import fr.teamunc.customitem_unclib.models.CustomNamespaceKey;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (item.getItemMeta() == null || !CustomNamespaceKey.CUSTOM_TYPE.hasCustomData(item) || !CustomNamespaceKey.CUSTOM_DISPLAYED_ATTACK_DAMAGE.hasCustomData(item)) return;

        for (Map.Entry<Enchantment, Integer> entry : event.getEnchantsToAdd().entrySet()) {
            Enchantment enchantment = entry.getKey();
            if (enchantment.getKey().equals(NamespacedKey.minecraft("sharpness"))) {
                Integer integer = entry.getValue();
                double extraDamage = 0.5 * Math.max(0, integer - 1) + 1.0;
                double baseDamage = CustomNamespaceKey.CUSTOM_DISPLAYED_ATTACK_DAMAGE.getCustomData(item);

                CustomNamespaceKey.CUSTOM_DISPLAYED_ATTACK_DAMAGE.setCustomData(item.getItemMeta(), (baseDamage + extraDamage));

                List<String> newAttackDamage = new ArrayList<>();
                newAttackDamage.add("" + (baseDamage + extraDamage));
                HashMap<CustomNamespaceKey, List<String>> data = new HashMap<>();
                data.put(CustomNamespaceKey.CUSTOM_DISPLAYED_ATTACK_DAMAGE, newAttackDamage);

                CustomItemLib.getUNCCustomItemController().updateLores(item, data);
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
            List<String> newAttackDamage = new ArrayList<>();
            newAttackDamage.add("10");
            HashMap<CustomNamespaceKey, List<String>> data = new HashMap<>();
            data.put(CustomNamespaceKey.CUSTOM_ATTACK_DAMAGE, newAttackDamage);

            CustomItemLib.getUNCCustomItemController().updateLores(result, data);
        }
    }
}
