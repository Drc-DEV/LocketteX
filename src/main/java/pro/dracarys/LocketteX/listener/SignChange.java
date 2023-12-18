package pro.dracarys.LocketteX.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.InventoryHolder;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.api.LocketteXAPI;
import pro.dracarys.LocketteX.api.PlayerProtectBlockEvent;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.Message;
import pro.dracarys.LocketteX.data.SignUser;
import pro.dracarys.LocketteX.hooks.claim.GriefPreventionHook;
import pro.dracarys.LocketteX.hooks.VaultHook;
import pro.dracarys.LocketteX.hooks.claim.WorldGuardHook;
import pro.dracarys.LocketteX.utils.ClaimUtil;
import pro.dracarys.LocketteX.utils.Util;

public class SignChange implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onSignChange(SignChangeEvent e) {
        if (!Util.isEnabledWorld(e.getPlayer().getWorld().getName())) {
            return;
        }

        if (e.getBlock().getState() instanceof Sign) {
            Sign sign = (Sign) e.getBlock().getState();
            String oldLine = ChatColor.stripColor(sign.getLine(0));

            if (oldLine.equalsIgnoreCase(Config.SIGN_ID_LINE.getString()) && !LocketteXAPI.canBreak(e.getPlayer(), e.getBlock())) {
                e.setCancelled(true);
                return;
            }
        }

        String line0 = e.getLine(0);
        if (line0 == null || !line0.equalsIgnoreCase(Config.SIGN_ID_LINE.getString())) return;
        // From this point forward we're sure the player is trying to create a [Protect] sign
        if (!Config.PERMISSION_FOR_ALL.getOption() && !e.getPlayer().hasPermission(Config.PERMISSION_CREATION.getString())) {
            e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.CREATION_NOPERMISSION.getMessage());
            e.getBlock().breakNaturally();
            return;
        }
        if (VaultHook.isEnabled() && VaultHook.getEconomy().getBalance(e.getPlayer()) < Config.PRICE_CREATION.getInt()) {
            e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.NOT_ENOUGH_MONEY.getMessage().replace("%price%", Config.PRICE_CREATION.getInt() + ""));
            e.getBlock().breakNaturally();
            return;
        }
        Block attachedBlock;
        Sign s = (Sign) e.getBlock().getState();
        try {
            org.bukkit.material.Sign sd = (org.bukkit.material.Sign) s.getData();
            attachedBlock = e.getBlock().getRelative(sd.getAttachedFace());
        } catch (ClassCastException | NullPointerException ex) {
            if (s.getBlockData() instanceof Directional) {
                Directional signData = (Directional) s.getBlockData();
                attachedBlock = e.getBlock().getRelative(signData.getFacing().getOppositeFace());
            } else if (s.getBlockData() instanceof Rotatable) {
                Rotatable signData = (Rotatable) s.getBlockData();
                attachedBlock = e.getBlock().getRelative(signData.getRotation().getOppositeFace());
            } else {
                return;
            }
        }
        if (Config.USE_CANBUILD_CHECK.getOption() && !Util.canBuildAt(e.getPlayer(), attachedBlock.getLocation())) {
            e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.CANT_PROTECT_CANTBUILD.getMessage());
            return;
        }
        if (Config.PROTECT_CLAIMED_ONLY.getOption() && !ClaimUtil.isClaimedAt(attachedBlock.getLocation())) {
            e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.CANT_PROTECT_ON_UNCLAIMED.getMessage());
            e.getBlock().breakNaturally();
            return;
        }
        if (attachedBlock.getState() instanceof InventoryHolder || attachedBlock.getState().getBlockData() instanceof Openable) {
            SignUser owner = LocketteXAPI.getOwner(attachedBlock.getState());
            if (owner != null) {
                e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.ALREADY_PROTECTED.getMessage().replace("%owner%", owner.getName()));
                e.getBlock().breakNaturally();
                return;
            }
        } else {
            LocketteX.getInstance().getLocaleManager().sendMessage(e.getPlayer(), Message.PREFIX.getMessage() + Message.CANT_PROTECT_THIS.getMessage(), attachedBlock.getType(), (short) 0, null);
            return;
        }
        if (LocketteX.getInstance().getHookManager().getHookedPlugins().contains("GriefPrevention")) {
            GriefPreventionHook gpHook = (GriefPreventionHook) LocketteX.getInstance().getHookManager().getHookedPluginsMap().get("GriefPrevention");
            if (!gpHook.canBuildAt(e.getPlayer(), attachedBlock.getLocation())) {
                e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.GP_HOOK_CANT_PROTECT.getMessage());
                e.getBlock().breakNaturally();
                return;
            }
        }
        if (LocketteX.getInstance().getHookManager().getHookedPlugins().contains("WorldGuard")) {
            WorldGuardHook psHook = (WorldGuardHook) LocketteX.getInstance().getHookManager().getHookedPluginsMap().get("WorldGuard");
            if (!psHook.isInOwnedProtection(e.getPlayer(), attachedBlock.getLocation())) {
                e.getPlayer().sendMessage(Message.PREFIX.getMessage() + Message.GP_HOOK_CANT_PROTECT.getMessage());
                return;
            }
        }
        PlayerProtectBlockEvent protectEvent = new PlayerProtectBlockEvent(e.getPlayer(), attachedBlock);
        Bukkit.getPluginManager().callEvent(protectEvent);
        if (protectEvent.isCancelled()) return;
        int num = 0;
        for (String ln : Config.SIGN_FORMATTED_LINES.getStrings()) {
            e.setLine(num, Util.color(ln
                    .replace("%owner%", e.getPlayer().getName())
                    .replace("%uuid%", e.getPlayer().getUniqueId().toString())));
            num++;
            if (num >= 4) // Sign has 4 lines
                break;
        }
        if (VaultHook.isEnabled()) {
            VaultHook.getEconomy().withdrawPlayer(e.getPlayer(), Config.PRICE_CREATION.getInt());
            e.getPlayer().sendMessage(Message.PROTECT_SUCCESS_ECON.getMessage().replace("%price%", VaultHook.getEconomy().format(Config.PRICE_CREATION.getInt()) + ""));
        } else {
            e.getPlayer().sendMessage(Message.PROTECT_SUCCESS.getMessage());
        }
    }
}