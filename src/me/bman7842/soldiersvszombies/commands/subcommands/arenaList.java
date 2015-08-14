package me.bman7842.soldiersvszombies.commands.subcommands;

import me.bman7842.soldiersvszombies.commands.CommandManager;
import me.bman7842.soldiersvszombies.commands.SubCommand;
import me.bman7842.soldiersvszombies.managers.Arena;
import me.bman7842.soldiersvszombies.managers.ArenaManager;
import me.bman7842.soldiersvszombies.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by brand_000 on 8/8/2015.
 */
public class arenaList implements SubCommand {

    @Override
    public boolean onCommand(Player p, String args[]) {
        if (p.hasPermission(CommandManager.svszAdmin)) {
            p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "ARENAS:");
            for (Arena arena : ArenaManager.getInstance().getArenas()) {
                p.sendMessage(ChatColor.GREEN + arena.getID());
            }
            return false;
        } else {
            Messages.sendNoPerms(p);
        }

        return false;
    }

    @Override
    public String help(Player p) {
        return "§l- "+ ChatColor.RESET+ChatColor.YELLOW+"/svsz arenalist"+ChatColor.GRAY+" - "+ ChatColor.YELLOW+"Displays the list of arenas!";
    }

}
