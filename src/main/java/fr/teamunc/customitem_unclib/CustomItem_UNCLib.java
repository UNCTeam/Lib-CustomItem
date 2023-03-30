package fr.teamunc.customitem_unclib;

import org.bukkit.plugin.java.JavaPlugin;

public final class CustomItem_UNCLib extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        CustomItemLib.initGameListeners(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (CustomItemLib.isInit()) {
            CustomItemLib.getUNCCustomItemController().stopCustomItemActionTimer();
        }
    }
}
