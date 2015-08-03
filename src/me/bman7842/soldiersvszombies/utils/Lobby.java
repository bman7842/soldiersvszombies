package me.bman7842.soldiersvszombies.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;

/**
 * Created by brand_000 on 8/2/2015.
 */
public class Lobby {

    private Location location;
    private HashSet<Player> players = new HashSet<Player>();

    public Lobby(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public boolean hasPlayer(Player p) {
        if (players.contains(p)) {
            return true;
        }
        return false;
    }

}
