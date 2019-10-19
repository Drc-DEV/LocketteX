package pro.dracarys.LocketteX.listener;

import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.utils.Util;

public class InventoryMoveItem implements Listener {

    @EventHandler
    public void onHopper(InventoryMoveItemEvent e){
        Util.sendConsole(""+e.getDestination().getType()+" "+e.getSource().getType());
        if (!e.getDestination().getType().equals(InventoryType.HOPPER)) return;
        Util.sendConsole("hopper check");
        if (LocketteXAPI.getChestOwner(e.getSource().getHolder()) != null){
            Util.sendConsole("lockette check");
            e.setCancelled(true);
            if (e.getDestination().getHolder() instanceof Hopper){
                Util.sendConsole("destination hopper");
                Hopper hopper = (Hopper) e.getDestination().getHolder();
                hopper.getBlock().setType(Material.AIR);
            }
        }
    }



}
