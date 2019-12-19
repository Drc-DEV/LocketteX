package pro.dracarys.LocketteX.hooks;

import com.licel.stringer.annotations.secured;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Location;

@secured
public class TownyHook {

    public static String getMayorOfTownAt(Location location) {
        if (TownyUniverse.isWilderness(location.getBlock())) return "";
        try {
            String townAtLoc = (TownyUniverse.getTownName(location));
            if (townAtLoc != null) return TownyUniverse.getDataSource().getTown(townAtLoc).getMayor().getName();
        } catch (NullPointerException | NotRegisteredException nre) {
            return "";
        }
        return "";
    }

    public static boolean isClaimed(Location location) {
        if (TownyUniverse.isWilderness(location.getBlock())) return false;
        try {
            String townAtLoc = (TownyUniverse.getTownName(location));
            if (townAtLoc != null) return true;
        } catch (NullPointerException npe) {
            return false;
        }
        return false;
    }

    public static boolean isSetup() {
        return HookManager.getInstance().getEnabledHooks().contains("Towny");
    }

}
