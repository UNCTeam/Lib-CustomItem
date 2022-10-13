package fr.teamunc.customitem_unclib.minecraft.commandsExecutor.commandsTab;

import fr.teamunc.base_unclib.models.libtools.CommandsTab;
import fr.teamunc.customitem_unclib.CustomItemLib;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CustomItemGiveTab extends CommandsTab {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        List<String> result = checkAllTab(
                args,
                Arrays.asList("give"),
                CustomItemLib.getPlugin().getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()),
                new ArrayList<>(CustomItemLib.getUNCCustomItemController().getRegisteredCustomItemTypes()));

        //sort the list
        Collections.sort(result);

        return result;
    }
}
