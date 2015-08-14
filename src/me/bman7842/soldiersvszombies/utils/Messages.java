package me.bman7842.soldiersvszombies.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by brand_000 on 8/2/2015.
 */
public class Messages {

    public static void sendErrorMessage(Player p, String msg) {
        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "ERROR " + ChatColor.GRAY + msg);
    }

    public static void sendAlertMessage(Player p, String msg) {
        p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "ALERT " + ChatColor.GRAY + msg);
    }

    public static void sendNoPerms(Player p) {
        sendErrorMessage(p, "You don't have permission to run this command!");
    }

    public static void sendInGameMessage(Player p, String msg) {
        p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "IN-GAME BROADCAST " + ChatColor.GRAY + msg);
    }
}
