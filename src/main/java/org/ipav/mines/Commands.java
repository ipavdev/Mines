package org.ipav.mines;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.Arrays;
import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1)
            return false;
        return switch (args[0].toLowerCase()) {
            case "edit" -> {
                Menu.openInventory((Player) sender);
                yield true;
            }
            case "reset" -> {
                Mine.FillMine((Player) sender, ((Player) sender).getWorld(), -950, 110, -194, -900, 200);
                yield true;
            }
            default -> false;
        };
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1)
            return Arrays.asList("edit", "reset");
        return Arrays.asList();
    }
}
