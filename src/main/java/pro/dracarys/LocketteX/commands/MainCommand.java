package pro.dracarys.LocketteX.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.utils.Message;
import pro.dracarys.LocketteX.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements TabExecutor {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        List<String> lista = new ArrayList<String>();
        if (args.length == 0) {
            lista.add("protect");
            lista.add("lockettex");
        }
        if (args.length == 1) {
            lista.add("reload");
        }
        return lista;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission("lockettex.help")) {
            sender.sendMessage(Message.GENERAL_NOPERMISSION.getMessage());
            return true;
        }
        // Help Command
        if (args.length == 0) {
            sender.sendMessage(Message.CMD_MAIN_HEADER.getMessage());
            sender.sendMessage(Util.color(" &e/protect &6reload &7>> &fRicarica Config"));
            sender.sendMessage(Message.CMD_MAIN_FOOTER.getMessage());
            return true;
        }
        if (args.length == 1) {
            // Reload Command
            if (args[0].equalsIgnoreCase("reload")) {
                LocketteX.getInstance().loadConfig();
                sender.sendMessage(Message.CMD_RELOAD_SUCCESS.getMessage());
                return true;
            }
        }
        // Nothing matched, send command usage.
        sender.sendMessage(Message.CMD_USAGE.getMessage());
        return true;
    }
}