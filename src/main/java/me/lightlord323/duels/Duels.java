package me.lightlord323.duels;

import me.lightlord323.duels.data.DataSource;
import me.lightlord323.duels.file.SettingsFile;
import me.lightlord323.duels.handler.concrete.HandlerManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Duels extends JavaPlugin {

    private HandlerManager handlerManager;
    private SettingsFile settingsFile;
    private DataSource dataSource;

    @Override
    public void onEnable() {
        this.settingsFile = new SettingsFile(this);
        this.dataSource = new DataSource(this);
        this.handlerManager = new HandlerManager(this);
        this.handlerManager.onLoad();
    }

    @Override
    public void onDisable() {
        this.handlerManager.getDPlayerHandler().saveAllDPlayers();
        this.handlerManager.onUnload();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public SettingsFile getSettingsFile() {
        return settingsFile;
    }

    public HandlerManager getHandlerManager() {
        return handlerManager;
    }
}
