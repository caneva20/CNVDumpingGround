package me.caneva20.CNVDumpingGround.Utils;

import org.bukkit.util.Vector;

public class Util {
    public static Vector castWEVector (com.sk89q.worldedit.Vector weVector) {
        return new Vector(weVector.getX(), weVector.getY(), weVector.getZ());
    }
}
