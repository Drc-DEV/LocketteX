package pro.dracarys.LocketteX.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.Message;
import pro.dracarys.LocketteX.data.SignUser;
import pro.dracarys.LocketteX.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements TabExecutor {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        List<String> lista = new ArrayList<>();
        if (args.length == 0) {
            lista.add("protect");
            lista.add("lockettex");
        }
        if (args.length == 1) {
            if (sender.hasPermission(Config.PERMISSION_ADMIN.getString()))
                lista.add("reload");
            lista.add("add");
            lista.add("remove");
        }
        return lista;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission(Config.PERMISSION_CREATION.getString())) {
            sender.sendMessage(Message.PREFIX.getMessage() + Message.GENERAL_NOPERMISSION.getMessage());
            return true;
        }
        // Help Command
        if (args.length == 0) {
            sender.sendMessage(Message.CMD_MAIN_HEADER.getMessage());
            if (sender.hasPermission(Config.PERMISSION_ADMIN.getString()))
                sender.sendMessage(Util.color(" &e/protect &6reload &7» &f" + Message.CMD_RELOAD_DESC.getMessage()));
            sender.sendMessage(Util.color(" &e/protect &6(add|remove) <player> &7» &f" + Message.CMD_WHITELIST_DESC.getMessage()));
            sender.sendMessage(Message.CMD_MAIN_FOOTER.getMessage());
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission(Config.PERMISSION_ADMIN.getString())) {
                    sender.sendMessage(Message.PREFIX.getMessage() + Message.GENERAL_NOPERMISSION.getMessage());
                    return true;
                }
                LocketteX.getInstance().loadConfig();
                sender.sendMessage(Message.PREFIX.getMessage() + Message.CMD_RELOAD_SUCCESS.getMessage());
                return true;
            }
        } else if (args.length == 2) {
            if ((args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) && sender instanceof Player) {
                Player p = (Player) sender;
                Block targetBlock = p.getTargetBlock(null, 4);
                if (!targetBlock.getType().name().contains("WALL_SIGN")) {
                    sender.sendMessage(Message.PREFIX.getMessage() + Message.CMD_WHITELIST_NOSIGN.getMessage());
                    return true;
                }
                SignUser owner = LocketteXAPI.getSignOwner(targetBlock);
                if (owner == null || !owner.getUniqueId().equals(p.getUniqueId())) {
                    sender.sendMessage(Message.PREFIX.getMessage() + Message.CMD_WHITELIST_NOSIGN.getMessage());
                    return true;
                }
                List<String> targets = new ArrayList<>();
                for (String name : args[1].split(",")) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                    boolean done;
                    if (args[0].equalsIgnoreCase("add"))
                        done = LocketteXAPI.giveAccess(target, targetBlock);
                    else
                        done = LocketteXAPI.removeAccess(target, targetBlock);
                    if (done) targets.add(target.getName());
                }
                if (args[0].equalsIgnoreCase("add"))
                    sender.sendMessage(Message.PREFIX.getMessage() + (targets.isEmpty() ? Message.CMD_ADD_FAIL.getMessage() : Message.CMD_ADD_SUCCESS.getMessage().replace("%player%", String.join(", ", targets))));
                else
                    sender.sendMessage(Message.PREFIX.getMessage() + (targets.isEmpty() ? Message.CMD_REMOVE_FAIL.getMessage() : Message.CMD_REMOVE_SUCCESS.getMessage().replace("%player%", String.join(", ", targets))));
                LocketteX.getInstance().saveData();
                return true;
            }
        }
        // Nothing matched, send command usage.
        sender.sendMessage(Message.PREFIX.getMessage() + Message.CMD_USAGE.getMessage());
        return true;
    }
}