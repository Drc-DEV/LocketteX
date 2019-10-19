package pro.dracarys.LocketteX.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.utils.Util;

public class BlockPlace implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent e) {
        if (!e.getBlock().getType().equals(Material.HOPPER)) return;
        if (e.getPlayer().isOp())
            return;
        Block block = e.getBlock().getRelative(BlockFace.UP);
        if ((block.getState() instanceof DoubleChest) || block.getState() instanceof Chest) {
            Chest chest = (Chest) block.getState();
            String owner = LocketteXAPI.getChestOwner(chest.getInventory().getHolder());
            if (owner != null && !e.getPlayer().getName().equalsIgnoreCase(owner)) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(Util.color("&7[&4✘&7] &cNon puoi piazzare Hopper sotto una chest protetta!"));
            }
        }
    }

}
