package pro.dracarys.LocketteX.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.Message;
import pro.dracarys.LocketteX.utils.ClaimUtil;
import pro.dracarys.LocketteX.utils.Util;

public class InventoryOpen implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInvOpen(InventoryOpenEvent e) {
        if (!Util.isEnabledWorld(e.getPlayer().getWorld().getName())) {
            return;
        }
        if (!(e.getPlayer() instanceof Player) || e.getInventory().getHolder() == null) {
            return;
        }
        Player p = (Player) e.getPlayer();
        if (p.isOp() || (Config.PROTECT_CLAIMED_ONLY.getOption() && ClaimUtil.isClaimedAt(p.getLocation())) || (Config.LEADER_CAN_OPEN.getOption() && ClaimUtil.getLeaderAt(p.getLocation()).equalsIgnoreCase(p.getName())))
            return;
        InventoryHolder holder = e.getInventory().getHolder();
        assert holder != null;
        Location loc = Util.getHolderLocation(holder);
        assert loc != null;
        String owner = LocketteXAPI.getChestOwner(loc.getBlock().getState());
        if (owner != null && !p.getName().equalsIgnoreCase(owner)) {
            p.sendMessage(Message.PREFIX.getMessage() + Message.CHEST_OPEN_DENIED.getMessage().replace("%owner%", owner));
            e.setCancelled(true);
        }
    }
}