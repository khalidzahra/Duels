package me.lightlord323.duels.handler.concrete;

import me.lightlord323.duels.Duels;
import me.lightlord323.duels.cmd.DuelCommand;
import me.lightlord323.duels.duel.DuelKit;
import me.lightlord323.duels.handler.Handler;
import me.lightlord323.duels.handler.Loadable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Khalid on 7/6/21.
 */
public class HandlerManager extends Handler implements Loadable {

    private DPlayerHandler dPlayerHandler;
    private DuelHandler duelHandler;

    public HandlerManager(Duels plugin) {
        super(plugin);
    }

    private List<Handler> handlers = Arrays.asList(
            new DeathHandler(getPlugin()),
            dPlayerHandler = new DPlayerHandler(getPlugin()),
            duelHandler = new DuelHandler(getPlugin()),
            new JoinHandler(getPlugin()),
            new LeaveHandler(getPlugin()),
            new DuelCommand(getPlugin()),
            new DuelKit(getPlugin())
    );

    @Override
    public void onLoad() {
        handlers.forEach(handler -> {
            if (handler instanceof Loadable)
                ((Loadable) handler).onLoad();
        });
    }

    @Override
    public void onUnload() {
        handlers.forEach(handler -> {
            if (handler instanceof Loadable)
                ((Loadable) handler).onUnload();
        });
    }

    public DPlayerHandler getDPlayerHandler() {
        return dPlayerHandler;
    }

    public DuelHandler getDuelHandler() {
        return duelHandler;
    }
}
