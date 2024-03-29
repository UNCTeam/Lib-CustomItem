package fr.teamunc.customitem_unclib.minecraft.eventlisteners;

import fr.teamunc.customitem_unclib.CustomItemLib;
import fr.teamunc.customitem_unclib.models.CustomNamespaceKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class CustomEventListener implements Listener {

    @EventHandler
    public void onItemUsed(PlayerInteractEvent event) {
        if (event.getItem() == null || !CustomItemLib.isInit() || event.isCancelled()) {
            return;
        }

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

        if (item.getItemMeta() == null || !CustomNamespaceKey.CUSTOM_TYPE.hasCustomData(item)) return;

        String customType = CustomNamespaceKey.CUSTOM_TYPE.getCustomData(event.getItem());

        if (CustomItemLib.getUNCCustomItemController().getCustomItemType(customType).getAction() != null) {
            int durabilityConsumed = CustomItemLib.getUNCCustomItemController().getCustomItemType(customType).getAction().execute(event);
            if (durabilityConsumed != 0) CustomItemLib.getUNCCustomItemController().changeDurability(item, event.getPlayer(), durabilityConsumed);
        }

    }

    @EventHandler
    public void onConsumeEvent(PlayerItemConsumeEvent event) {
        if (!CustomItemLib.isInit() || event.isCancelled())
            return;

        ItemStack originItem = event.getItem();

        if (originItem.getItemMeta() == null || !CustomNamespaceKey.CUSTOM_TYPE.hasCustomData(originItem)) return;

        String customType = CustomNamespaceKey.CUSTOM_TYPE.getCustomData(originItem);
        if (CustomItemLib.getUNCCustomItemController().getCustomItemType(customType).getAction() != null) {
            CustomItemLib.getUNCCustomItemController().getCustomItemType(customType).getAction().execute(event);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

        if (!CustomItemLib.isInit() || event.isCancelled()) {
            return;
        }

        if (item == null || !CustomNamespaceKey.CUSTOM_TYPE.hasCustomData(item)) {
            return;
        }

        String customType = CustomNamespaceKey.CUSTOM_TYPE.getCustomData(item);

        if (CustomItemLib.getUNCCustomItemController().getCustomItemType(customType).getAction() != null) {
            int durabilityConsumed = CustomItemLib.getUNCCustomItemController().getCustomItemType(customType).getAction().execute(event);
            if (durabilityConsumed != 0) CustomItemLib.getUNCCustomItemController().changeDurability(item, event.getPlayer(), durabilityConsumed);
        }
    }

    @EventHandler
    public void onPlayerDamageDone(EntityDamageByEntityEvent event) {
        if (!CustomItemLib.isInit() || event.isCancelled()) {
            return;
        }

        if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) && event.getDamager() instanceof LivingEntity ) {
            LivingEntity damager = (LivingEntity) event.getDamager();

            if (damager.getEquipment() == null) return;

            ItemStack item = damager.getEquipment().getItemInMainHand();

            if (item == null || !CustomNamespaceKey.CUSTOM_TYPE.hasCustomData(item)) {
                return;
            }

            String customType = CustomNamespaceKey.CUSTOM_TYPE.getCustomData(item);

            if (CustomItemLib.getUNCCustomItemController().getCustomItemType(customType).getAction() != null) {
                int durabilityConsumed =CustomItemLib.getUNCCustomItemController().getCustomItemType(customType).getAction().execute(event);
                if (durabilityConsumed != 0 && event.getDamager() instanceof Player) {
                    Player eventDamager = (Player) event.getDamager();
                    CustomItemLib.getUNCCustomItemController().changeDurability(item, eventDamager, durabilityConsumed);
                }
            }
        }

    }
}
