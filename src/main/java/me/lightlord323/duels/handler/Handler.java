package me.lightlord323.duels.handler;

import me.lightlord323.duels.Duels;
import org.bukkit.event.Listener;

/**
 * Created by Khalid on 7/6/21.
 */
public class Handler {

    private final Duels plugin;

    public Handler(Duels plugin) {
        this.plugin = plugin;
    }

    protected void registerAsListener(Handler handler) {
        if (handler instanceof Listener) {
            plugin.getServer().getPluginManager().registerEvents(((Listener) handler), plugin);
        }
    }

    protected Duels getPlugin() {
        return plugin;
    }
}
