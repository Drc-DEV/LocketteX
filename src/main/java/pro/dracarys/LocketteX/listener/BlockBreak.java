package pro.dracarys.LocketteX.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class BlockBreak implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e) {
        if (e.getPlayer().isOp())
            return;
        if (e.getBlock().getType().equals(Material.WALL_SIGN)) {
            Sign s = (Sign) e.getBlock().getState();
            if (s.getLine(0).contains(Util.color("&1[Protect]"))) {
                if (!s.getLine(1).equalsIgnoreCase(e.getPlayer().getName())) {
                    e.getPlayer().sendMessage(Util.color("&7[&4✘&7] &cSolo &e%owner% &cpuò rompere questo cartello!".replace("%owner%", s.getLine(1))));
                    e.setCancelled(true);
                    return;
                } else {
                    return;
                }
            }
        } else if ((e.getBlock().getState() instanceof DoubleChest) || e.getBlock().getState() instanceof Chest) {
            Chest chest = (Chest) e.getBlock().getState();
            String owner = LocketteXAPI.getChestOwner(chest.getInventory().getHolder());
            if (owner != null && !owner.equalsIgnoreCase(e.getPlayer().getName())){
                e.getPlayer().sendMessage(Util.color("&7[&4✘&7] &cQuesta chest è protetta! Solo &e%owner% &cpuò romperla!".replace("%owner%", owner)));
                e.setCancelled(true);
                return;
            }
        }
    }


}
