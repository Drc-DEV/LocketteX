package pro.dracarys.LocketteX.hooks.claim;

import net.prosavage.factionsx.core.Faction;
import net.prosavage.factionsx.manager.GridManager;
import org.bukkit.Location;

public class FactionsXHook extends ClaimPlugin {

    @Override
    public String getLeaderOfClaimAt(Location location) {
        Faction f = GridManager.INSTANCE.getFactionAt(location.getChunk());
        if (f.isWilderness()) return "";
        return f.getLeader().getName();
    }

    @Override
    public String getClaimTagAt(Location location) {
        Faction f = GridManager.INSTANCE.getFactionAt(location.getChunk());
        if (f.isWilderness()) return "";
        return f.getTag();
    }

    @Override
    public boolean isClaimed(Location location) {
        return !getClaimTagAt(location).equalsIgnoreCase("");
    }

}
