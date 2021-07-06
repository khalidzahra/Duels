package me.lightlord323.duels.duel;

import me.lightlord323.duels.Duels;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Khalid on 7/6/21.
 */
public class Duel {

    private final HashMap<String, DPlayerInventory> participants;
    private String winner;
    private boolean draw = false;

    public Duel(Player player1, Player player2) {
        participants = new HashMap<>();
        participants.put(player1.getUniqueId().toString(), new DPlayerInventory(player1.getInventory().getContents(), player1.getInventory().getArmorContents()));
        participants.put(player2.getUniqueId().toString(), new DPlayerInventory(player2.getInventory().getContents(), player2.getInventory().getArmorContents()));
        giveKits();
    }

    public void end() {
        participants.forEach((u, c) -> {
            Player p = Bukkit.getPlayer(UUID.fromString(u));
            if (p != null) {
                if (draw) {
                    p.sendMessage(ChatColor.BLUE + "The duel has ended in a draw!");
                } else {
                    if (winner != null) {
                        p.sendMessage(ChatColor.GREEN + winner + ChatColor.BLUE + " has won the duel!");
                    } else {
                        p.sendMessage(ChatColor.BLUE + "The duel has been concluded.");
                    }
                }
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
                p.getInventory().setContents(c.getContents());
                p.getInventory().setArmorContents(c.getArmorContents());
            }
        });
    }

    public void declareLoser(Player player, Duels plugin) {
        participants.keySet().forEach(u -> {
            DPlayer dPlayer = plugin.getHandlerManager().getDPlayerHandler().getDPlayer(u);
            if (dPlayer != null) {
                if (u.equals(player.getUniqueId().toString())) {
                    dPlayer.setLosses(dPlayer.getLosses() + 1);
                } else {
                    dPlayer.setWins(dPlayer.getWins() + 1);
                    Player p = Bukkit.getPlayer(UUID.fromString(u));
                    if (p != null)
                        winner = p.getName();
                }
            }
        });
    }

    public void declareDraw(Duels plugin) {
        this.draw = true;
        participants.keySet().forEach(u -> {
            DPlayer dPlayer = plugin.getHandlerManager().getDPlayerHandler().getDPlayer(u);
            if (dPlayer != null) {
                dPlayer.setDraws(dPlayer.getDraws() + 1);
            }
        });
    }

    private void giveKits() {
        participants.keySet().forEach(u -> {
            Player p = Bukkit.getPlayer(UUID.fromString(u));
            if (p != null) {
                p.getInventory().setContents(DuelKit.getContents());
                p.getInventory().setArmorContents(DuelKit.getArmorContents());
            }
        });
    }

    public boolean containsPlayer(UUID uuid) {
        return this.participants.containsKey(uuid.toString());
    }

    public DPlayerInventory getPlayerInventory(UUID uuid) {
        if (participants.containsKey(uuid.toString()))
            return participants.get(uuid.toString());
        return null;
    }

    public Set<String> getParticipants() {
        return participants.keySet();
    }
}
