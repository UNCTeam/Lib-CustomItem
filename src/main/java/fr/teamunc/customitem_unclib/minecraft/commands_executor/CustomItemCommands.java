package fr.teamunc.customitem_unclib.minecraft.commands_executor;

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
        }
        else if (args.length < 3 || args.length > 4 || !"give".equals(args[0])) {
                    sendErrorMessage(sender);
        } else {
            Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                Message.Get().sendMessage("Player " + args[1] + " not found!", sender, true);
            } else try {
                int amount = args.length == 4 ? Integer.parseInt(args[3]) : 1;
                CustomItemLib.getUNCCustomItemController().giveCustomItem(player, args[2], amount);
                Message.Get().sendMessage("CustomItem " + args[2] + " given to " + args[1] + "!", sender, false);
            } catch (NumberFormatException e) {
                Message.Get().sendMessage("Invalid amount!", sender, true);
            } catch (Exception e) {
                Message.Get().broadcastMessageToConsole(e.getMessage());
                Message.Get().sendMessage("CustomItem " + args[2] + " not found!", sender, true);
            }
        }
        return true;
    }

    public static String[] getCommands() {
        return new String[] {"give"};
    }

    public void sendErrorMessage(CommandSender sender) {
        Message.Get().sendMessage("usage : /uncitem give <playerName> <customItemName> <(optional) amount>", sender, true);
    }
}
