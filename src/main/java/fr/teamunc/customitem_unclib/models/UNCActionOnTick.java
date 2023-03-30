package fr.teamunc.customitem_unclib.models;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface UNCActionOnTick {
    void execute(ItemStack itemStack, Player player);
}
