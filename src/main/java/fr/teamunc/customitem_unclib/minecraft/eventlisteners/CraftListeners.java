package fr.teamunc.customitem_unclib.minecraft.eventlisteners;

import fr.teamunc.base_unclib.utils.helpers.Message;
import fr.teamunc.customitem_unclib.CustomItemLib;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CraftListeners implements Listener {

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        if (event.getRecipe() == null || event.getInventory().getResult() == null || !CustomItemLib.isInit()) {
            return;
        }

        ItemStack customItem = Arrays.stream(event.getInventory().getMatrix()).filter(itemStack -> itemStack != null && CustomItemLib.getUNCCustomItemController().isCustomItem(itemStack)).findFirst().orElse(null);
        if (customItem != null) {
            // if Material of the result is same as the material of the recipe
            if (customItem.getType() == event.getInventory().getResult().getType()) {
                Message.Get().broadcastMessageToConsole("You can't craft custom items");
                event.getInventory().setResult(null);
            }
        }
    }
}
