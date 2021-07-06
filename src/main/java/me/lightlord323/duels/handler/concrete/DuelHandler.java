package me.lightlord323.duels.handler.concrete;

import me.lightlord323.duels.Duels;
import me.lightlord323.duels.duel.Duel;
import me.lightlord323.duels.handler.DuelOutcome;
import me.lightlord323.duels.handler.Handler;
import me.lightlord323.duels.handler.Loadable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khalid on 7/6/21.
 */
public class DuelHandler extends Handler implements Loadable {

    private List<Duel> activeDuels;

    public DuelHandler(Duels plugin) {
        super(plugin);
        activeDuels = new ArrayList<>();
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onUnload() {
        activeDuels.forEach(Duel::end);
    }

    public void registerDuel(Duel duel) {
        activeDuels.add(duel);
    }

    public Duel getDuel(Player player) {
        return activeDuels.stream().filter(d -> d.containsPlayer(player.getUniqueId())).findAny().orElse(null);
    }

    public void declareOutcome(Player player, DuelOutcome duelOutcome) {
        Duel duel = getDuel(player);
        if (duel != null) {
            switch (duelOutcome) {
                case LOSS:
                    duel.declareLoser(player, getPlugin());
                    break;
                case DRAW:
                    duel.declareDraw(getPlugin());
                    break;
            }
            duel.end();
            this.activeDuels.remove(duel);
        }
    }

}
