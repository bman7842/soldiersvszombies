package me.bman7842.soldiersvszombies.exceptions;

import org.bukkit.ChatColor;

/**
 * Created by brand_000 on 8/2/2015.
 */
public class ArenaNotFoundException extends Exception {

    public ArenaNotFoundException() {
        super(ChatColor.RED+"The main arena has not been set up yet!");
    }

    public ArenaNotFoundException(String message) {
        super(message);
    }

    public ArenaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArenaNotFoundException(Throwable cause) {
        super(cause);
    }

}
