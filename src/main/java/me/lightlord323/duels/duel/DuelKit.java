package me.lightlord323.duels.duel;

import me.lightlord323.duels.Duels;
import me.lightlord323.duels.handler.Handler;
import me.lightlord323.duels.handler.Loadable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khalid on 7/6/21.
 */
public class DuelKit extends Handler implements Loadable {


    private static List<ItemStack> contents, armorContents;

    public DuelKit(Duels plugin) {
        super(plugin);
    }

    @Override
    public void onLoad() {
        contents = new ArrayList<>();
        armorContents = new ArrayList<>();
        getPlugin().getSettingsFile().getConfig().getStringList("kit.inventory").forEach(i -> contents.add(new ItemStack(Material.valueOf(i))));
        getPlugin().getSettingsFile().getConfig().getStringList("kit.armor").forEach(i -> armorContents.add(new ItemStack(Material.valueOf(i))));
    }

    @Override
    public void onUnload() {

    }

    public static ItemStack[] getContents() {
        return contents.toArray(new ItemStack[]{});
    }

    public static ItemStack[] getArmorContents() {
        return armorContents.toArray(new ItemStack[]{});
    }
}
