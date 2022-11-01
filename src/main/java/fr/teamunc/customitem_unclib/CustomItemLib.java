package fr.teamunc.customitem_unclib;

import fr.teamunc.customitem_unclib.controllers.UNCCustomItemController;
import fr.teamunc.customitem_unclib.minecraft.commands_executor.CustomItemCommands;
import fr.teamunc.customitem_unclib.minecraft.commands_executor.commands_tab.CustomItemGiveTab;
import fr.teamunc.customitem_unclib.minecraft.eventlisteners.*;
import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;

public class CustomItemLib {

    @Getter
    private static JavaPlugin plugin;

    @Getter
    private static UNCCustomItemController UNCCustomItemController;

    private CustomItemLib() {}

    public static void init(JavaPlugin plugin) {
        CustomItemLib.plugin = plugin;

        // init team container
        UNCCustomItemController = new UNCCustomItemController();

        // register commands
        initCommands();
    }

    public static boolean isInit() {
        return Objects.nonNull(plugin);
    }

    private static void initCommands() {
        PluginCommand teamCommand = plugin.getCommand("uncitem");
        if (teamCommand != null) {
            teamCommand.setExecutor(new CustomItemCommands());
            teamCommand.setTabCompleter(new CustomItemGiveTab());
        }
    }

    public static void initGameListeners(CustomItemUNCLib customItemUncLib) {
        customItemUncLib.getServer().getPluginManager().registerEvents(new CraftListener(), customItemUncLib);
        customItemUncLib.getServer().getPluginManager().registerEvents(new DurabilityListener(), customItemUncLib);
        customItemUncLib.getServer().getPluginManager().registerEvents(new DamageListener(), customItemUncLib);
        customItemUncLib.getServer().getPluginManager().registerEvents(new CustomEventListener(), customItemUncLib);
        customItemUncLib.getServer().getPluginManager().registerEvents(new ConsumeListener(), customItemUncLib);
    }
}
