package me.caneva20.CNVDumpingGround.Config;

import me.caneva20.CNVCore.CNVConfig;
import me.caneva20.CNVDumpingGround.CNVDumpingGround;

import java.util.List;

@SuppressWarnings("unused")
public class Configuration {
    @SuppressWarnings("FieldCanBeLocal")
    private CNVDumpingGround plugin;

    private CNVConfig config;

    public Configuration(CNVDumpingGround plugin) {
        this.plugin = plugin;

        config = new CNVConfig(plugin, "Config", "config");
    }

    public void reloadConfig () {
        config.reloadCustomConfig();
    }

    public boolean chestsCanBeGenerated () {
        return config.getBoolean("CHESTS.CHESTS_CAN_BE_GENERATED");
    }

    public boolean chestsCanBeDouble () {
        return config.getBoolean("CHESTS.CHESTS_CAN_BE_DOUBLE");
    }

    public boolean chestsCanBeFloating () {
        return config.getBoolean("CHESTS.FLOATING.CHESTS_CAN_BE_FLOATING");
    }

    public int chestsFloatingMaxHeight () {
        return config.getInt("CHESTS.FLOATING.CHEST_FLOATING_MAX_HEIGHT");
    }

    public boolean otherChestsCountAsValidGround () {
        return config.getBoolean("CHESTS.FLOATING.OTHER_CHESTS_COUNT_AS_VALID_GROUND");
    }

    public int chestsMaxFindAttempts () {
        return config.getInt("CHESTS.CHESTS_MAX_FIND_ATTEMPTS");
    }

    public boolean fullChestsCanBeFound () {
        return config.getBoolean("CHESTS.ITEMS.COMPLETELY_FULL_CHESTS_CAN_FOUND");
    }

    public boolean newItemsCanBePlacedOnOccupiedSlots () {
        return config.getBoolean("CHESTS.ITEMS.NEW_ITEMS_CAN_BE_PLACED_ON_OCCUPIED_SLOTS");
    }

    public boolean placeNewItemsOnRandomSlot () {
        return config.getBoolean("CHESTS.ITEMS.PLACE_NEW_ITEMS_ON_RANDOM_SLOT");
    }

    public boolean deleteUndumpedItems () {
        return config.getBoolean("CHESTS.ITEMS.DELETE_UNDUMPED_ITEMS");
    }

    //Section cleaning
    public boolean cleaningEnabled () {
        return config.getBoolean("CLEANING.ENABLED");
    }

    public long cleaningRunDelay () {
        return config.getLong("CLEANING.RUN_DELAY");
    }

    public boolean cleaningRunOnStartup() {
        return config.getBoolean("CLEANING.RUN_ON_STARTUP");
    }

    public boolean cleaningRunOnSpecificTime () {
        return config.getBoolean("CLEANING.RUN_ON_SPECIFIC_TIME.ENABLED");
    }

    public List<String> cleaningRunOnSpecificTimeList () {
        return config.getStringList("CLEANING.RUN_ON_SPECIFIC_TIME.TIME_LIST");
    }

    public boolean cleaningRunEveryXTime () {
        return config.getBoolean("CLEANING.RUN_EVERY_X_TIME.ENABLED");
    }

    public long cleaningRunEveryXTimeDelay () {
        return config.getLong("CLEANING.RUN_EVERY_X_TIME.DELAY");
    }

    public boolean cleaningDeleteChests () {
        return config.getBoolean("CLEANING.DELETE_CHESTS");
    }
}





















