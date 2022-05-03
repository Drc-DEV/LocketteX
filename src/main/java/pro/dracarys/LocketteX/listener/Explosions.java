package pro.dracarys.LocketteX.listener;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Openable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.InventoryHolder;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class Explosions implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onExplosion(EntityExplodeEvent event) {
        if (!Config.USE_CANCEL_EXPLOSIONS.getOption() || !Util.isEnabledWorld(event.getEntity().getWorld().getName()))
            return;
        List<Block> toRemove = new ArrayList<>();
        for (Block block : event.blockList()) {
            if ((block.getState() instanceof InventoryHolder || block.getState().getBlockData() instanceof Openable) && LocketteXAPI.isProtected(block.getState())) {
                toRemove.add(block);
            }
            if (!block.getType().name().contains("WALL_SIGN")) continue;
            Sign s = (Sign) block.getState();
            if (s.getLine(0).contains(Util.color(Config.SIGN_FORMATTED_LINES.getStrings()[0]))) {
                toRemove.add(block);
            }
        }
        event.blockList().removeAll(toRemove);
    }

}
