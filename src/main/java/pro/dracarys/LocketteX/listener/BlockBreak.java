package pro.dracarys.LocketteX.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pro.dracarys.LocketteX.api.LocketteXAPI;

public class BlockBreak implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e) {
        if (!LocketteXAPI.canBreak(e.getPlayer(), e.getBlock())) e.setCancelled(true);
    }

}
