package me.caneva20.CNVDumpingGround.Commands;

import me.caneva20.CNVCore.Command.CommandBase;
import me.caneva20.CNVCore.Command.ICommand;
import me.caneva20.CNVDumpingGround.CNVDumpingGround;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class ReloadCommand extends CommandBase implements ICommand{
    private String command = "reload";
    private String permission = "cnv.dumpingground.command.reload";
    private String usage = "/dumpinggroud reload";
    private List<String> alias = new ArrayList<>();
    private boolean onlyPlayers = false;
    private String description = "Reloads all the config files";

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
        if (args.length != 0) {
            sendUsage(sender, usage, true);
            return;
        }

        CNVDumpingGround plugin = (CNVDumpingGround) javaPlugin;

        plugin.getStorage().reloadConfig();
        plugin.getConfiguration().reloadConfig();
        plugin.getLang().reloadConfig();

        sendMessageInfo(sender, plugin.getLang().getAllConfigFilesWereReloaded(), true);
    }
}























