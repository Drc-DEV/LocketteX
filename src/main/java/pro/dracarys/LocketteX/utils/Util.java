package pro.dracarys.LocketteX.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.hooks.FactionsHook;
import pro.dracarys.LocketteX.hooks.TownyHook;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Block> getBlocks(Block start, int radius) {
        if (radius < 0) {
            return new ArrayList<Block>(0);
        }
        int iterations = (radius * 2) + 1;
        List<Block> blocks = new ArrayList<Block>(iterations * iterations * iterations);
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

}
