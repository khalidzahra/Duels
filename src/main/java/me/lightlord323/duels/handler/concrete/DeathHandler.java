package me.lightlord323.duels.handler.concrete;

import me.lightlord323.duels.Duels;
import me.lightlord323.duels.duel.Duel;
import me.lightlord323.duels.handler.DuelOutcome;
import me.lightlord323.duels.handler.Handler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

/**
 * Created by Khalid on 7/6/21.
 */
public class DeathHandler extends Handler implements Listener {

    public DeathHandler(Duels plugin) {
        super(plugin);
        registerAsListener(this);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            Duel duel = getPlugin().getHandlerManager().getDuelHandler().getDuel(player);
            if (duel != null && e.getFinalDamage() >= player.getHealth()) {
                e.setCancelled(true);
                boolean allDead = true;
                for (String participant : duel.getParticipants()) {
                    if (!participant.equals(player.getUniqueId().toString())) {
                        Player p2 = Bukkit.getPlayer(UUID.fromString(participant));
                        if (p2 != null && p2.getHealth() > 0)
                            allDead = false;
                    }
                }
                DuelOutcome outcome;
                if (allDead)
                    outcome = DuelOutcome.DRAW;
                else
                    outcome = DuelOutcome.LOSS;
                getPlugin().getHandlerManager().getDuelHandler().declareOutcome(player, outcome);
            }
        }
    }

}
