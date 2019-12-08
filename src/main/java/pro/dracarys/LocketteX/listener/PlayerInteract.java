package pro.dracarys.LocketteX.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pro.dracarys.LocketteX.utils.Config;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (Config.SNEAKCLICK_TO_CREATE.getOption() && e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getPlayer().isSneaking() && Config.ENABLED_WORLDS.getStringList().stream().anyMatch(e.getPlayer().getWorld().getName()::equalsIgnoreCase) && e.getPlayer().hasPermission(Config.PERMISSION_CREATION.getString()) && e.hasItem() && e.hasBlock() && e.getItem() != null && e.getItem().getType().toString().contains("SIGN")) {
            //TODO: finish up the sneak click to protect, check if can place at block
        }
    }
}
