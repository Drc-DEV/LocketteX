package pro.dracarys.LocketteX.hooks;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Location;
import pro.dracarys.LocketteX.LocketteX;

public class MCoreFactionsHook {

    public static String getLeaderOfFactionAt(Location location) {
        try {
            Faction faction = BoardColl.get().getFactionAt(PS.valueOf(location));
            if (!faction.isNormal())
                return "";
            return faction.getLeader().getName();
        } catch (NullPointerException npe) {
            return "";
        }
    }

    public static String getFactionTagAt(Location location) {
        try {
            Faction faction = BoardColl.get().getFactionAt(PS.valueOf(location));
            if (!faction.isNormal())
                return "";
            return faction.getName();
        } catch (NullPointerException npe) {
            return "";
        }
    }

    public static boolean isClaimed(Location location) {
        return !getFactionTagAt(location).equalsIgnoreCase("");
    }

    public static boolean isSetup() {
        return HookManager.getInstance().getEnabledHooks().contains("Factions") && LocketteX.isMCoreFactions;
    }

}
