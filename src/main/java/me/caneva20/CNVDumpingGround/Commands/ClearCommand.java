package me.caneva20.CNVDumpingGround.Commands;

import me.caneva20.CNVCore.Command.CommandBase;
import me.caneva20.CNVCore.Command.ICommand;
import me.caneva20.CNVDumpingGround.CNVDumpingGround;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class ClearCommand extends CommandBase implements ICommand{
    private String command = "clear";
    private String permission = "cnv.dumpingground.command.clear";
    private String usage = "/dumpinggroud clear <delete chests>";
    private List<String> alias = new ArrayList<>();
    private boolean onlyPlayers = false;
    private String description = "Cleans the dumping ground area";

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public boolean getOnlyPlayers() {
        return onlyPlayers;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public List<String> getAlias() {
        return alias;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args, JavaPlugin javaPlugin) {
        if (args.length > 1) {
            sendUsage(sender, usage, true);
            return;
        }

        if (args.length == 1) {
            if (!args[0].equalsIgnoreCase("true") && !args[0].equalsIgnoreCase("false")) {
                sendUsage(sender, usage, true);
                return;
            }
        }

        CNVDumpingGround plugin = ((CNVDumpingGround) javaPlugin);

        if (args.length == 0) {
            plugin.getMainCleaner().clear();
            sendMessageInfo(sender, plugin.getLang().getDumpingGroundAreaSuccessfullyCleaned(), true);
            return;
        }

        boolean clearChests = Boolean.parseBoolean(args[0]);
        plugin.getMainCleaner().clear(clearChests);

        if (clearChests) {
            sendMessageInfo(sender, plugin.getLang().getDumpingGroundAreaSuccessfullyCleanedAndChestsRemoved(), true);
        } else {
            sendMessageInfo(sender, plugin.getLang().getDumpingGroundAreaSuccessfullyCleanedAndChestsKept(), true);
        }
    }
}
















