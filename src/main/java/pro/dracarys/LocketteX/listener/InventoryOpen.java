package pro.dracarys.LocketteX.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import pro.dracarys.LocketteX.utils.Util;

public class InventoryOpen implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInvOpen(InventoryOpenEvent e) {
        if (e.getInventory().getHolder() == null || !(e.getInventory().getHolder() instanceof Chest)) {
            return;
        }
        Chest chest = (Chest) e.getInventory().getHolder();
        for (Block block : Util.getBlocks(chest.getBlock(), 1)) {
            if (!block.getType().equals(Material.WALL_SIGN)) continue;
            Sign s = (Sign) block.getState();
            org.bukkit.material.Sign sd = (org.bukkit.material.Sign) s.getData();
            if (chest.getBlock().getLocation().equals(block.getRelative(sd.getAttachedFace()).getLocation())) {
                if (s.getLine(0).contains(Util.color("&1[Protect]"))){
                    if (!s.getLine(1).equalsIgnoreCase(e.getPlayer().getName())){
                        e.getPlayer().sendMessage(Util.color("&7[&4✘&7] &cQuesta chest è protetta! Solo &e%owner% &cpuò aprirla!".replace("%owner%",s.getLine(1))));
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
