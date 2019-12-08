package pro.dracarys.LocketteX.utils;

import com.licel.stringer.annotations.secured;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.hooks.FactionsHook;
import pro.dracarys.LocketteX.hooks.TownyHook;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@secured
public class Util {
    public static void sendConsole(String str) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', str));
    }

    public static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static int roundToInt(double doubleVar) {
        return (int) Math.round(doubleVar);
    }

    public static List<Block> getBlocks(Block start, int radius) {
        if (radius < 0) {
            return new ArrayList<>(0);
        }
        int iterations = (radius * 2) + 1;
        List<Block> blocks = new ArrayList<>(iterations * iterations * iterations);
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    blocks.add(start.getRelative(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static String getLeaderAt(Location location) {
        if (LocketteX.getInstance().getEnabledHooks().contains("Factions")) {
            return FactionsHook.getLeaderOfFactionAt(location);
        } else if (LocketteX.getInstance().getEnabledHooks().contains("Towny")) {
            return TownyHook.getMayorOfTownAt(location);
        } else {
            return "";
        }
    }

    public static boolean isClaimedAt(Location location) {
        if (LocketteX.getInstance().getEnabledHooks().contains("Factions")) {
            return FactionsHook.isClaimed(location);
        } else if (LocketteX.getInstance().getEnabledHooks().contains("Towny")) {
            return TownyHook.isClaimed(location);
        } else {
            return false;
        }
    }

    // Trim UUID utils for uuid support TODO

    public UUID formatFromInput(String uuid) {
        if (uuid == null) throw new IllegalArgumentException();
        uuid = uuid.trim();
        return uuid.length() == 32 ? fromTrimmed(uuid.replace("-", "")) : UUID.fromString(uuid);
    }

    public UUID fromTrimmed(String trimmedUUID) {
        if (trimmedUUID == null) throw new IllegalArgumentException();
        StringBuilder builder = new StringBuilder(trimmedUUID.trim());
        /* Backwards adding to avoid index adjustments */
        try {
            builder.insert(20, "-");
            builder.insert(16, "-");
            builder.insert(12, "-");
            builder.insert(8, "-");
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
        return UUID.fromString(builder.toString());
    }

}
