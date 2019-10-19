package pro.dracarys.LocketteX.listener;

import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import pro.dracarys.LocketteX.api.LocketteXAPI;

public class InventoryMoveItem implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onHopper(InventoryMoveItemEvent e){
        if (!e.getDestination().getType().equals(InventoryType.HOPPER)) return;
        if (LocketteXAPI.getChestOwner(e.getSource().getHolder()) != null){
            e.setCancelled(true);
            if (e.getDestination().getHolder() instanceof Hopper){
                Hopper hopper = (Hopper) e.getDestination().getHolder();
                hopper.getBlock().setType(Material.AIR);
            }
        }
    }



}
