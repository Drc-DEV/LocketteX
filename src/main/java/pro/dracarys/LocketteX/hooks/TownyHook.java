package pro.dracarys.LocketteX.hooks;

import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Location;

public class TownyHook {

    public static String getMayorOfTownAt(Location location) {
        if (TownyUniverse.isWilderness(location.getBlock())) return "";
        try {
            String townAtLoc = (TownyUniverse.getTownName(location));
            if (townAtLoc != null) return TownyUniverse.getDataSource().getTown(townAtLoc).getMayor().getName();
        } catch (Exception nre) {
            return "";
        }
        return "";
    }

    public static boolean isClaimed(Location location) {
        if (TownyUniverse.isWilderness(location.getBlock())) return false;
        try {
            String townAtLoc = (TownyUniverse.getTownName(location));
            if (townAtLoc != null) return true;
        } catch (Exception npe) {
            return false;
        }
        return false;
    }

    public static boolean isSetup() {
        return HookManager.getInstance().getEnabledHooks().contains("Towny");
    }

}
