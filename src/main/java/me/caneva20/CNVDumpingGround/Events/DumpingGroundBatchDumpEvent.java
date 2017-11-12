package me.caneva20.CNVDumpingGround.Events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public class DumpingGroundBatchDumpEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private ItemStack[] dumpedItems;
    private boolean isCanceled;

    public DumpingGroundBatchDumpEvent(ItemStack[] dumpedItems) {
        this.dumpedItems = dumpedItems;
    }

    public ItemStack[] getDumpedItems() {
        return dumpedItems;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return isCanceled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCanceled = b;
    }
}
