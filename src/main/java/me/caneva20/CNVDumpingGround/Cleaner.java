package me.caneva20.CNVDumpingGround;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.regions.CuboidRegion;
import me.caneva20.CNVCore.CNVLogger;
import me.caneva20.CNVCore.CNVScheduler.CNVScheduler;
import me.caneva20.CNVDumpingGround.Config.Configuration;
import me.caneva20.CNVDumpingGround.Events.DumpingGroundCleanEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Cleaner {
    private CNVDumpingGround plugin;
    @SuppressWarnings("FieldCanBeLocal")
    private CNVLogger logger;
    private BukkitTask cleanTask;
    private Configuration configuration;

    public Cleaner(CNVDumpingGround plugin) {
        this.plugin = plugin;

        this.logger = plugin.getMainLogger();
        configuration = plugin.getConfiguration();

        startCleaner();
    }

    private void startCleaner () {
        if (configuration.cleaningRunOnStartup()) {
            clear();
        }

        if (configuration.cleaningRunOnSpecificTime()) {
            new CNVScheduler(plugin, configuration.cleaningRunEveryXTimeDelay(), this::clear, configuration.cleaningRunOnSpecificTimeList().toArray(new String[0]));
        }

        if (configuration.cleaningRunEveryXTime()) {
            cleanTask = new BukkitRunnable() {
                @Override
                public void run() {
                    clear();
                }
            }.runTaskTimer(plugin, 0L, configuration.cleaningRunEveryXTimeDelay());
        }
    }

    public void clear() {
        clear(configuration.cleaningDeleteChests());
    }

    public void clear(boolean clearChests) {
        plugin.getMainLogger().infoConsole("Cleaning dumping ground");
        List<Chest> chests = getChests();

        for (Chest chest : chests) {
            chest.getBlockInventory().clear();

            if (clearChests) {
                chest.getBlock().setType(Material.AIR);
            }
        }

        plugin.getServer().getPluginManager().callEvent(new DumpingGroundCleanEvent());
    }

    private List<Chest> getChests () {
        List<Chest> chests = new ArrayList<>();
        CuboidRegion region = plugin.getStorage().getRegion();

        Vector min = region.getMinimumPoint();
        Vector max = region.getMaximumPoint();
        World world = ((BukkitWorld) region.getWorld()).getWorld();

        for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
            for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    Location loc = new Location(world, x, y, z);

                    Block block = loc.getBlock();

                    if (block.getType() == Material.CHEST) {
                        chests.add(((Chest) block.getState()));
                    }
                }
            }
        }

        return chests;
    }

    public void disable () {
        cleanTask.cancel();
    }
}





















