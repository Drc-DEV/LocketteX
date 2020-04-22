package pro.dracarys.LocketteX.listener;

import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.Message;
import pro.dracarys.LocketteX.utils.ClaimUtil;
import pro.dracarys.LocketteX.utils.Util;

public class BlockBreak implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e) {
        if (!Util.isEnabledWorld(e.getPlayer().getWorld().getName())) {
            return;
        }
        if (e.getPlayer().isOp() || (Config.LEADER_CAN_BREAK.getOption() && ClaimUtil.getLeaderAt(e.getBlock().getLocation()).equalsIgnoreCase(e.getPlayer().getName())))
            return;
        if (e.getBlock().getType().name().contains("WALL_SIGN")) {
            Sign s = (Sign) e.getBlock().getState();
            if (s.getLine(0).contains(Util.color(Config.SIGN_FORMATTED_LINES.getStrings()[0]))) {
                if (!s.getLine(2).equalsIgnoreCase(e.getPlayer().getName())) {
                    e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.SIGN_BREAK_DENIED.getMessage().replace("%owner%", s.getLine(2)));
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
                e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.CHEST_BREAK_DENIED.getMessage().replace("%owner%", owner));
                e.setCancelled(true);
                return;
            }
        }
    }


}
