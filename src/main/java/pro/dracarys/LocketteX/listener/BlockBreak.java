package pro.dracarys.LocketteX.listener;

import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.utils.Config;
import pro.dracarys.LocketteX.utils.Message;
import pro.dracarys.LocketteX.utils.Util;

import java.util.Arrays;

public class BlockBreak implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e) {
        if (Arrays.stream(Config.ENABLED_WORLDS.getStrings()).noneMatch(e.getPlayer().getWorld().getName()::equalsIgnoreCase)) {
            return;
        }
        if (e.getPlayer().isOp() || (Config.LEADER_CAN_BREAK.getOption() && Util.getLeaderAt(e.getBlock().getLocation()).equalsIgnoreCase(e.getPlayer().getName())))
            return;
        if (e.getBlock().getType().name().contains("WALL_SIGN")) {
            Sign s = (Sign) e.getBlock().getState();
            if (s.getLine(0).contains(Util.color(Config.SIGN_FORMATTED_LINES.getStrings()[0]))) {
                if (!s.getLine(1).equalsIgnoreCase(e.getPlayer().getName())) {
                    e.getPlayer().sendMessage(Message.SIGN_BREAK_DENIED.getMessage().replace("%owner%", s.getLine(1)));
                    e.setCancelled(true);
                    return;
                } else {
                    return;
                }
            }
        } else if ((e.getBlock().getState() instanceof DoubleChest) || e.getBlock().getState() instanceof Chest) {
            Chest chest = (Chest) e.getBlock().getState();
            String owner = LocketteXAPI.getChestOwner(chest.getInventory().getHolder());
            if (owner != null && !owner.equalsIgnoreCase(e.getPlayer().getName())) {
                e.getPlayer().sendMessage(Message.CHEST_BREAK_DENIED.getMessage().replace("%owner%", owner));
                e.setCancelled(true);
                return;
            }
        }
    }


}
