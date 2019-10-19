package pro.dracarys.LocketteX.hooks;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import org.bukkit.Location;

public class FactionsHook {

    public static String getLeaderOfFactionAt(Location location) {
        FLocation fLoc = new FLocation(location);
        Faction faction = Board.getInstance().getFactionAt(fLoc);
        if (faction.isWilderness() || !faction.isNormal() || faction.isSafeZone() || faction.isWarZone()) {
            return "";
        }
        return faction.getFPlayerAdmin().getName();
    }

}
