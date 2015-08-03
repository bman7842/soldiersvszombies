package me.bman7842.soldiersvszombies.commands.subcommands;

import me.bman7842.soldiersvszombies.commands.SubCommand;
import me.bman7842.soldiersvszombies.customevents.GameJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by brand_000 on 8/2/2015.
 */
public class join implements SubCommand {

    @Override
    public boolean onCommand(Player p, String[] args) {
        Bukkit.getServer().getPluginManager().callEvent(new GameJoinEvent(p));
        return false;
    }

    @Override
    public String help(Player p) {
        return "§l- "+ ChatColor.RESET+ChatColor.YELLOW+"/svsz join"+ChatColor.GRAY+" - "+ ChatColor.YELLOW+"Joins the game!";
    }
}
