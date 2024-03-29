package pro.dracarys.LocketteX.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Openable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.api.PlayerProtectBlockEvent;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.Message;
import pro.dracarys.LocketteX.data.SignUser;
import pro.dracarys.LocketteX.hooks.VaultHook;
import pro.dracarys.LocketteX.hooks.claim.GriefPreventionHook;
import pro.dracarys.LocketteX.hooks.claim.WorldGuardHook;
import pro.dracarys.LocketteX.utils.Util;

public class BlockPlace implements Listener {

    static boolean hasUpdateBooleanBoolean = true;

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent e) {
        if (!Util.isEnabledWorld(e.getPlayer().getWorld().getName()))
            return;
        if (Config.SNEAKCLICK_TO_CREATE.getOption()
                && e.getPlayer().isSneaking()
                && (Config.PERMISSION_FOR_ALL.getOption() || e.getPlayer().hasPermission(Config.PERMISSION_CREATION.getString()))
                && e.getBlock().getState() instanceof Sign
                && (e.getBlockAgainst().getState() instanceof InventoryHolder || e.getBlockAgainst().getState().getBlockData() instanceof Openable)) {
            if (VaultHook.isEnabled() && VaultHook.getEconomy().getBalance(e.getPlayer()) < Config.PRICE_CREATION.getInt()) {
                e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.NOT_ENOUGH_MONEY.getMessage().replace("%price%", VaultHook.getEconomy().format(Config.PRICE_CREATION.getInt())));
                return;
            }
            if (!LocketteXAPI.isProtected(e.getBlockAgainst().getState())) { // Handle only cases where the chest is not already protected
                if (Config.USE_CANBUILD_CHECK.getOption() && !Util.canBuildAt(e.getPlayer(), e.getBlockAgainst().getLocation())) {
                    e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.CANT_PROTECT_CANTBUILD.getMessage());
                    return;
                }
                if (LocketteX.getInstance().getHookManager().getHookedPlugins().contains("GriefPrevention")) {
                    GriefPreventionHook gpHook = (GriefPreventionHook) LocketteX.getInstance().getHookManager().getHookedPluginsMap().get("GriefPrevention");
                    if (!gpHook.canBuildAt(e.getPlayer(), e.getBlockAgainst().getLocation())) {
                        e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.GP_HOOK_CANT_PROTECT.getMessage());
                        return;
                    }
                }
                if (LocketteX.getInstance().getHookManager().getHookedPlugins().contains("WorldGuard")) {
                    WorldGuardHook psHook = (WorldGuardHook) LocketteX.getInstance().getHookManager().getHookedPluginsMap().get("WorldGuard");
                    if (!psHook.isInOwnedProtection(e.getPlayer(), e.getBlockAgainst().getLocation())) {
                        e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.GP_HOOK_CANT_PROTECT.getMessage());
                        return;
                    }
                }
                PlayerProtectBlockEvent protectEvent = new PlayerProtectBlockEvent(e.getPlayer(), e.getBlock());
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
                        } catch (NoSuchMethodError err) {
                            hasUpdateBooleanBoolean = false;
                            s.update();
                        }
                    } else {
                        s.update();
                    }
                }, 2);
                if (VaultHook.isEnabled()) {
                    VaultHook.getEconomy().withdrawPlayer(e.getPlayer(), Config.PRICE_CREATION.getInt());
                    e.getPlayer().sendMessage(Message.PROTECT_SUCCESS_ECON.getMessage().replace("%price%", VaultHook.getEconomy().format(Config.PRICE_CREATION.getInt())));
                } else {
                    e.getPlayer().sendMessage(Message.PROTECT_SUCCESS.getMessage());
                }
            }
        }
        // Handle hoppers bypass - ONLY CONTAINERS
        if (!e.getBlock().getType().equals(Material.HOPPER)) return;
        if (e.getPlayer().isOp())
            return;
        Block block = e.getBlock().getRelative(BlockFace.UP);
        if ((block.getState() instanceof InventoryHolder)) {
            SignUser owner = LocketteXAPI.getOwner(block.getState());
            if (owner != null && !e.getPlayer().getUniqueId().equals(owner.getUniqueId())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.HOPPER_PLACE_DENIED.getMessage());
            }
        }
    }

}
