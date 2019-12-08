package pro.dracarys.LocketteX.api;

import com.licel.stringer.annotations.secured;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import pro.dracarys.LocketteX.utils.Config;
import pro.dracarys.LocketteX.utils.Util;

import java.util.ArrayList;
import java.util.List;

@secured
public class LocketteXAPI {

    public static boolean hasChestAccess(Player p, InventoryHolder holder) {
        String owner = getChestOwner(holder);
        if (owner != null && owner.equalsIgnoreCase(p.getName()))
            return true;
        return false;
    }

    public static String getChestOwner(InventoryHolder holder) {
        List<Block> chestBlocks = new ArrayList<>();
        if (holder instanceof DoubleChest) {
            DoubleChest dchest = (DoubleChest) holder;
            Chest chest1 = (Chest) dchest.getRightSide();
            chestBlocks.add(chest1.getBlock());
            Chest chest2 = (Chest) dchest.getLeftSide();
            chestBlocks.add(chest2.getBlock());
        } else if (holder instanceof Chest) {
            Chest chest = (Chest) holder;
            chestBlocks.add(chest.getBlock());
        } else {
            // Not a chest/doublechest
            return null;
        }
        for (Block chestBlock : chestBlocks) {
            for (Block block : Util.getBlocks(chestBlock, 1)) {
                if (block.getType().name().contains("WALL_SIGN")) {
                    Sign s = (Sign) block.getState();
                    org.bukkit.material.Sign sd = (org.bukkit.material.Sign) s.getData();
                    if (chestBlock.getLocation().equals(block.getRelative(sd.getAttachedFace()).getLocation())) {
                        if (s.getLine(0).equalsIgnoreCase(Util.color(Config.SIGN_FORMATTED_LINES.getStrings()[0]))) {
                            if (s.getLine(2).length() < 3) { //Playernames can't be less than 3 digits
                                return null;
                            }
                            // Strip color so that you can add colors to the name
                            return (ChatColor.stripColor(s.getLine(2)));
                        }
                    }
                }
            }
        }
        // No Sign found
        return null;
    }
}
