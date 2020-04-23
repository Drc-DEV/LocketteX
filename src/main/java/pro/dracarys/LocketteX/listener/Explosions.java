package pro.dracarys.LocketteX.listener;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.utils.Util;

import java.util.List;

public class Explosions implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onExplosion(EntityExplodeEvent event) {
        if (!Config.USE_CANCEL_EXPLOSIONS.getOption() || !Util.isEnabledWorld(event.getEntity().getWorld().getName()))
            return;
        List<Block> toExplode = event.blockList();
        for (Block block : toExplode) {
            if (!block.getType().name().contains("WALL_SIGN")) continue;
            Sign s = (Sign) block.getState();
            if (s.getLine(0).contains(Util.color(Config.SIGN_FORMATTED_LINES.getStrings()[0]))) {
                event.blockList().remove(block);
            }
        }
    }

}
