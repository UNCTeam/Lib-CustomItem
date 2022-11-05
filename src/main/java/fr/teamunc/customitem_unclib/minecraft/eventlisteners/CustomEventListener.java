package fr.teamunc.customitem_unclib.minecraft.eventlisteners;

import fr.teamunc.customitem_unclib.CustomItemLib;
import fr.teamunc.customitem_unclib.models.CustomNamespaceKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class CustomEventListener implements Listener {

    @EventHandler
    public void onItemUsed(PlayerInteractEvent event) {
        if (event.getItem() == null || !CustomItemLib.isInit()) {
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
        if (!CustomItemLib.isInit())
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

        if (!CustomItemLib.isInit()) {
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
}
