package me.bman7842.soldiersvszombies.eventhandlers;

import me.bman7842.soldiersvszombies.customevents.GameJoinEvent;
import me.bman7842.soldiersvszombies.managers.Arena;
import me.bman7842.soldiersvszombies.managers.ArenaManager;
import me.bman7842.soldiersvszombies.utils.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by brand_000 on 8/2/2015.
 */
public class joinGameEvent implements Listener {

    @EventHandler
    public void onGameJoin(GameJoinEvent e) {
        Player p = e.getPlayer();

        Arena mainArena = ArenaManager.getInstance().getArena("main");

        if (mainArena.containsPlayer(p)) {
            Messages.sendErrorMessage(p, "You are already in-game!");
        } else {
            if (mainArena.isState(Arena.ArenaState.IN_GAME)) {
                Messages.sendAlertMessage(p, "Since the game has already begun you have joined as a ghost!");
                mainArena.addGhost(p);
            } else {
                Messages.sendAlertMessage(p, "You are now in the lobby, the game will be starting soon!");
                mainArena.addPlayerLobby(p);
            }
        }
    }

}
