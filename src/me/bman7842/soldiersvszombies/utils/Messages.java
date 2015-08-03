package me.bman7842.soldiersvszombies.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by brand_000 on 8/2/2015.
 */
public class Messages {

    public static void sendErrorMessage(Player p, String msg) {
        p.sendMessage(ChatColor.BOLD + "" + ChatColor.RED + "ERROR: " + ChatColor.GRAY + " | " + msg);
    }

    public static void sendAlertMessage(Player p, String msg) {
        p.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "ALERT" + ChatColor.GRAY + " | " + msg);
    }
}
