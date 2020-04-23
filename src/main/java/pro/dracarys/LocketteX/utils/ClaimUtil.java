package pro.dracarys.LocketteX.utils;

import org.bukkit.Location;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.hooks.*;

public class ClaimUtil {

    public static String getLeaderAt(Location location) {
        if (Config.DISABLE_CLAIM_HOOKS.getOption()) return "";
        if (FactionsHook.isSetup()) {
            return FactionsHook.getLeaderOfFactionAt(location);
        } else if (TownyHook.isSetup()) {
            return TownyHook.getLeaderOfFactionAt(location);
        } else if (FactionsXHook.isSetup()) {
            return FactionsXHook.getLeaderOfFactionAt(location);
        } else if (LandsHook.isSetup()) {
            return LandsHook.getLeaderOfFactionAt(location);
        } else if (MCoreFactionsHook.isSetup()) {
            return MCoreFactionsHook.getLeaderOfFactionAt(location);
        } else {
            return "";
        }
    }

    public static boolean isClaimedAt(Location location) {
        if (Config.DISABLE_CLAIM_HOOKS.getOption()) return false;
        if (FactionsHook.isSetup()) {
            return FactionsHook.isClaimed(location);
        } else if (TownyHook.isSetup()) {
            return TownyHook.isClaimed(location);
        } else if (FactionsXHook.isSetup()) {
            return FactionsXHook.isClaimed(location);
        } else if (LandsHook.isSetup()) {
            return LandsHook.isClaimed(location);
        } else if (MCoreFactionsHook.isSetup()) {
            return MCoreFactionsHook.isClaimed(location);
        } else {
            return false;
        }
    }

}
