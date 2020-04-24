package pro.dracarys.LocketteX.hooks.claim;

import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Location;

public class TownyHook extends ClaimPlugin {

    @Override
    public String getLeaderOfClaimAt(Location location) {
        if (TownyUniverse.isWilderness(location.getBlock())) return "";
        try {
            String townAtLoc = (TownyUniverse.getTownName(location));
            if (townAtLoc != null) return TownyUniverse.getDataSource().getTown(townAtLoc).getMayor().getName();
        } catch (Exception nre) {
            return "";
        }
        return "";
    }

    @Override
    public String getClaimTagAt(Location location) {
        if (TownyUniverse.isWilderness(location.getBlock())) return "";
        try {
            String townAtLoc = (TownyUniverse.getTownName(location));
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
