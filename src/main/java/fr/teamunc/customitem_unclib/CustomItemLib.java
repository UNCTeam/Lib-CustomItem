package fr.teamunc.customitem_unclib;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class CustomItemLib {

    @Getter
    private static JavaPlugin plugin;

    public static void init(JavaPlugin plugin) {
        CustomItemLib.plugin = plugin;
    }

    public static boolean isInit() {
        return Objects.nonNull(plugin);
    }
}
