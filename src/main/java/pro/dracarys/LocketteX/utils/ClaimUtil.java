package pro.dracarys.LocketteX.utils;

import org.bukkit.Location;
import pro.dracarys.LocketteX.hooks.FactionsHook;
import pro.dracarys.LocketteX.hooks.TownyHook;

public class ClaimUtil {

    public static String getLeaderAt(Location location) {
        if (FactionsHook.isSetup()) {
            return FactionsHook.getLeaderOfFactionAt(location);
        } else if (TownyHook.isSetup()) {
            return TownyHook.getMayorOfTownAt(location);
        } else {
            return "";
        }
    }

    public static boolean isClaimedAt(Location location) {
        if (FactionsHook.isSetup()) {
            return FactionsHook.isClaimed(location);
        } else if (TownyHook.isSetup()) {
            return TownyHook.isClaimed(location);
        } else {
            return false;
        }
    }

}
