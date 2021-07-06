package me.lightlord323.duels.handler.concrete;

import me.lightlord323.duels.Duels;
import me.lightlord323.duels.handler.Handler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Khalid on 7/6/21.
 */
public class JoinHandler extends Handler implements Listener {

    public JoinHandler(Duels plugin) {
        super(plugin);
        registerAsListener(this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        getPlugin().getHandlerManager().getDPlayerHandler().loadDPlayer(e.getPlayer());
    }

}
