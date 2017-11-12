package me.caneva20.CNVDumpingGround.Commands;

import me.caneva20.CNVCore.Command.CommandBase;
import me.caneva20.CNVCore.Command.ICommand;
import me.caneva20.CNVDumpingGround.CNVDumpingGround;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class DumpCommand extends CommandBase implements ICommand{
    private String command = "dump";
    private String permission = "cnv.dumpingground.command.dump";
    private String usage = "/dumpinggroud dump [hand|inv|inventory]";
    private List<String> alias = new ArrayList<>();
    private boolean onlyPlayers = true;
    private String description = "Dumps the item in hand or all your inventory";

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

        if (args.length > 0
                && !args[0].equalsIgnoreCase("inv")
                && !args[0].equalsIgnoreCase("inventory")
                && !args[0].equalsIgnoreCase("hand")) {
            sendUsage(sender, usage, true);
            return;
        }

        CNVDumpingGround plugin = (CNVDumpingGround) javaPlugin;
        Player player = (Player) sender;

        boolean dumpInv = args.length > 0 && !args[0].equalsIgnoreCase("hand");

        if (dumpInv) {
            List<ItemStack> items = new ArrayList<>();
            for (ItemStack itemStack : player.getInventory()) {
                if (itemStack != null) {
                    items.add(itemStack);
                }
            }

            plugin.getMainDumper().dump(items.toArray(new ItemStack[0]));

            player.getInventory().clear();

            sendMessageInfo(sender, plugin.getLang().getItemsInInventorySuccessfullyDumped(), true);
        } else {
            plugin.getMainDumper().dump(player.getItemInHand());
            player.setItemInHand(null);

            sendMessageInfo(sender, plugin.getLang().getItemInHandSuccessfullyDumped(), true);
        }
    }
}























