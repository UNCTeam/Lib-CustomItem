package fr.teamunc.customitem_unclib.minecraft.eventlisteners;

import fr.teamunc.customitem_unclib.CustomItemLib;
import fr.teamunc.customitem_unclib.models.CustomNamespaceKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CustomEventListener implements Listener {

    @EventHandler
    public void onItemUsed(PlayerInteractEvent event) {
        if (!CustomItemLib.isInit()) {
            return ;
        }

        if (event.getItem() == null || !CustomNamespaceKey.CUSTOM_TYPE.hasCustomData(event.getItem())) {
            return ;
        }

        String customType = CustomNamespaceKey.CUSTOM_TYPE.getCustomData(event.getItem());

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
            CustomItemLib.getUNCCustomItemController().getCustomItemType(customType).getAction().execute(event);
        }

    }
}
