package pro.dracarys.LocketteX.hooks.claim;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Location;

public class MCoreHook extends ClaimPlugin {

    @Override
    public String getLeaderOfClaimAt(Location location) {
        try {
            Faction faction = BoardColl.get().getFactionAt(PS.valueOf(location));
            if (!faction.isNormal())
                return "";
            return faction.getLeader().getName();
        } catch (NullPointerException npe) {
            return "";
        }
    }

    @Override
    public String getClaimTagAt(Location location) {
        try {
            Faction faction = BoardColl.get().getFactionAt(PS.valueOf(location));
            if (!faction.isNormal())
                return "";
            return faction.getName();
        } catch (NullPointerException npe) {
            return "";
        }
    }

    @Override
    public boolean isClaimed(Location location) {
        return !getClaimTagAt(location).equalsIgnoreCase("");
    }

}
