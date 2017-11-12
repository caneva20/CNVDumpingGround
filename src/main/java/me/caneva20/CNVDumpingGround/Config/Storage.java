package me.caneva20.CNVDumpingGround.Config;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegion;
import me.caneva20.CNVCore.CNVConfig;
import me.caneva20.CNVDumpingGround.CNVDumpingGround;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@SuppressWarnings("unused")
public class Storage {
    @SuppressWarnings("FieldCanBeLocal")
    private CNVDumpingGround plugin;
    private CNVConfig storage;

    public Storage(CNVDumpingGround plugin) {
        this.plugin = plugin;

        storage = new CNVConfig(plugin, "storage", "storage");
    }

    public void reloadConfig () {
        storage.reloadCustomConfig();
    }

    public void saveRegion(Selection selection) {
        storage.set("DUMPING_GROUND.WORLD", selection.getWorld().getName());

        Location minimumPoint = selection.getMinimumPoint();
        storage.set("DUMPING_GROUND.MINIMUM_POINT.X", minimumPoint.getBlockX());
        storage.set("DUMPING_GROUND.MINIMUM_POINT.Y", minimumPoint.getBlockY());
        storage.set("DUMPING_GROUND.MINIMUM_POINT.Z", minimumPoint.getBlockZ());

        Location maximumPoint = selection.getMaximumPoint();
        storage.set("DUMPING_GROUND.MAXIMUM_POINT.X", maximumPoint.getBlockX());
        storage.set("DUMPING_GROUND.MAXIMUM_POINT.Y", maximumPoint.getBlockY());
        storage.set("DUMPING_GROUND.MAXIMUM_POINT.Z", maximumPoint.getBlockZ());
    }

    public CuboidRegion getRegion () {
        if (!storage.contains("DUMPING_GROUND.WORLD")) {
            return null;
        }

        String worldName = storage.getString("DUMPING_GROUND.WORLD");
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            return null;
        }

        Vector v1 = new Vector(
                storage.getDouble("DUMPING_GROUND.MINIMUM_POINT.X"),
                storage.getDouble("DUMPING_GROUND.MINIMUM_POINT.Y"),
                storage.getDouble("DUMPING_GROUND.MINIMUM_POINT.Z")
        );

        Vector v2 = new Vector(
                storage.getDouble("DUMPING_GROUND.MAXIMUM_POINT.X"),
                storage.getDouble("DUMPING_GROUND.MAXIMUM_POINT.Y"),
                storage.getDouble("DUMPING_GROUND.MAXIMUM_POINT.Z")
        );

//        plugin.getMainLogger().debug("-----------[ REGION ]-------------");
//        plugin.getMainLogger().debug("V1: " + v1);
//        plugin.getMainLogger().debug("V2: " + v2);

        return new CuboidRegion(new BukkitWorld(world), v1, v2);
    }
}





















