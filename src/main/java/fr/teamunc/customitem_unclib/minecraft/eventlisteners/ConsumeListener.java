package fr.teamunc.customitem_unclib.minecraft.eventlisteners;

import fr.teamunc.customitem_unclib.CustomItemLib;
import fr.teamunc.customitem_unclib.models.CustomNamespaceKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class ConsumeListener implements Listener {
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (!CustomItemLib.isInit())
            return;

        ItemStack originItem = event.getItem();
        Player player = event.getPlayer();

        if (originItem.getItemMeta() == null || !CustomNamespaceKey.CUSTOM_TYPE.hasCustomData(originItem)) return;

        if (CustomNamespaceKey.CUSTOM_FOOD.hasCustomData(originItem)) {
            int customFood = CustomNamespaceKey.CUSTOM_FOOD.getCustomData(originItem);
            int newVal = player.getFoodLevel() + customFood;
            player.setFoodLevel(Math.min(newVal, 20));
        }

        if (CustomNamespaceKey.CUSTOM_SATURATION.hasCustomData(originItem)) {
            float customSaturation = CustomNamespaceKey.CUSTOM_SATURATION.getCustomData(originItem);
            player.setSaturation(player.getSaturation() + customSaturation);
        }
    }
}
