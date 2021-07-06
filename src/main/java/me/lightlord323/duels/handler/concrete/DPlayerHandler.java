package me.lightlord323.duels.handler.concrete;

import me.lightlord323.duels.Duels;
import me.lightlord323.duels.duel.DPlayer;
import me.lightlord323.duels.handler.Handler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khalid on 7/6/21.
 */
public class DPlayerHandler extends Handler {

    private List<DPlayer> dPlayers;

    public DPlayerHandler(Duels plugin) {
        super(plugin);
        this.dPlayers = new ArrayList<>();
    }

    public void loadDPlayer(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> {
            try {
                int[] stats = getPlugin().getDataSource().queryPlayerStats(player.getUniqueId());
                dPlayers.add(new DPlayer(player.getUniqueId(), stats[0], stats[2], stats[1]));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void saveDPlayerAsync(Player player) {
        DPlayer dPlayer = getDPlayer(player);
        if (dPlayer == null)
            return;
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> saveDPlayer(dPlayer));
    }

    public void saveAllDPlayers() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            DPlayer dPlayer = getDPlayer(player);
            if (dPlayer == null)
                return;
            saveDPlayer(dPlayer);
        });
    }

    public DPlayer getDPlayer(Player player) {
        return this.dPlayers.stream()
                .filter(dPlayer -> dPlayer.getUniqueId().toString().equals(player.getUniqueId().toString()))
                .findAny()
                .orElse(null);
    }

    public DPlayer getDPlayer(String uuid) {
        return this.dPlayers.stream()
                .filter(dPlayer -> dPlayer.getUniqueId().toString().equals(uuid))
                .findAny()
                .orElse(null);
    }

    private void saveDPlayer(DPlayer dPlayer) {
        try {
            this.dPlayers.remove(dPlayer);
            getPlugin().getDataSource().savePlayerStats(dPlayer);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
