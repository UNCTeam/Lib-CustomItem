package fr.teamunc.customitem_unclib.minecraft.eventlisteners;

import fr.teamunc.base_unclib.utils.helpers.Message;
import fr.teamunc.customitem_unclib.CustomItemLib;
import fr.teamunc.customitem_unclib.models.CustomNamespaceKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

public class DurabilityListener implements Listener {

    @EventHandler
    public void onDurabilityChange(PlayerItemDamageEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        Player player = event.getPlayer();

        Message.Get().broadcastMessageToConsole("" + CustomNamespaceKey.CUSTOM_TYPE.hasCustomData(item));
        if (item.getItemMeta() == null || !CustomNamespaceKey.CUSTOM_TYPE.hasCustomData(item)) return;

        if (CustomNamespaceKey.CUSTOM_UNBREAKABLE.hasCustomData(item) && CustomNamespaceKey.CUSTOM_UNBREAKABLE.getCustomData(item).equals((byte) 1)) {
            event.setCancelled(true);
            return;
        }

        CustomItemLib.getUNCCustomItemController().changeDurability(item, player, event.getDamage());
    }
}
