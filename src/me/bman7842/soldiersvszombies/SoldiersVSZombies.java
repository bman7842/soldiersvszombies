package me.bman7842.soldiersvszombies;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.bman7842.soldiersvszombies.commands.CommandManager;
import me.bman7842.soldiersvszombies.eventhandlers.joinGameEvent;
import me.bman7842.soldiersvszombies.exceptions.ArenaNotFoundException;
import me.bman7842.soldiersvszombies.managers.Arena;
import me.bman7842.soldiersvszombies.managers.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by brand_000 on 8/2/2015.
 */
public class SoldiersVSZombies extends JavaPlugin {

    public static Arena arena;

    @Override
    public void onEnable() {
        //SETUP\\
        ArenaManager.getInstance().setup();
        if (ArenaManager.getInstance().getArenas().size() > 0) {
            arena = ArenaManager.getInstance().getActiveArena();
        }

        //COMMANDS\\
        getCommand("svsz").setExecutor(new CommandManager());

        //EVENTS\\
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new joinGameEvent(), this);
    }

    public static void saveLocation(Location location, ConfigurationSection section) {
        section.set("world", location.getWorld().getName());
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());
        section.set("pitch", location.getPitch());
        section.set("yaw", location.getYaw());
    }

    public static Location parseLocation(ConfigurationSection location) throws ArenaNotFoundException {
        World world = Bukkit.getWorld(location.getString("world"));
        double x = location.getDouble("x");
        double y = location.getDouble("y");
        double z = location.getDouble("z");
        float pitch = (float) location.getDouble("pitch");
        float yaw = (float) location.getDouble("yaw");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static WorldEditPlugin getWorldEdit() {
        return (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
    }

    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("SoldiersVSZombies");
    }
}
