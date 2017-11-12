package me.caneva20.CNVDumpingGround;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.regions.CuboidRegion;
import me.caneva20.CNVCore.CNVLogger;
import me.caneva20.CNVCore.Util.Random;
import me.caneva20.CNVCore.Util.Util;
import me.caneva20.CNVDumpingGround.Config.Configuration;
import me.caneva20.CNVDumpingGround.Config.Storage;
import me.caneva20.CNVDumpingGround.Events.DumpingGroundBatchDumpEvent;
import me.caneva20.CNVDumpingGround.Events.DumpingGroundDumpEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Dumper {
    private CNVDumpingGround plugin;
    private Storage storage;
    private Configuration configuration;
    @SuppressWarnings("FieldCanBeLocal")
    private CNVLogger logger;

    private List<ItemStack> undumpedItems = new ArrayList<>();

    public Dumper(CNVDumpingGround plugin) {
        this.plugin = plugin;

        storage = plugin.getStorage();
        configuration = plugin.getConfiguration();
        logger = plugin.getMainLogger();
    }

    public void dump (ItemStack[] items) {
        DumpingGroundBatchDumpEvent event = new DumpingGroundBatchDumpEvent(items);
        plugin.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        for (ItemStack item : items) {
            dump(item);
        }
    }

    public void dump (ItemStack item) {
        DumpingGroundDumpEvent event = new DumpingGroundDumpEvent(item);
        plugin.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        int leftAttempts = configuration.chestsMaxFindAttempts();
        CuboidRegion region = storage.getRegion();
        Block blockAt;

        do {
            Vector v1 = me.caneva20.CNVDumpingGround.Utils.Util.castWEVector(region.getMinimumPoint());
            Vector v2 = me.caneva20.CNVDumpingGround.Utils.Util.castWEVector(region.getMaximumPoint());

            Vector position = Random.randomVector(v1, v2);
            World world = ((BukkitWorld) region.getWorld()).getWorld();

            Location location = new Location(world, position.getX(), position.getY(), position.getZ());
            blockAt = location.getBlock();

            if (blockAt.getType() != Material.CHEST) {
                if (configuration.chestsCanBeGenerated()) {
                    Block[] chestsAround = Util.getBlocksAround(blockAt.getLocation(), Material.CHEST, true);

                    if (chestsAround.length == 0) {
                        blockAt = generateChest(location);
                    } else if (chestsAround.length == 1 && configuration.chestsCanBeDouble()) {
                        if (Util.getBlocksAround(chestsAround[0].getLocation(), Material.CHEST, true).length == 0) {
                            blockAt = generateChest(location);
                        }
                    }
                }
            } else if (!configuration.fullChestsCanBeFound()) {
                Chest chest = (Chest) blockAt.getState();
                if (Util.getEmptySlots(chest.getBlockInventory()).length == 0) {
                    blockAt = null;
                }
            }
        } while ((blockAt == null || blockAt.getType() != Material.CHEST) && leftAttempts-- > 0);

//        logger.debug("Attempts: " + (configuration.chestsMaxFindAttempts() - leftAttempts));

        if (blockAt == null) {
            return;
        }

        if (blockAt.getType() != Material.CHEST) {
            //We couldn't get a chest :(
            //Save the item to be dumped in the next dumping
            if (!configuration.deleteUndumpedItems()) {
                undumpedItems.add(item);
            }

            return;
        }

        undumpedItems.add(0, item);

        Chest chest = ((Chest) blockAt.getState());

        Integer[] emptySlots = Util.getEmptySlots(chest.getBlockInventory());

        if (!configuration.newItemsCanBePlacedOnOccupiedSlots() && emptySlots.length == 0) {
            // We can't place on an occupied slot and do not have an empty slot, so, we save the item for the next time
            return;
        }

        for (ItemStack itemStack : new ArrayList<>(undumpedItems)) {
            int slot;
            if (configuration.placeNewItemsOnRandomSlot()) {
                if (configuration.newItemsCanBePlacedOnOccupiedSlots()) {
                    slot = Random.range(0, chest.getBlockInventory().getSize());
                } else {
                    slot = emptySlots[Random.range(0, emptySlots.length)];
                }
            } else {
                if (configuration.newItemsCanBePlacedOnOccupiedSlots()) {
                    slot = 0;
                } else {
                    slot = emptySlots[0];
                }
            }

            chest.getBlockInventory().setItem(slot, item);
            undumpedItems.remove(item);
        }
    }

    private Block generateChest (Location location) {
        if (location.getBlock().getType() != Material.AIR) {
            return null;
        }

        if (configuration.chestsCanBeFloating()) {
            if (configuration.chestsFloatingMaxHeight() > 0) {
                boolean isSuitable = false;

                for (int i = 0; i < configuration.chestsFloatingMaxHeight(); i++) {
                    Material nthBlockBellow = Util.getNthBlockBellow(location, i + 1).getType();

                    if (configuration.otherChestsCountAsValidGround()) {
                        if (nthBlockBellow != Material.AIR) {
                            isSuitable = true;
                            break;
                        }
                    } else {
                        if (nthBlockBellow != Material.AIR && nthBlockBellow != Material.CHEST && nthBlockBellow != Material.TRAPPED_CHEST) {
                            isSuitable = true;
                            break;
                        }
                    }
                }

                if (isSuitable) {
                    location.getBlock().setType(Material.CHEST);
                    return location.getBlock();
                } else {
                    return null;
                }
            } else {
                location.getBlock().setType(Material.CHEST);
                return location.getBlock();
            }
        } else {
            Material bellow = Util.getBlockBellow(location).getType();

            if (configuration.otherChestsCountAsValidGround()) {
                if (bellow != Material.AIR) {
                    location.getBlock().setType(Material.CHEST);
                    return location.getBlock();
                } else {
                    return null;
                }
            } else if (bellow != Material.CHEST && bellow != Material.AIR) {
                location.getBlock().setType(Material.CHEST);
                return location.getBlock();
            } else {
                return null;
            }
        }
    }

    public void disable () {
        undumpedItems = new ArrayList<>();
    }
}













