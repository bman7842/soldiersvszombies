package me.bman7842.soldiersvszombies.commands;

import me.bman7842.soldiersvszombies.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by brand_000 on 8/2/2015.
 */
public class CommandManager implements CommandExecutor {

    private HashMap<String, SubCommand> cmds;
    public static Permission svszAdmin = new Permission("svsz.Admin");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is only for players you console peasant");
            return false;
        }

        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("svsz")) {
            String sub = args[0];

            if (sub.equalsIgnoreCase("help")) {
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "GAMEMODE " + ChatColor.GREEN + "" + ChatColor.BOLD + "HELP:");
                msgHelp(p);
                return false;
            }

            Vector<String> l = new Vector<String>();
            l.addAll(Arrays.asList(args));
            l.remove(0);
            args = l.toArray(new String[0]);
            try {
                cmds.get(sub).onCommand(p, args);
            } catch (Exception e) {
                e.printStackTrace();
                Messages.sendErrorMessage(p, "Could not find the command '" + args.toString().trim() + "'!");
                return true;
            }
        }

        return false;
    }

    public void msgHelp(Player p) {
        for (SubCommand sc : cmds.values()) {
            p.sendMessage(sc.help(p));
        }
    }

}
