package me.bman7842.soldiersvszombies.utils;

import me.bman7842.soldiersvszombies.SoldiersVSZombies;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Set;

/**
 * Created by brand_000 on 8/2/2015.
 */
public class FileManager {

    private static final FileManager conf = new FileManager("config"), arenas = new FileManager("arenas");

    public static FileManager getConfig() {
        return conf;
    }

    public static FileManager getArenas() {
        return arenas;
    }

    File f;
    FileConfiguration config;

    private FileManager(String path) {
        if (!SoldiersVSZombies.getPlugin().getDataFolder().exists()) {
            SoldiersVSZombies.getPlugin().getDataFolder().mkdir();
        }

        f = new File(SoldiersVSZombies.getPlugin().getDataFolder(), path+".yml");

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(f);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String path) {
        return (T) config.get(path);
    }

    public Set<String> getKeys() {
        return config.getKeys(false);
    }

    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }
    public ConfigurationSection createSection(String path) {
        ConfigurationSection cs = config.createSection(path);
        save();
        return cs;
    }



    public boolean contains(String path) {
        return config.contains(path);
    }

    public void save() {
        try {
            config.save(f);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
