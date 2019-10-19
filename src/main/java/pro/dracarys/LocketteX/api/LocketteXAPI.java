package pro.dracarys.LocketteX.api;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import pro.dracarys.LocketteX.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class LocketteXAPI {


    public static boolean hasChestAccess(Player p, InventoryHolder holder){
        String owner = getChestOwner(holder);
        if (owner != null && owner.equalsIgnoreCase(p.getName()))
            return true;
        return false;
    }

    public static String getChestOwner(InventoryHolder holder) {
        List<Block> chestBlocks = new ArrayList<>();
        if (holder instanceof DoubleChest) {
            Util.sendConsole("DoubleChest -> >");
            DoubleChest dchest = (DoubleChest) holder;
            Chest chest1 = (Chest) dchest.getRightSide();
            chestBlocks.add(chest1.getBlock());
            Chest chest2 = (Chest) dchest.getLeftSide();
            chestBlocks.add(chest2.getBlock());
        } else if (holder instanceof Chest) {
            Util.sendConsole("Chest -> >");
            Chest chest = (Chest) holder;
            chestBlocks.add(chest.getBlock());
        } else {
            // Not a chest/doublechest
            return null;
        }
        for (Block chestBlock : chestBlocks) {
            for (Block block : Util.getBlocks(chestBlock, 1)) {
                if (block.getType().equals(Material.WALL_SIGN)) {
                    Util.sendConsole("detected -> > SIGN");
                    Sign s = (Sign) block.getState();
                    org.bukkit.material.Sign sd = (org.bukkit.material.Sign) s.getData();
                    if (chestBlock.getLocation().equals(block.getRelative(sd.getAttachedFace()).getLocation())) {
                        if (s.getLine(0).contains(Util.color("&1[Protect]"))) {
                            Util.sendConsole("protect-sign detected =="+s.getLine(1));
                            if (s.getLine(1).length() <3) { //Playernames can't be less than 3 digits
                                Util.sendConsole("line length <3 ="+s.getLine(1).length());
                                return null;
                            }
                            return (s.getLine(1));
                        }
                    }
                }
            }
        }
        // No Sign found
        return null;
    }
}
