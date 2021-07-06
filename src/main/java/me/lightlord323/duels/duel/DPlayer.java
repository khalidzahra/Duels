package me.lightlord323.duels.duel;

import java.util.UUID;

/**
 * Created by Khalid on 7/6/21.
 */
public class DPlayer {

    private final UUID uniqueId;
    private int wins, losses, draws;
    private UUID currentChallenger;

    public DPlayer(UUID uniqueId, int wins, int losses, int draws) {
        this.uniqueId = uniqueId;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public void setCurrentChallenger(UUID currentChallenger) {
        this.currentChallenger = currentChallenger;
    }

    public UUID getCurrentChallenger() {
        return currentChallenger;
    }
}
