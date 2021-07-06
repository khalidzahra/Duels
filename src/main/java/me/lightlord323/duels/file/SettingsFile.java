package me.lightlord323.duels.file;

import me.lightlord323.duels.Duels;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Khalid on 7/6/21.
 */
public class SettingsFile {

    private File file;
    private FileConfiguration config;

    public SettingsFile(Duels plugin) {
        this.file = new File(plugin.getDataFolder(), "settings.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists())
            plugin.saveResource("settings.yml", false);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
