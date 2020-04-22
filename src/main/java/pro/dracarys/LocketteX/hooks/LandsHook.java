package pro.dracarys.LocketteX.hooks;

import me.angeschossen.lands.api.integration.LandsIntegration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import pro.dracarys.LocketteX.LocketteX;

public class LandsHook {

    public static void init() {
        landsAddon = new LandsIntegration(LocketteX.getInstance(), false);
    }

    private static LandsIntegration landsAddon;

    public static String getLeaderOfFactionAt(Location location) {
        try {
            return Bukkit.getOfflinePlayer(landsAddon.getLand(location).getOwnerUID()).getName();
        } catch (NullPointerException npe) {
            return "";
        }
    }

    public static String getFactionTagAt(Location location) {
        try {
            return landsAddon.getLand(location).getName();
        } catch (NullPointerException npe) {
            return "";
        }
    }

    public static boolean isClaimed(Location location) {
        try {
            return landsAddon.getLand(location).exists();
        } catch (NullPointerException npe) {
            return false;
        }
    }

    public static boolean isSetup() {
        return HookManager.getInstance().getEnabledHooks().contains("Lands");
    }
}
