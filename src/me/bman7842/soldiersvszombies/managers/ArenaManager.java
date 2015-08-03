package me.bman7842.soldiersvszombies.managers;

import me.bman7842.soldiersvszombies.utils.FileManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by brand_000 on 8/2/2015.
 */
public class ArenaManager {

    private ArrayList<Arena> arenas;

    private ArenaManager(){
        this.arenas = new ArrayList<Arena>();
    }

    static ArenaManager instance = new ArenaManager();

    public static ArenaManager getInstance() {
        return instance;
    }

    public void setup() {
        arenas.clear();

        for (String arenaId : FileManager.getArenas().getKeys()) {
            arenas.add(new Arena(arenaId));
        }
    }

    public Arena getArena(String id) {
        for (Arena arena : arenas) {
            if (arena.getID().equals(id)) {
                return arena;
            }
        }
        return null;
    }

    public ArrayList<Arena> getArenas() {
        return arenas;
    }

    public Arena getActiveArena() {
        Random r = new Random();
        int index = r.nextInt(getArenas().size());
        return getArenas().get(index);
    }

}
