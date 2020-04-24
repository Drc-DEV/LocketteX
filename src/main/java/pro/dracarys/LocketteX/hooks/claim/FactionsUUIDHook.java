package pro.dracarys.LocketteX.hooks.claim;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import org.bukkit.Location;

public class FactionsUUIDHook extends ClaimPlugin {

    @Override
    public String getLeaderOfClaimAt(Location location) {
        try {
            FLocation fLoc = new FLocation(location);
            Faction faction = Board.getInstance().getFactionAt(fLoc);
            if (!faction.isNormal())
                return "";
            return faction.getFPlayerAdmin().getName();
        } catch (NullPointerException npe) {
            return "";
        }
    }

    @Override
    public String getClaimTagAt(Location location) {
        try {
            FLocation fLoc = new FLocation(location);
            Faction faction = Board.getInstance().getFactionAt(fLoc);
            if (!faction.isNormal())
                return "";
            return faction.getTag();
        } catch (NullPointerException npe) {
            return "";
        }
    }

    @Override
    public boolean isClaimed(Location location) {
        return !getClaimTagAt(location).equalsIgnoreCase("");
    }

}
