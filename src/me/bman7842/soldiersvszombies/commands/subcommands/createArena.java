package me.bman7842.soldiersvszombies.commands.subcommands;

import com.sk89q.worldedit.bukkit.selections.Selection;
import me.bman7842.soldiersvszombies.SoldiersVSZombies;
import me.bman7842.soldiersvszombies.commands.CommandManager;
import me.bman7842.soldiersvszombies.commands.SubCommand;
import me.bman7842.soldiersvszombies.managers.ArenaManager;
import me.bman7842.soldiersvszombies.utils.FileManager;
import me.bman7842.soldiersvszombies.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by brand_000 on 8/2/2015.
 */
public class createArena implements SubCommand {

    @Override
    public boolean onCommand(Player p, String[] args) {
        if (args.length == 0) {
            Messages.sendErrorMessage(p, "Invalid usage, type /svsz createarena <name>");
            return false;
        }

        if (p.hasPermission(CommandManager.svszAdmin)) {
            if (ArenaManager.getInstance().getArena(args[0]) != null) {
                Messages.sendErrorMessage(p, "An arena with this name already exists!");
                return false;
            }

            Selection sel = SoldiersVSZombies.getWorldEdit().getSelection(p);

            if (sel == null) {
                Messages.sendErrorMessage(p, "Please make a WorldEdit selection of the arena!");
                return false;
            }

            FileManager.getArenas().set(args[0] + ".world", sel.getWorld().getName());
            SoldiersVSZombies.saveLocation(sel.getMinimumPoint(), FileManager.getArenas().createSection(args[0] + ".cornerA"));
            SoldiersVSZombies.saveLocation(sel.getMaximumPoint(), FileManager.getArenas().createSection(args[0] + ".cornerB"));
            FileManager.getArenas().save();
            ArenaManager.getInstance().setup();
            Messages.sendAlertMessage(p, "Created arena: " + args[0] + ". " + "Now you must set the lobby location and spawn locations!");
            return true;
        }

        return false;
    }

    @Override
    public String help(Player p) {
        return "§l- "+ChatColor.RESET+ChatColor.YELLOW+"/svsz createarena <name>"+ChatColor.GRAY+" - "+ ChatColor.YELLOW+"Adds a arena from the world edit points";
    }
}
