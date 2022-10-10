package fr.teamunc.customitem_unclib;

import fr.teamunc.customitem_unclib.controllers.UNCCustomItemController;
import fr.teamunc.customitem_unclib.minecraft.commandsExecutor.CustomItemCommands;
import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;

public class CustomItemLib {

    @Getter
    private static JavaPlugin plugin;

    @Getter
    private static UNCCustomItemController UNCCustomItemController;

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
        }
    }
}
