package me.bman7842.soldiersvszombies.commands.subcommands;

import me.bman7842.soldiersvszombies.SoldiersVSZombies;
import me.bman7842.soldiersvszombies.commands.CommandManager;
import me.bman7842.soldiersvszombies.commands.SubCommand;
import me.bman7842.soldiersvszombies.managers.Arena;
import me.bman7842.soldiersvszombies.managers.ArenaManager;
import me.bman7842.soldiersvszombies.utils.FileManager;
import me.bman7842.soldiersvszombies.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * Created by brand_000 on 8/8/2015.
 */
public class setLobbySpawn implements SubCommand {

    @Override
    public boolean onCommand(Player p, String args[]) {
        if (args.length == 0) {
            Messages.sendErrorMessage(p, "Invalid usage, type /svsz setlobbyspawn <name>");
            return false;
        }

        if (p.hasPermission(CommandManager.svszAdmin)) {
            Arena arena = ArenaManager.getInstance().getArena(args[0]);

            if (FileManager.getArenas().<ConfigurationSection>get(arena.getID()+".lobby") == null) {
                FileManager.getArenas().createSection(arena.getID()+".lobby");
            }
            SoldiersVSZombies.saveLocation(p.getLocation(), FileManager.getArenas().<ConfigurationSection>get(arena.getID() + ".lobby"));
            FileManager.getArenas().save();
            ArenaManager.getInstance().setup();
            Messages.sendAlertMessage(p, "Successfully set the lobby location of arena " + arena.getID() + "!");
            return false;
        } else {
            Messages.sendNoPerms(p);
        }

        return false;
    }

    @Override
    public String help(Player p) {
        return "�l- "+ ChatColor.RESET+ChatColor.YELLOW+"/svsz setlobbyspawn <name>"+ChatColor.GRAY+" - "+ ChatColor.YELLOW+"Sets the Arena's lobby spawn!";
    }

}
