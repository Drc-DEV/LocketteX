package pro.dracarys.LocketteX.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pro.dracarys.LocketteX.utils.Util;

public class BlockBreak implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onSignBreak(BlockBreakEvent e) {
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
        } else if ((e.getBlock().getType().equals(Material.CHEST)) || e.getBlock().getType().equals(Material.TRAPPED_CHEST)){
            for (Block block : Util.getBlocks(e.getBlock(), 1)) {
                if (!block.getType().equals(Material.WALL_SIGN)) continue;
                Sign s = (Sign) block.getState();
                org.bukkit.material.Sign sd = (org.bukkit.material.Sign) s.getData();
                if (e.getBlock().getLocation().equals(block.getRelative(sd.getAttachedFace()).getLocation())) {
                    if (s.getLine(0).contains(Util.color("&1[Protect]"))){
                        if (!s.getLine(1).equalsIgnoreCase(e.getPlayer().getName())){
                            e.getPlayer().sendMessage(Util.color("&7[&4✘&7] &cQuesta chest è protetta! Solo &e%owner% &cpuò romperla!".replace("%owner%",s.getLine(1))));
                            e.setCancelled(true);
                            return;
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }


}
