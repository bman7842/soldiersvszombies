package me.bman7842.soldiersvszombies.managers;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import me.bman7842.soldiersvszombies.SoldiersVSZombies;
import me.bman7842.soldiersvszombies.exceptions.ArenaNotFoundException;
import me.bman7842.soldiersvszombies.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brand_000 on 8/2/2015.
 */
public class Arena {

    public enum ArenaState {
        WAITING(ChatColor.WHITE, "Waiting..."), LOBBY_COUNTDOWN(ChatColor.BLUE, "Lobby Countdown"), IN_GAME(ChatColor.RED, "In-Game"), RESETTING(ChatColor.WHITE, "Resetting...");

        private String name;
        private ChatColor color;

        ArenaState(ChatColor color, String name) {
            this.color = color;
            this.name = name;
        }

        public String getName() {
            return color+name;
        }
    }

    private ArenaState state;
    private String id;
    private CuboidSelection bounds;
    private Zombies zombies;
    private Soldiers soldiers;
    private Ghosts ghosts;
    private Lobby lobby;
    private Integer lobbyCountdown;
    /**
     * @param id Name of arena.
     */
    public Arena(String id) {
        this.id = id;
        Location cornerA = null;
        Location cornerB = null;
        try {
            cornerA = SoldiersVSZombies.parseLocation(FileManager.getArenas().<ConfigurationSection>get(id + ".cornerA"));
            cornerB = SoldiersVSZombies.parseLocation(FileManager.getArenas().<ConfigurationSection>get(id + ".cornerB"));
        } catch(ArenaNotFoundException e) {

        }
        this.bounds = new CuboidSelection(Bukkit.getWorld(FileManager.getArenas().<String>get(id + ".world")),
                cornerA,
                cornerB
        );
        this.state = ArenaState.WAITING;

        if (FileManager.getArenas().contains(id + ".ghostspawn")) {
            Location spawn = null;
            try {
                spawn = SoldiersVSZombies.parseLocation(FileManager.getArenas().<ConfigurationSection>get(id + ".ghostspawn"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            ghosts = new Ghosts(spawn);
        }
        if (FileManager.getArenas().contains(id + ".soldierspawn")) {
            Location spawn = null;
            try {
                spawn = SoldiersVSZombies.parseLocation(FileManager.getArenas().<ConfigurationSection>get(id + ".soldierspawn"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            soldiers = new Soldiers(spawn);
        }
        if (FileManager.getArenas().contains(id + ".zombiespawn")) {
            Location spawn = null;
            try {
                spawn = SoldiersVSZombies.parseLocation(FileManager.getArenas().<ConfigurationSection>get(id + ".zombiespawn"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            zombies = new Zombies(spawn);
        }
        if (FileManager.getArenas().contains(id + ".lobby")) {
            Location spawn = null;
            try {
                spawn = SoldiersVSZombies.parseLocation(FileManager.getArenas().<ConfigurationSection>get(id + ".lobby"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            lobby = new Lobby(spawn);
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(SoldiersVSZombies.getPlugin(), new Runnable() {
            @Override
            public void run() {
                if (lobby != null && zombies != null && soldiers != null && ghosts != null) {
                    if (state.equals(ArenaState.WAITING)) {
                        if (lobby.returnPlayers().size() >= 1) {
                            lobbyCountdown = 20;
                            state = ArenaState.LOBBY_COUNTDOWN;
                            broadcastMessageIngame("Countdown starting! 20 seconds until game starts!");
                        }
                    } else if (state.equals(ArenaState.LOBBY_COUNTDOWN)) {
                        if (lobby.returnPlayers().size() < 1) {
                            lobbyCountdown = 0;
                            state = ArenaState.WAITING;
                            broadcastMessageIngame("Someone left, bummer, you need at least 6 players for the countdown to begin!");
                        } else {
                            lobbyCountdown = lobbyCountdown - 1;
                            broadcastMessageIngame(lobbyCountdown.toString() + " seconds left until the game starts!");
                            if (lobbyCountdown == 0) {
                                broadcastMessageIngame("The game is starting, beginning the teleportation process!");
                                //TOOD: Make a method that teleports users
                                state = ArenaState.IN_GAME;
                            }
                        }
                    }
                }
            }
        }, 20L, 20L);
    }

    public String getID() {
        return id;
    }

    public CuboidSelection getBounds() {
        return bounds;
    }

    public boolean containsPlayer(Player player) {
        if (lobby.hasPlayer(player)) { return true; }
        if (ghosts.hasPlayer(player)) { return true; }
        if (zombies.hasPlayer(player)) { return true; }
        if (soldiers.hasPlayer(player)) { return true; }
        return false;
    }

    public void addSoldier(Player p) {
        p.getInventory().clear();

        if (ghosts.hasPlayer(p)) { ghosts.removePlayer(p); }
        if (lobby.hasPlayer(p)) { lobby.removePlayer(p); }
        if (zombies.hasPlayer(p)) { zombies.removePlayer(p); }
        if (soldiers.hasPlayer(p)) {
            Bukkit.getLogger().warning("Soldiers already contains " + p.getName());
            return;
        }
        soldiers.addPlayer(p);
        p.teleport(soldiers.getLocation());
    }

    public void addZombie(Player p) {
        p.getInventory().clear();

        if (ghosts.hasPlayer(p)) { ghosts.removePlayer(p); }
        if (lobby.hasPlayer(p)) { lobby.removePlayer(p); }
        if (soldiers.hasPlayer(p)) { soldiers.removePlayer(p); }
        if (zombies.hasPlayer(p)) {
            Bukkit.getLogger().warning("Zombies already contains " + p.getName());
            return;
        }
        zombies.addPlayer(p);
        p.teleport(zombies.getLocation());
    }

    public void addGhost(Player p) {
        p.getInventory().clear();

        if (soldiers.hasPlayer(p)) { soldiers.removePlayer(p); }
        if (lobby.hasPlayer(p)) { lobby.removePlayer(p); }
        if (zombies.hasPlayer(p)) { zombies.removePlayer(p); }
        if (soldiers.hasPlayer(p)) {
            Bukkit.getLogger().warning("Soldiers already contains " + p.getName());
            return;
        }
        ghosts.addPlayer(p);
        p.teleport(ghosts.getLocation());
    }

    public void addPlayerLobby(Player p) {
        p.getInventory().clear();

        if (ghosts.hasPlayer(p)) { ghosts.removePlayer(p); }
        if (soldiers.hasPlayer(p)) { soldiers.removePlayer(p); }
        if (zombies.hasPlayer(p)) { zombies.removePlayer(p); }
        if (lobby.hasPlayer(p)) {
            Bukkit.getLogger().warning("Lobby already contains " + p.getName());
            return;
        }
        lobby.addPlayer(p);
        p.teleport(lobby.getLocation());
    }

    public void removePlayer(Player p) {
        p.performCommand("/spawn");
        if (ghosts.hasPlayer(p)) { ghosts.removePlayer(p); }
        if (soldiers.hasPlayer(p)) { soldiers.removePlayer(p); }
        if (zombies.hasPlayer(p)) { zombies.removePlayer(p); }
        if (lobby.hasPlayer(p)) { lobby.removePlayer(p); }
    }

    public void broadcastMessageIngame(String msg) {
        for (Player p : lobby.returnPlayers()) {
            Messages.sendInGameMessage(p, msg);
        }
    }

    public void setState(ArenaState state) {
        this.state = state;
    }

    public boolean isState(ArenaState state) {
        return this.state == state;
    }

}
