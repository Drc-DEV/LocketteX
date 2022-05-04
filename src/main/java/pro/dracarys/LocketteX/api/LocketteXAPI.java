package pro.dracarys.LocketteX.api;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.*;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.Message;
import pro.dracarys.LocketteX.data.SignUser;
import pro.dracarys.LocketteX.utils.ClaimUtil;
import pro.dracarys.LocketteX.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LocketteXAPI {

    public static boolean canBreak(Player p, Block b) {
        if (!Util.isEnabledWorld(p.getWorld().getName())) return true;
        if (p.isOp() || p.hasPermission(Config.PERMISSION_ADMIN.getString()) || (Config.LEADER_CAN_BREAK.getOption() && ClaimUtil.getLeaderAt(b.getLocation()).equalsIgnoreCase(p.getName())))
            return true;
        SignUser owner;
        List<SignUser> wh;
        boolean isSign = false;
        if (b.getType().name().contains("WALL_SIGN")) {
            isSign = true;
            Sign s = (Sign) b.getState();
            owner = getSignOwner(s, b, b);
            wh = getWhitelistedAt(b);
        } else {
            wh = getWhitelisted(b.getState());
            if (wh.isEmpty()) return true;
            owner = wh.get(0);
        }
        if (owner == null || owner.getUniqueId().equals(p.getUniqueId())) return true;
        if (Config.ONLY_OWNER_CAN_BREAK_SIGN.getOption() || wh.stream().noneMatch(signUser -> signUser.getUniqueId().equals(p.getUniqueId()))) {
            if (isSign)
                p.sendMessage(Message.PREFIX.getMessage() + Message.SIGN_BREAK_DENIED.getMessage()
                        .replace("%owner%", owner.getName()));
            else
                LocketteX.getInstance().getLocaleManager().sendMessage(p, Message.PREFIX.getMessage() + Message.BREAK_DENIED.getMessage()
                        .replace("%owner%", owner.getName()), b.getType(), (short) 0, null);
            return false;
        }
        return true;
    }

    public static boolean isProtected(BlockState blockState) {
        return !getWhitelisted(blockState).isEmpty();
    }

    // Returns players who have given access by the owner
    public static List<SignUser> getWhitelistedAt(Block sign) {
        return LocketteX.getWhitelistMap().getOrDefault(Util.locationSerialize(sign), new ArrayList<>());
    }

    @Deprecated // use hasAccess
    public static boolean hasChestAccess(Player p, BlockState blockState) {
        return hasAccess(p, blockState);
    }


    public static boolean hasAccess(Player p, BlockState blockState) {
        List<SignUser> wh = getWhitelisted(blockState);
        return wh.isEmpty() || wh.stream().anyMatch(signUser -> signUser.getUniqueId().equals(p.getUniqueId()) && (!Config.ONLY_ACCESS_WHEN_ONLINE.getOption() || Bukkit.getOfflinePlayer(wh.get(0).getUniqueId()).isOnline()));
    }

    public static boolean giveAccess(OfflinePlayer p, Block sign) {
        String loc = Util.locationSerialize(sign);
        List<SignUser> actualWhitelist = LocketteX.getWhitelistMap().getOrDefault(loc, new ArrayList<>());
        if (actualWhitelist.stream().anyMatch(signUser -> signUser.getUniqueId().equals(p.getUniqueId()))) return false;
        actualWhitelist.add(new SignUser(p.getName(), p.getUniqueId()));
        LocketteX.getWhitelistMap().put(loc, actualWhitelist);
        return true;
    }

    public static boolean removeAccess(OfflinePlayer p, Block sign) {
        String loc = Util.locationSerialize(sign);
        List<SignUser> actualWhitelist = LocketteX.getWhitelistMap().getOrDefault(loc, new ArrayList<>());
        if (actualWhitelist.isEmpty()) return false;
        actualWhitelist.removeIf(signUser -> signUser.getUniqueId().equals(p.getUniqueId()));
        if (actualWhitelist.isEmpty())
            LocketteX.getWhitelistMap().remove(loc);
        else
            LocketteX.getWhitelistMap().put(loc, actualWhitelist);
        return true;
    }

    @Deprecated // use getOwner
    public static String getChestOwner(BlockState blockState) {
        return getOwner(blockState).getName();
    }

    // Returns the main owner
    public static SignUser getOwner(BlockState blockState) {
        List<SignUser> wh = getWhitelisted(blockState);
        return wh.isEmpty() ? null : wh.get(0);
    }

    // Returns the main owner + whitelisted players (owner is the first of the list)
    public static List<SignUser> getWhitelisted(BlockState blockState) {
        List<Block> protectedBlocks = new ArrayList<>();
        if (blockState instanceof InventoryHolder) {
            if (blockState instanceof Chest) {
                Chest chest = (Chest) blockState;
                Inventory chestInventory = chest.getInventory();
                if (chestInventory instanceof DoubleChestInventory) {
                    DoubleChest doubleChest = (DoubleChest) chestInventory.getHolder();
                    protectedBlocks.add(((Chest) doubleChest.getLeftSide()).getBlock());
                    protectedBlocks.add(((Chest) doubleChest.getRightSide()).getBlock());
                } else {
                    protectedBlocks.add(chest.getBlock());
                }
            } else {
                protectedBlocks.add(blockState.getBlock());
            }
        } else if (blockState.getBlockData() instanceof Openable) { // Door, Gate, Trapdoor
            if (blockState.getBlockData() instanceof Bisected) { // Doors
                Bisected bi = (Bisected) blockState.getBlockData();
                if (bi.getHalf().equals(Bisected.Half.BOTTOM)) {
                    protectedBlocks.add(blockState.getBlock().getRelative(BlockFace.UP));
                } else {
                    protectedBlocks.add(blockState.getBlock().getRelative(BlockFace.DOWN));
                }
            }
            protectedBlocks.add(blockState.getBlock());
        } else {
            return new ArrayList<>();
        }
        for (Block b : protectedBlocks) {
            for (Block block : Util.getBlocks(b, 1)) {
                if (!block.getType().name().contains("WALL_SIGN")) continue;
                Sign s = (Sign) block.getState();
                Block attachedBlock;
                try {
                    org.bukkit.material.Sign sd = (org.bukkit.material.Sign) s.getData();
                    attachedBlock = block.getRelative(sd.getAttachedFace());
                } catch (NullPointerException | ClassCastException ex) { // Use new API (fixes 1.15 errors)
                    WallSign ws = (WallSign) s.getBlockData();
                    attachedBlock = block.getRelative(ws.getFacing().getOppositeFace());
                }
                if (b.getLocation().equals(attachedBlock.getLocation())) {
                    SignUser found = getSignOwner(s, block, attachedBlock);
                    if (found != null) {
                        List<SignUser> wh = new ArrayList<>();
                        wh.add(found);
                        wh.addAll(LocketteX.getWhitelistMap().getOrDefault(Util.locationSerialize(block), new ArrayList<>()));
                        Util.debug(wh.stream().map(signUser -> signUser.getName() + " " + signUser.getUniqueId() + " ").collect(Collectors.joining()));
                        return wh;
                    }
                }
            }
        }
        // No Sign found
        return new ArrayList<>();
    }

    public static SignUser getSignOwner(Block signBlock) {
        return getSignOwner((Sign) signBlock.getState(), signBlock, null);
    }

    public static SignUser getSignOwner(Sign s, Block signBlock, Block attachedBlock) {
        if (!s.getLine(0).equalsIgnoreCase(Util.color(Config.SIGN_FORMATTED_LINES.getStrings()[0]))) return null;
        if (s.getLine(Config.SIGN_OWNER_LINE.getInt() - 1).length() < 3) { //Playernames can't be less than 3 digits
            return null;
        }
        try {
            // Strip color so that you can add colors to the name
            String line = ChatColor.stripColor(s.getLine(Config.SIGN_OWNER_LINE.getInt() - 1));
            OfflinePlayer owner;
            if (line.contains("#")) { // UUID Compatibility
                owner = Bukkit.getOfflinePlayer(UUID.fromString(line.split("#")[1]));
            } else {
                owner = Bukkit.getOfflinePlayer(line);
            }
            if (!owner.hasPlayedBefore()) {
                signBlock.breakNaturally();
                return null;
            }
            if (Util.isExpired(owner.getUniqueId())) {
                signBlock.breakNaturally(); // Break sign since protection expired
                return null;
            } else {
                if (attachedBlock == null || attachedBlock == signBlock) {
                    Util.debug("Owner of Sign at " + signBlock.getLocation() + " is '" + owner.getName() + "'");
                } else {
                    Util.debug("Owner of Sign at " + signBlock.getLocation() + " which protects container at " + attachedBlock.getLocation() + " is '" + owner.getName() + "'");
                }
                return new SignUser(owner.getName(), owner.getUniqueId());
            }
        } catch (Exception ex) {
            //ignored
        }
        return null;
    }
}
