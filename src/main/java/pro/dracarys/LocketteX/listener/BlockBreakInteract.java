package pro.dracarys.LocketteX.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.Message;
import pro.dracarys.LocketteX.data.SignUser;

public class BlockBreakInteract implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e) {
        if (!LocketteXAPI.canBreak(e.getPlayer(), e.getBlock())) e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent e) {
        if (!e.hasBlock() || e.getPlayer().isOp() || e.getPlayer().hasPermission(Config.PERMISSION_ADMIN.getString()))
            return;
        if (!LocketteXAPI.hasAccess(e.getPlayer(), e.getClickedBlock().getState())) {
            e.setCancelled(true);
            SignUser owner = LocketteXAPI.getOwner(e.getClickedBlock().getState());
            LocketteX.getInstance().getLocaleManager().sendMessage(e.getPlayer(), Message.PREFIX.getMessage() + Message.INTERACT_DENIED.getMessage()
                    .replace("%owner%", owner.getName()), e.getClickedBlock().getType(), (short) 0, null);
        }
    }

}
