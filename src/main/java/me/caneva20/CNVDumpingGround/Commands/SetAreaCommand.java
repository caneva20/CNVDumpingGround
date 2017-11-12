package me.caneva20.CNVDumpingGround.Commands;

import com.sk89q.worldedit.bukkit.selections.Selection;
import me.caneva20.CNVCore.Command.CommandBase;
import me.caneva20.CNVCore.Command.ICommand;
import me.caneva20.CNVDumpingGround.CNVDumpingGround;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class SetAreaCommand extends CommandBase implements ICommand {
    private String command = "setarea";
    private String permission = "cnv.dumpingground.set.area";
    private String usage = "/dumpinggroud setarea";
    private List<String> alias = new ArrayList<>();
    private boolean onlyPlayers = true;
    private String description = "Set the dumping area where chests will be created";

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
        CNVDumpingGround plugin = (CNVDumpingGround) javaPlugin;

        if (args.length != 0) {
            sendUsage(sender, getUsage(), true);
            return;
        }

        Player player = ((Player) sender);

        Selection selection = getSelection(player, plugin);

        if (selection == null) {
            sendMessageWarn(sender, plugin.getLang().getYouHaventSelectedAnArea(), true);
            return;
        }

        plugin.getStorage().saveRegion(selection);

        sendMessageInfo(sender, plugin.getLang().getDumpingGroundAreaSuccessfullySaved(), true);
    }

    private Selection getSelection (Player player, CNVDumpingGround plugin) {
        return plugin.getWorldEdit().getSelection(player);
    }
}















