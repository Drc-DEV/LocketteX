package pro.dracarys.LocketteX.hooks.claim;

import com.palmergames.bukkit.towny.TownyAPI;
import org.bukkit.Location;

public class TownyHook extends ClaimPlugin {

    @Override
    public String getLeaderOfClaimAt(Location location) {
        if (TownyAPI.getInstance().isWilderness(location)) return "";
        try {
            String townAtLoc = (TownyAPI.getInstance().getTownName(location));
            if (townAtLoc != null)
                return TownyAPI.getInstance().getDataSource().getTown(townAtLoc).getMayor().getName();
        } catch (Exception nre) {
            return "";
        }
        return "";
    }

    @Override
    public String getClaimTagAt(Location location) {
        if (TownyAPI.getInstance().isWilderness(location)) return "";
        try {
            String townAtLoc = (TownyAPI.getInstance().getTownName(location));
            return townAtLoc != null ? townAtLoc : "";
        } catch (Exception npe) {
            return "";
        }
    }

    @Override
    public boolean isClaimed(Location location) {
        return !getClaimTagAt(location).equalsIgnoreCase("");
    }

}
