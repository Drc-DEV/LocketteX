package pro.dracarys.LocketteX.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.api.PlayerProtectContainerEvent;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.Message;
import pro.dracarys.LocketteX.hooks.VaultHook;
import pro.dracarys.LocketteX.utils.Util;

public class BlockPlace implements Listener {

    static boolean hasUpdateBooleanBoolean = true;

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent e) {
        if (!Util.isEnabledWorld(e.getPlayer().getWorld().getName())) {
            return;
        }
        if (Config.SNEAKCLICK_TO_CREATE.getOption()
                && e.getPlayer().isSneaking()
                && e.getPlayer().hasPermission(Config.PERMISSION_CREATION.getString())
                && e.getBlock().getState() instanceof Sign
                && e.getBlockAgainst().getState() instanceof InventoryHolder) {
            if (VaultHook.isEnabled() && VaultHook.getEconomy().getBalance(e.getPlayer()) < Config.PRICE_CREATION.getInt()) {
                e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.NOT_ENOUGH_MONEY.getMessage().replace("%price%", VaultHook.getEconomy().format(Config.PRICE_CREATION.getInt())));
                return;
            }
            String owner = LocketteXAPI.getChestOwner(e.getBlockAgainst().getState());
            if (owner == null) { // Handle only cases where the chest is not already protected
                PlayerProtectContainerEvent protectEvent = new PlayerProtectContainerEvent(e.getPlayer(), e.getBlock());
                Bukkit.getPluginManager().callEvent(protectEvent);
                if (protectEvent.isCancelled()) return;
                try {
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2, 5));
                } catch (Exception ignored) {
                    // ignored
                }
                e.getPlayer().openInventory(Bukkit.createInventory(null, 9, "LocketteX"));
                Bukkit.getScheduler().runTaskLater(LocketteX.getInstance(), () -> e.getPlayer().closeInventory(), 1);
                Bukkit.getScheduler().runTaskLater(LocketteX.getInstance(), () -> {
                    Sign s = (Sign) e.getBlock().getState();
                    int num = 0;
                    for (String ln : Config.SIGN_FORMATTED_LINES.getStrings()) {
                        s.setLine(num, Util.color(ln.replace("%owner%", e.getPlayer().getName())));
                        num++;
                        if (num >= 4) // Sign has 4 lines
                            break;
                    }
                    if (hasUpdateBooleanBoolean) {
                        try {
                            s.update(false, false);
                        } catch (final NoSuchMethodError err) {
                            hasUpdateBooleanBoolean = false;
                            s.update();
                        }
                    } else {
                        s.update();
                    }

                }, 2);
                if (VaultHook.isEnabled()) {
                    VaultHook.getEconomy().withdrawPlayer(e.getPlayer(), Config.PRICE_CREATION.getInt());
                    e.getPlayer().sendMessage(Message.CHEST_PROTECT_SUCCESS_ECON.getMessage().replace("%price%", VaultHook.getEconomy().format(Config.PRICE_CREATION.getInt())));
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
        if ((block.getState() instanceof InventoryHolder)) {
            String owner = LocketteXAPI.getChestOwner(block.getState());
            if (owner != null && !e.getPlayer().getName().equalsIgnoreCase(owner)) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.HOPPER_PLACE_DENIED.getMessage());
            }
        }
    }

}
