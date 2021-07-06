package me.lightlord323.duels.duel;

import org.bukkit.inventory.ItemStack;

/**
 * Created by Khalid on 7/6/21.
 */
public class DPlayerInventory {

    private ItemStack[] contents, armorContents;

    public DPlayerInventory(ItemStack[] contents, ItemStack[] armorContents) {
        this.contents = contents;
        this.armorContents = armorContents;
    }

    public ItemStack[] getContents() {
        return contents;
    }

    public ItemStack[] getArmorContents() {
        return armorContents;
    }
}
