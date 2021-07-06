package me.lightlord323.duels.handler.concrete;

import me.lightlord323.duels.Duels;
import me.lightlord323.duels.duel.Duel;
import me.lightlord323.duels.handler.DuelOutcome;
import me.lightlord323.duels.handler.Handler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Khalid on 7/6/21.
 */
public class LeaveHandler extends Handler implements Listener {

    public LeaveHandler(Duels plugin) {
        super(plugin);
        registerAsListener(this);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Duel duel = getPlugin().getHandlerManager().getDuelHandler().getDuel(e.getPlayer());
        if (duel != null)
            getPlugin().getHandlerManager().getDuelHandler().declareOutcome(e.getPlayer(), DuelOutcome.LOSS);
        getPlugin().getHandlerManager().getDPlayerHandler().saveDPlayerAsync(e.getPlayer());
    }

}
