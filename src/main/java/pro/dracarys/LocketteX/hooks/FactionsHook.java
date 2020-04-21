package pro.dracarys.LocketteX.hooks;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import org.bukkit.Location;

public class FactionsHook {

    public static String getLeaderOfFactionAt(Location location) {
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

    public static String getFactionTagAt(Location location) {
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

    public static boolean isClaimed(Location location) {
        return !getFactionTagAt(location).equalsIgnoreCase("");
    }

    public static boolean isSetup() {
        return HookManager.getInstance().getEnabledHooks().contains("Factions");
    }

}
