package pro.dracarys.LocketteX.hooks;

import com.licel.stringer.annotations.secured;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import org.bukkit.Location;

@secured
public class FactionsHook {

    public static String getLeaderOfFactionAt(Location location) {
        FLocation fLoc = new FLocation(location);
        Faction faction = Board.getInstance().getFactionAt(fLoc);
        if (faction.isWilderness() || !faction.isNormal() || faction.isSafeZone() || faction.isWarZone()) {
            return "";
        }
        return faction.getFPlayerAdmin().getName();
    }

    public static String getFactionTagAt(Location location) {
        FLocation fLoc = new FLocation(location);
        Faction faction = Board.getInstance().getFactionAt(fLoc);
        if (faction.isWilderness() || !faction.isNormal() || faction.isSafeZone() || faction.isWarZone()) {
            return "";
        }
        return faction.getTag();
    }

    public static boolean isClaimed(Location location) {
        return !getFactionTagAt(location).equalsIgnoreCase("");
    }

    public static boolean isSetup() {
        return HookManager.getInstance().getEnabledHooks().contains("Factions");
    }

}
