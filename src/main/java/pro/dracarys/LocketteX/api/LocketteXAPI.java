package pro.dracarys.LocketteX.api;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.*;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LocketteXAPI {

    public static boolean isProtected(BlockState blockState) {
        return getChestOwner(blockState) != null;
    }

    public static boolean hasChestAccess(Player p, BlockState blockState) {
        String owner = getChestOwner(blockState);
        return owner != null && owner.equalsIgnoreCase(p.getName());
    }

    public static String getChestOwner(BlockState blockState) {
        List<Block> chestBlocks = new ArrayList<>();
        if (blockState instanceof Chest) {
            Chest chest = (Chest) blockState;
            Inventory chestInventory = chest.getInventory();
            if (chestInventory instanceof DoubleChestInventory) {
                DoubleChest doubleChest = (DoubleChest) chestInventory.getHolder();
                chestBlocks.add(((Chest) doubleChest.getLeftSide()).getBlock());
                chestBlocks.add(((Chest) doubleChest.getRightSide()).getBlock());
            } else {
                chestBlocks.add(chest.getBlock());
            }
        } else {
            if (!(blockState instanceof InventoryHolder)) return null;
            chestBlocks.add(blockState.getBlock());
        }
        for (Block chestBlock : chestBlocks) {
            for (Block block : Util.getBlocks(chestBlock, 1)) {
                if (block.getType().name().contains("WALL_SIGN")) {
                    Sign s = (Sign) block.getState();
                    Block attachedBlock;
                    try {
                        org.bukkit.material.Sign sd = (org.bukkit.material.Sign) s.getData();
                        attachedBlock = block.getRelative(sd.getAttachedFace());
                    } catch (NullPointerException | ClassCastException ex) { // Use new API (fixes 1.15 errors)
                        WallSign ws = (WallSign) s.getBlockData();
                        attachedBlock = block.getRelative(ws.getFacing().getOppositeFace());
                    }
                    if (chestBlock.getLocation().equals(attachedBlock.getLocation())) {
                        return getSignOwner(s, block, attachedBlock);
                    }
                }
            }
        }
        // No Sign found
        return null;
    }

    public static String getSignOwner(Sign s, Block signBlock, Block attachedBlock) {
        if (s.getLine(0).equalsIgnoreCase(Util.color(Config.SIGN_FORMATTED_LINES.getStrings()[0]))) {
            if (s.getLine(Config.SIGN_OWNER_LINE.getInt() - 1).length() < 3) { //Playernames can't be less than 3 digits
                return null;
            }
            try {
                // Strip color so that you can add colors to the name
                String owner = ChatColor.stripColor(s.getLine(Config.SIGN_OWNER_LINE.getInt() - 1));
                if (owner.contains("#")) { // UUID Compatibility
                    OfflinePlayer off = Bukkit.getOfflinePlayer(UUID.fromString(owner.split("#")[1]));
                    if (off.hasPlayedBefore()) {
                        owner = off.getName();
                    } else { // FALLBACK
                        owner = owner.split("#")[0];
                    }
                }
                if (Util.isExpired(owner)) {
                    signBlock.breakNaturally(); // Break sign since protection expired
                    return null;
                } else {
                    if (attachedBlock == null || attachedBlock == signBlock) {
                        Util.debug("Owner of Sign at " + signBlock.getLocation() + " is '" + owner + "'");
                    } else {
                        Util.debug("Owner of Sign at " + signBlock.getLocation() + " which protects container at " + attachedBlock.getLocation() + " is '" + owner + "'");
                    }
                    return owner;
                }
            } catch (Exception ex) {
                //ignored
            }
        }
        return null;
    }
}
