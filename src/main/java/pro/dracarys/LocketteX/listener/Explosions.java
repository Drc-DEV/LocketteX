package pro.dracarys.LocketteX.listener;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.utils.Util;

import java.util.HashSet;
import java.util.Set;

public class Explosions implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onExplosion(EntityExplodeEvent event) {
        if (!Config.USE_CANCEL_EXPLOSIONS.getOption() || !Util.isEnabledWorld(event.getEntity().getWorld().getName()))
            return;
        Util.debug("explosion");
        Set<Block> supportBlocks = new HashSet<>();
        event.blockList().removeIf(block -> {
            if (LocketteXAPI.isProtected(block.getState())) {
                if (block.getState().getBlockData() instanceof Door && ((Door) block.getState().getBlockData()).getHalf() == Bisected.Half.BOTTOM)
                    supportBlocks.add(block.getRelative(BlockFace.DOWN));
                return true;
            }
            if (!block.getType().name().contains("WALL_SIGN")) return false;
            return ((Sign) block.getState()).getLine(0).contains(Util.color(Config.SIGN_FORMATTED_LINES.getStrings()[0]));
        });
        if (!supportBlocks.isEmpty())
            event.blockList().removeAll(supportBlocks);
    }

}
