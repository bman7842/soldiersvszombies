package me.bman7842.soldiersvszombies.customevents;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by brand_000 on 8/2/2015.
 */
public class GameJoinEvent extends Event {

    Player p;

    public GameJoinEvent(Player p) {
        this.p = p;
    }

    public Player getPlayer() {
        return p;
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
