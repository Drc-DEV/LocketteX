package pro.dracarys.LocketteX.utils;

import org.bukkit.Location;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.config.Config;

public class ClaimUtil {

    public static String getLeaderAt(Location location) {
        if (Config.DISABLE_CLAIM_HOOKS.getOption()) return "";
        return LocketteX.getInstance().getClaimPlugin().getLeaderOfClaimAt(location);
    }

    public static boolean isClaimedAt(Location location) {
        if (Config.DISABLE_CLAIM_HOOKS.getOption()) return false;
        return LocketteX.getInstance().getClaimPlugin().isClaimed(location);
    }

}
