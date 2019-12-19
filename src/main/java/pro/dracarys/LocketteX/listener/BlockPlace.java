package pro.dracarys.LocketteX.listener;

import com.licel.stringer.annotations.secured;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.Message;
import pro.dracarys.LocketteX.utils.Util;
@secured
public class BlockPlace implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent e) {
        if (Config.ENABLED_WORLDS.getStringList().stream().noneMatch(e.getPlayer().getWorld().getName()::equalsIgnoreCase)) {
            return;
        }
        if (Config.SNEAKCLICK_TO_CREATE.getOption() && e.getPlayer().isSneaking() && e.getPlayer().hasPermission(Config.PERMISSION_CREATION.getString()) && e.getBlock().getState() instanceof Sign && ((e.getBlockAgainst().getState() instanceof DoubleChest) || e.getBlockAgainst().getState() instanceof Chest)) {
            if (LocketteX.UseEconomy) {
                if (LocketteX.econ.getBalance(e.getPlayer()) < Config.PRICE_CREATION.getInt()) {
                    e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.NOT_ENOUGH_MONEY.getMessage().replace("%price%", Config.PRICE_CREATION.getInt() + ""));
                    return;
                }
            }
            Chest chest = (Chest) e.getBlockAgainst().getState();
            String owner = LocketteXAPI.getChestOwner(chest.getInventory().getHolder());
            if (owner == null) { // Handle only cases where the chest is not already protected
                Sign s = (Sign) e.getBlock().getState();
                int num = 0;
                for (String ln : Config.SIGN_FORMATTED_LINES.getStrings()) {
                    s.setLine(num, Util.color(ln.replace("%owner%", e.getPlayer().getName())));
                    num++;
                    if (num >= 5) // Sign has 4 lines
                        break;
                }
                s.update();
                try {
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2, 5));
                } catch (Exception ignored) {
                }
                e.getPlayer().openInventory(Bukkit.createInventory(null, 9, "LocketteX"));
                Bukkit.getScheduler().runTaskLater(LocketteX.getInstance(), () -> e.getPlayer().closeInventory(), 1);
                if (LocketteX.UseEconomy) {
                    LocketteX.econ.withdrawPlayer(e.getPlayer(), Config.PRICE_CREATION.getInt());
                    e.getPlayer().sendMessage(Message.CHEST_PROTECT_SUCCESS_ECON.getMessage().replace("%price%", Config.PRICE_CREATION.getInt() + ""));
                } else {
                    e.getPlayer().sendMessage(Message.CHEST_PROTECT_SUCCESS.getMessage());
                }
            }
        }


        // Handle hoppers bypass
        if (!e.getBlock().getType().equals(Material.HOPPER)) return;
        if (e.getPlayer().isOp())
            return;
        Block block = e.getBlock().getRelative(BlockFace.UP);
        if ((block.getState() instanceof DoubleChest) || block.getState() instanceof Chest) {
            Chest chest = (Chest) block.getState();
            String owner = LocketteXAPI.getChestOwner(chest.getInventory().getHolder());
            if (owner != null && !e.getPlayer().getName().equalsIgnoreCase(owner)) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.HOPPER_PLACE_DENIED.getMessage());
            }
        }
    }

}
