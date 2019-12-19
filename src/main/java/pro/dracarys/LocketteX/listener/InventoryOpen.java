package pro.dracarys.LocketteX.listener;

import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.Message;
import pro.dracarys.LocketteX.utils.ClaimUtil;

import java.util.Arrays;

public class InventoryOpen implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInvOpen(InventoryOpenEvent e) {
        if (Arrays.stream(Config.ENABLED_WORLDS.getStrings()).noneMatch(e.getPlayer().getWorld().getName()::equalsIgnoreCase)) {
            return;
        }
        if (!(e.getPlayer() instanceof Player) || e.getInventory().getHolder() == null || !(e.getInventory().getHolder() instanceof Chest) && !(e.getInventory().getHolder() instanceof DoubleChest)) {
            return;
        }
        Player p = (Player) e.getPlayer();
        if (p.isOp() || (Config.PROTECT_CLAIMED_ONLY.getOption() && ClaimUtil.isClaimedAt(p.getLocation())) || (Config.LEADER_CAN_OPEN.getOption() && ClaimUtil.getLeaderAt(p.getLocation()).equalsIgnoreCase(p.getName())))
            return;
        String owner = LocketteXAPI.getChestOwner(e.getInventory().getHolder());
        if (owner != null && !p.getName().equalsIgnoreCase(owner)) {
            p.sendMessage(Message.PREFIX.getMessage() + Message.CHEST_OPEN_DENIED.getMessage().replace("%owner%", owner));
            e.setCancelled(true);
        }
    }
}