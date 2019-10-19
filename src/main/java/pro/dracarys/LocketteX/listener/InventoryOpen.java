package pro.dracarys.LocketteX.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class InventoryOpen implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInvOpen(InventoryOpenEvent e) {
        if (!(e.getPlayer() instanceof Player) || e.getInventory().getHolder() == null || !(e.getInventory().getHolder() instanceof Chest) && !(e.getInventory().getHolder() instanceof DoubleChest)) {
            return;
        }
        Player p = (Player) e.getPlayer();
        String owner = LocketteXAPI.getChestOwner(e.getInventory().getHolder());
        if (owner != null && !p.getName().equalsIgnoreCase(owner)) {
            p.sendMessage(Util.color("&7[&4✘&7] &cQuesta chest è protetta! Solo &e%owner% &cpuò aprirla!".replace("%owner%", owner)));
            e.setCancelled(true);
        }
    }
}