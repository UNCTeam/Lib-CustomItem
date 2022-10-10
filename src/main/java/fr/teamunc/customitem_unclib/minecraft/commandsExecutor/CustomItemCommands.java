package fr.teamunc.customitem_unclib.minecraft.commandsExecutor;

import fr.teamunc.base_unclib.utils.helpers.Message;
import fr.teamunc.customitem_unclib.CustomItemLib;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomItemCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!CustomItemLib.isInit()) {
            Message.Get().sendMessage("CustomItemLib is not initialized!", sender, true);
            return true;
        }

        if (args.length != 0) {
            switch (args[0]) {
                case "give": {
                    if (args.length < 3 || args.length > 4) {
                        Message.Get().sendMessage("usage : /uncitem give <playerName> <customItemName> <(optional) amount>", sender, true);
                        return true;
                    } else {
                        Player player = Bukkit.getPlayer(args[1]);

                        if (player == null) {
                            Message.Get().sendMessage("Player " + args[1] + " not found!", sender, true);
                            return true;
                        }

                        try {
                            int amount = args.length == 4 ? Integer.parseInt(args[3]) : 1;
                            try {
                                CustomItemLib.getUNCCustomItemController().giveCustomItem(player, args[2], amount);
                                Message.Get().sendMessage("CustomItem " + args[2] + " given to " + args[1] + "!", sender, false);
                            } catch (Exception e) {
                                Message.Get().sendMessage("CustomItem " + args[2] + " not found!", sender, true);
                            }
                        } catch (NumberFormatException e) {
                            Message.Get().sendMessage("Invalid amount!", sender, true);
                        }
                        return true;
                    }
                }
                default: {
                    Message.Get().sendMessage("usage : /uncitem give <playerName> <customItemName> <(optional) amount>", sender, true);
                    return true;
                }
            }
        } else {
            Message.Get().sendMessage("usage : /uncitem give <playerName> <customItemName> <(optional) amount>", sender, true);
            return true;
        }
    }
}
