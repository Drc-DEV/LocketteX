package pro.dracarys.LocketteX.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.Message;
import pro.dracarys.LocketteX.utils.ClaimUtil;
import pro.dracarys.LocketteX.utils.Util;

import java.util.logging.Level;

public class InventoryOpen implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInvOpen(InventoryOpenEvent e) {
        if (!Util.isEnabledWorld(e.getPlayer().getWorld().getName()) || !(e.getPlayer() instanceof Player) || e.getInventory().getHolder() == null)
            return;
        Player p = (Player) e.getPlayer();
        if (p.isOp() || p.hasPermission(Config.PERMISSION_ADMIN.getString())
                || (Config.PROTECT_CLAIMED_ONLY.getOption() && ClaimUtil.isClaimedAt(p.getLocation()))
                || (Config.LEADER_CAN_OPEN.getOption() && ClaimUtil.getLeaderAt(p.getLocation()).equalsIgnoreCase(p.getName())))
            return;
        InventoryHolder holder = e.getInventory().getHolder();
        Location loc = Util.getHolderLocation(holder);
        if (loc != null) {
            try {
                String owner = LocketteXAPI.getChestOwner(loc.getBlock().getState());
                if (owner != null && !p.getName().equalsIgnoreCase(owner)) {
                    LocketteX.getInstance().getLocaleManager().sendMessage(p, Message.PREFIX.getMessage() + Message.CONTAINER_OPEN_DENIED.getMessage().replace("%owner%", owner), loc.getBlock().getType(), (short) 0, null);
                    e.setCancelled(true);
                }
            } catch (NullPointerException npe) {
                if (Config.DEBUG.getOption())
                    Bukkit.getServer().getLogger().log(Level.SEVERE, "NPE on chestOwner check: ", npe);
            }
        }
    }
}