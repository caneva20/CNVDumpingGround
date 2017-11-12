package me.caneva20.CNVDumpingGround;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.caneva20.CNVCore.CNVCore;
import me.caneva20.CNVCore.CNVLogger;
import me.caneva20.CNVCore.Command.CNVCommands;
import me.caneva20.CNVDumpingGround.Commands.*;
import me.caneva20.CNVDumpingGround.Config.Configuration;
import me.caneva20.CNVDumpingGround.Config.Lang;
import me.caneva20.CNVDumpingGround.Config.Storage;
import me.caneva20.CNVDumpingGround.Listeners.ItemDespawnListener;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class CNVDumpingGround extends JavaPlugin {
    private CNVLogger logger;
    @SuppressWarnings("FieldCanBeLocal")
    private CNVCore cnvCore;
    private WorldEditPlugin worldEdit;
    private CNVCommands commands;
    private CNVCommands mainCommands;
    private Dumper dumper;
    private Cleaner cleaner;

    //Configs
    private Configuration configuration;
    private Storage storage;
    private Lang lang;

    @Override
    public void onLoad() {
        logger = new CNVLogger(this);
    }

    @Override
    public void onEnable() {
        logger.setLoggerTag("&f[&5CNV&6DumpingGround&f] ");
        logger.infoConsole("Initializing setup");

//        commands = new CNVCommands(this, logger);
        configuration = new Configuration(this);
        storage = new Storage(this);
        lang = new Lang(this);

        if (!setupCore() || !setupWorldEdit()) {
            logger.infoConsole("Something went wrong while enabling, see the log for more information");
            logger.infoConsole("Disabling plugin...");

            getPluginLoader().disablePlugin(this);

            return;
        }

        dumper = new Dumper(this);

        if (configuration.cleaningEnabled()) {
            cleaner = new Cleaner(this);
        }

        setupListeners();
        setupCommands();

        logger.infoConsole("Enabled!");
    }

    @Override
    public void onDisable() {
        logger.infoConsole("Disabled!");
    }

    private boolean setupCore () {
        JavaPlugin cnvCorePlugin = (JavaPlugin) getServer().getPluginManager().getPlugin("CNVCore");

        if (!(cnvCorePlugin instanceof CNVCore)) {
            logger.warnConsole("<par>CNVCore</par> not found!");
            return false;
        }

        cnvCore = ((CNVCore) cnvCorePlugin);

        if (!cnvCore.getConfiguration().getItemDespawnEventEnabled()) {
            logger.warnConsole("<par>Item despawn event</par> from <par>CNVCore</par> is disabled!");
            logger.warnConsole("You must enabled it in order to this plugin work");

            return false;
        }

        return true;
    }

    private boolean setupWorldEdit () {
        JavaPlugin worldEditPlugin = ((JavaPlugin) getServer().getPluginManager().getPlugin("WorldEdit"));

        if (!(worldEditPlugin instanceof WorldEditPlugin)) {
            logger.warnConsole("<par>WorldEdit</par> not found!");

            return false;
        }

        worldEdit = ((WorldEditPlugin) worldEditPlugin);

        return true;
    }

    private void setupListeners () {
        logger.infoConsole("Registering listeners...");

        getServer().getPluginManager().registerEvents(new ItemDespawnListener(this), this);

        logger.infoConsole("All listeners registered!");
    }

    private void setupCommands () {
        logger.infoConsole("Registering commands...");

        commands = new CNVCommands(this, logger, "dumpingground");
        mainCommands = new CNVCommands(this, logger, "cnvdumpingground");

        commands.registerCommand(new SetAreaCommand());
        commands.registerCommand(new ClearCommand());
        commands.registerCommand(new DumpCommand());
        commands.registerCommand(new ReloadCommand());

        mainCommands.registerCommand(new InfoCommand());

        logger.infoConsole("All commands registered!");
    }

    public World getMainWorld () {
        return getServer().getWorlds().get(0);
    }

    public CNVLogger getMainLogger() {
        return logger;
    }

    public WorldEditPlugin getWorldEdit () {
        return worldEdit;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Storage getStorage() {
        return storage;
    }

    public Lang getLang() {
        return lang;
    }

    public Dumper getMainDumper() {
        return dumper;
    }

    public Cleaner getMainCleaner() {
        return cleaner;
    }
}























