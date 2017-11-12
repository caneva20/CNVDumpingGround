package me.caneva20.CNVDumpingGround.Listeners;

import me.caneva20.CNVCore.CNVLogger;
import me.caneva20.CNVCore.Events.CustomEvents.BatchItemDespawnEvent;
import me.caneva20.CNVDumpingGround.CNVDumpingGround;
import me.caneva20.CNVDumpingGround.Dumper;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@SuppressWarnings("unused")
public class ItemDespawnListener implements Listener {
    private CNVDumpingGround plugin;
    @SuppressWarnings("FieldCanBeLocal")
    private CNVLogger logger;
    private Dumper dumper;

    public ItemDespawnListener(CNVDumpingGround plugin) {
        this.plugin = plugin;

        logger = plugin.getMainLogger();
        dumper = plugin.getMainDumper();
    }

    @EventHandler
    private void onItemDespawn (BatchItemDespawnEvent e) {
        List<ItemStack> items = e.getItems();

        if (items.size() <= 0) {
            return;
        }

        dumper.dump(items.toArray(new ItemStack[0]));

        BlockState blockAt = plugin.getMainWorld().getBlockAt(new Location(plugin.getMainWorld(), 0D, 5D, 0D)).getState();

        if (blockAt instanceof Chest) {
            Chest chest = (Chest)blockAt;
            Inventory inventory = chest.getBlockInventory();

            for (ItemStack item : items) {
                inventory.addItem(item);
            }
        }
    }
}





















