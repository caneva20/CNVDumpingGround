package me.caneva20.CNVDumpingGround.Config;

import me.caneva20.CNVCore.CNVConfig;
import me.caneva20.CNVDumpingGround.CNVDumpingGround;

@SuppressWarnings("unused")
public class Lang {
    @SuppressWarnings("FieldCanBeLocal")
    private CNVDumpingGround plugin;
    private CNVConfig language;

    public Lang(CNVDumpingGround plugin) {
        this.plugin = plugin;

        language = new CNVConfig(plugin, "Language", "language");
    }

    public void reloadConfig () {
        language.reloadCustomConfig();
    }

    public String getYouHaventSelectedAnArea() {
        return language.getString("YOU_HAVENT_SELECTED_AN_AREA");
    }

    public String getDumpingGroundAreaSuccessfullySaved () {
        return language.getString("DUMPING_GROUND_AREA_SUCCESSFULLY_SAVED");
    }

    public String getDumpingGroundAreaSuccessfullyCleaned () {
        return language.getString("DUMPING_GROUND_AREA_SUCCESSFULLY_CLEANED");
    }

    public String getDumpingGroundAreaSuccessfullyCleanedAndChestsRemoved () {
        return language.getString("DUMPING_GROUND_AREA_SUCCESSFULLY_CLEANED_AND_CHESTS_REMOVED");
    }

    public String getDumpingGroundAreaSuccessfullyCleanedAndChestsKept () {
        return language.getString("DUMPING_GROUND_AREA_SUCCESSFULLY_CLEANED_AND_CHESTS_KEPT");
    }

    public String getItemInHandSuccessfullyDumped() {
        return language.getString("ITEM_IN_HAND_SUCCESSFULLY_DUMPED");
    }

    public String getItemsInInventorySuccessfullyDumped () {
        return language.getString("ITEMS_IN_INVENTORY_SUCCESSFULLY_DUMPED");
    }

    public String getAllConfigFilesWereReloaded () {
        return language.getString("ALL_CONFIG_FILES_WERE_RELOADED");
    }
}






















